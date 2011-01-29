/**
 * This file is part of KuBatsch.
 *   created on: 25.01.2011
 *   filename: CollisionController.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.networktest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import at.kubatsch.model.ICollidable;
import at.kubatsch.model.ICollisionRule;
import at.kubatsch.model.IUpdatable;
import at.kubatsch.util.Event;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

/**
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class CollisionController
{
    /**
     * The updateinterval in ms.
     */
    public static final int                UPDATE_INTERVAL = 30;

    private Map<String, List<ICollidable>> _collidables;
    private Map<ICollidable, String>       _collidableType;
    private Thread                         _updateThread;
    private boolean                        _running;
    private Event<EventArgs>               _stateUpdated   = new Event<EventArgs>(
                                                                   this);

    private Object                         _lastUpdateLock = new Object();
    private long                           _lastUpdate;

    public long getLastUpdate()
    {
        synchronized (_lastUpdateLock)
        {
            return _lastUpdate;
        }
    }

    public synchronized void setLastUpdate(long lastUpdate)
    {
        synchronized (_lastUpdateLock)
        {
            _lastUpdate = lastUpdate;
        }
    }

    /**
     * Initializes a new instance of the {@link CollisionController} class.
     */
    public CollisionController()
    {
        _collidables = new ConcurrentHashMap<String, List<ICollidable>>();
        _collidableType = new HashMap<ICollidable, String>();
    }

    public synchronized boolean isRunning()
    {
        return _running;
    }

    /**
     * Sets the collidables.
     * @param collidables the collidables to set
     */
    public synchronized void setCollidables(
            Map<String, List<ICollidable>> collidables)
    {
        _collidables = collidables;
        // store time of last update
        setLastUpdate(System.currentTimeMillis());
        // if thread is already waiting, interrupt sleep.
        _updateThread.interrupt();
    }

    public synchronized void setRunning(boolean running)
    {
        _running = running;
    }

    public synchronized Map<String, List<ICollidable>> getCollidablesMapping()
    {
        return _collidables;
    }

    /**
     * Adds a collidable Object and his Type to the List if the type does not
     * exist it will be created
     * @param type
     * @param collidable
     */
    public void addCollidable(String type, ICollidable collidable)
    {
        if (!getCollidablesMapping().containsKey(type))
        {
            getCollidablesMapping().put(type, new Vector<ICollidable>());
        }
        getCollidablesMapping().get(type).add(collidable);

        if (!_collidableType.containsKey(collidable))
        {
            _collidableType.put(collidable, type);
        }
        else
        {
            String tempType = _collidableType.get(collidable);
            tempType = type;
        }

        _stateUpdated.fireEvent(EventArgs.Empty);
    }

    public void removeCollidable(ICollidable collidable)
    {
        getCollidablesMapping().get(_collidableType.get(collidable)).remove(
                collidable);
        _collidableType.remove(collidable);
        _stateUpdated.fireEvent(EventArgs.Empty);
    }

    public void start()
    {
        if (_updateThread != null)
            return; // already running

        getCollidables().clear();

        // initialize
        setRunning(true);

        _updateThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                doUpdateWork();
            }
        }, "UpdateThread");
        _updateThread.start();
    }

    public void stop()
    {
        if (_updateThread == null)
            return; // not running
        setRunning(false);

        try
        {
            _updateThread.interrupt();
            _updateThread.join();
        }
        catch (InterruptedException e)
        {
        }
        _updateThread = null;

    }

    private void doUpdateWork()
    {
        while (isRunning())
        {
            long updateTime = getLastUpdate();
            long startTime = System.currentTimeMillis();
            updateGame();

            // only wait if we have still the same data
            if (updateTime == getLastUpdate())
            {
                // calculate how many time we need to wait
                long timeElapsed = System.currentTimeMillis() - startTime;
                long waitTime = UPDATE_INTERVAL - timeElapsed;
                if (waitTime > 0)
                {
                    try
                    {
                        Thread.sleep(waitTime);
                    }
                    catch (InterruptedException e)
                    {
                    }
                }
            }
        }
    }

    private void updateGame()
    {
        ICollidable[] collidables = getCollidables()
                .toArray(new ICollidable[0]);

        for (ICollidable collidable : collidables)
        {
            // let item update before checking collisions
            if (collidable instanceof IUpdatable)
            {
                ((IUpdatable) collidable).update();
            }
        }
        
        // store collision objects, rules can move balls, therefore we need to
        // store all collisions before we apply rules.
        List<ICollidable[]> toApply = new ArrayList<ICollidable[]>();
        
        // store all rules which will be applied
        Set<ICollisionRule> rules = new HashSet<ICollisionRule>();

        for (ICollidable collidable : collidables)
        {
            // check if current item collides with any other element
            for (ICollidable potentialCollision : collidables)
            {
                if (collidable != potentialCollision
                        && collidable.collidesWith(potentialCollision))
                {
                    // add to list of collisions
                    toApply.add(new ICollidable[] { collidable,
                            potentialCollision });
                    for (ICollisionRule iCollisionRule : collidable.getCollisionRules())
                    {
                        rules.add(iCollisionRule);
                    }
                    break;
                }
            }
        }
        
        // reset all rules
        for (ICollisionRule iCollisionRule : rules)
        {
            iCollisionRule.reset();
        }

        // apply all rules
        for (ICollidable[] current : toApply)
        {
            applyAllRules(current[0], current[1]);
        }

        _stateUpdated.fireEvent(EventArgs.Empty);
    }

    /**
     * @return
     */
    private List<ICollidable> getCollidables()
    {
        List<ICollidable> collidables = new ArrayList<ICollidable>();
        for (Entry<String, List<ICollidable>> tempCollidable : getCollidablesMapping()
                .entrySet())
        {
            collidables.addAll(tempCollidable.getValue());
        }

        return collidables;
    }

    private void applyAllRules(ICollidable toApply, ICollidable collidesWith)
    {
        for (ICollisionRule rule : toApply.getCollisionRules())
        {
            rule.apply(toApply, collidesWith);
        }
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#addHandler(at.kubatsch.util.IEventHandler)
     */
    public void addStateUpdatedHandler(IEventHandler<EventArgs> handler)
    {
        _stateUpdated.addHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#removeHandler(at.kubatsch.util.IEventHandler)
     */
    public void removeStateUpdatedHandler(IEventHandler<EventArgs> handler)
    {
        _stateUpdated.removeHandler(handler);
    }

    /**
     * 
     */
    public void reset()
    {
        _collidables.clear();
        _collidableType.clear();
    }

}
