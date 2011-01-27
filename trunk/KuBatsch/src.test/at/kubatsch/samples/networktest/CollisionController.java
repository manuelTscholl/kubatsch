/**
 * This file is part of KuBatsch.
 *   created on: 25.01.2011
 *   filename: CollisionController.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.networktest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
    private Map<String, List<ICollidable>> _collidables;
    private Map<ICollidable, String>       _collidableType;
    private Thread                         _updateThread;
    private boolean                        _running;

    private Event<EventArgs>               _stateUpdated = new Event<EventArgs>(
                                                                 this);

    /**
     * Initializes a new instance of the {@link CollisionController} class.
     */
    public CollisionController()
    {
        _collidables = new HashMap<String, List<ICollidable>>();
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
            getCollidablesMapping().put(type, new ArrayList());        
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
        getCollidablesMapping().get(_collidableType.get(collidable)).remove(collidable);
        _collidableType.remove(collidable);
        _stateUpdated.fireEvent(EventArgs.Empty);
    }

    public void start()
    {
        if (_updateThread != null)
            return; // already running

        // initialize
        setRunning(true);

        _updateThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                doUpdateWork();
            }
        });
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
    }

    private void doUpdateWork()
    {
        while (isRunning())
        {
            updateGame();
            try
            {
                Thread.sleep(30);
            }
            catch (InterruptedException e)
            {
            }
        }
    }

    private void updateGame()
    {
        ICollidable[] collidables = getCollidables().toArray(
                new ICollidable[0]);

        for (ICollidable collidable : collidables)
        {
            // let item update before checking collisions
            if (collidable instanceof IUpdatable)
            {
                ((IUpdatable) collidable).update();
            }
        }

        for (ICollidable collidable : collidables)
        {
            // check if current item collides with any other element
            for (ICollidable potentialCollision : collidables)
            {
                if (collidable != potentialCollision
                        && collidable.collidesWith(potentialCollision))
                {
                    // if we collide, apply rules
                    applyAllRules(collidable, potentialCollision);
                    break;
                }
            }
        }
        _stateUpdated.fireEvent(EventArgs.Empty);
    }

    /**
     * @return
     */
    private List<ICollidable> getCollidables()
    {
        List<ICollidable> collidables = new ArrayList<ICollidable>();
        for (Entry<String, List<ICollidable>> tempCollidable : getCollidablesMapping().entrySet())
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

}
