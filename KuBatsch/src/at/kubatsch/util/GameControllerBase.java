/**
 * author: Manuel Tscholl(mts3970)
 * created on: 03.02.2011
 * filename: MainController.java
 * project: KuBaTsch
 */
package at.kubatsch.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import at.kubatsch.model.Ball;
import at.kubatsch.model.GameState;
import at.kubatsch.model.ICollidable;
import at.kubatsch.model.IUpdatable;
import at.kubatsch.model.SpecialItem;
import at.kubatsch.model.rules.ICollisionRule;

/**
 * Controller for the Collision handling, and fires updates to the listener
 * @author Manuel Tscholl (mts3970)
 */
public abstract class GameControllerBase extends Thread
{
    protected static GameControllerBase _mainController = null;
    protected static int                UPDATE_INTERVAL = 8;
    private GameState                   _currentGameState;
    protected Thread                    _updateGameState;

    private boolean                     _isRunning;
    private List<ICollidable>           _collidables;
    protected Event<EventArgs>          _stateUpdated;
    private Object                      _lastUpdateLock = new Object();
    private long                        _lastUpdate;
    private int                         _stateUpdateInterval;

    /**
     * Gets the stateUpdateInterval.
     * @return the stateUpdateInterval
     */
    public int getStateUpdateInterval()
    {
        return _stateUpdateInterval;
    }

    /**
     * Sets the stateUpdateInterval. Update all x states.
     * @param stateUpdateInterval the stateUpdateInterval to set
     */
    public void setStateUpdateInterval(int stateUpdateInterval)
    {
        _stateUpdateInterval = stateUpdateInterval;
    }

    /**
     * @return returns the last Update
     */
    public long getLastUpdate()
    {
        synchronized (_lastUpdateLock)
        {
            return _lastUpdate;
        }
    }

    /**
     * @param lastUpdate set the las update
     */
    public synchronized void setLastUpdate(long lastUpdate)
    {
        synchronized (_lastUpdateLock)
        {
            _lastUpdate = lastUpdate;
        }
    }

    /**
     * Initializes a new instance of the {@link GameControllerBase} class.
     */
    protected GameControllerBase()
    {
        _currentGameState = new GameState();
        _collidables = new ArrayList<ICollidable>();
        _stateUpdated = new Event<EventArgs>(this);

        startUpdating();
    }

    /**
     * {@link Thread} which sends in a intervall all updates
     */
    protected void updateLoop()
    {
        int count = 0;
        while (isRunning())
        {
            long updateTime = getLastUpdate();
            long startTime = System.currentTimeMillis();
            boolean anyRuleApplied = updateGameState();
            count++;

            if (anyRuleApplied || (count >= _stateUpdateInterval && getCurrentGameState() != null))
            {
                _stateUpdated.fireEvent(EventArgs.Empty);
                count = 0;
            }

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

    /**
     * Update the Gamestate
     * @return true if any collision was applied, otherwise false. 
     */
    private boolean updateGameState()
    {
        if (getCurrentGameState() == null)
            return false;
        ICollidable[] collidables = getCurrentGameState().getAllCollidables()
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
                    for (ICollisionRule iCollisionRule : collidable
                            .getCollisionRules())
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
        boolean anyRuleApplied = false;
        for (ICollidable[] current : toApply)
        {
            if(applyAllRules(current[0], current[1]))
                anyRuleApplied = true;
        }
        return anyRuleApplied;
    }

    /**
     * Apply all rules to the Collidables
     * @param toApply which you want to apply the rule
     * @param collidesWith which collides with the Collidable
     */
    public static boolean applyAllRules(ICollidable toApply,
            ICollidable collidesWith)
    {
        boolean anyRuleApplied = false;
        for (ICollisionRule rule : toApply.getCollisionRules())
        {
            if(rule.apply(toApply, collidesWith))
                anyRuleApplied = true;
        }
        return anyRuleApplied;
    }

    /**
     * Gets the currentGameState.
     * @return the currentGameState
     */
    public synchronized GameState getCurrentGameState()
    {
        return _currentGameState;
    }

    /**
     * Sets the currentGameState.
     * @param currentGameState the currentGameState to set
     */
    public synchronized void setCurrentGameState(GameState currentGameState)
    {
        _currentGameState = currentGameState;
        _stateUpdated.fireEvent(EventArgs.Empty);
    }

    /**
     * Add a new ball to the currentgamestate
     * @param ballToAdd ball you want to add
     */
    public void addBall(Ball ballToAdd)
    {
        getCurrentGameState().getBalls().add(ballToAdd);
    }

    /**
     * Add a specialItem
     * @param item the special Item you want to add
     */
    public void addSpecialItem(SpecialItem item)
    {
        getCurrentGameState().getSpecialItems().add(item);
    }

    /**
     * Remove a ball from the game
     * @param ballToRemove the ball which you want to remove
     */
    public void removeBall(Ball ballToRemove)
    {
        getCurrentGameState().getBalls().remove(ballToRemove);
    }

    /**
     * Remove a specialitem
     * @param itemToRemove you want to remove
     */
    public void removeSpecialItems(SpecialItem itemToRemove)
    {
        getCurrentGameState().getSpecialItems().remove(itemToRemove);
    }

    /**
     * Gets the isRunning.
     * @return the isRunning
     */
    public boolean isRunning()
    {
        return _isRunning;
    }

    /**
     * Sets the isRunning.
     * @param isRunning the isRunning to set
     */
    public void setRunning(boolean isRunning)
    {
        _isRunning = isRunning;
    }

    /**
     * Gets the collidables.
     * @return the collidables
     */
    protected List<ICollidable> getCollidables()
    {
        return _collidables;
    }

    /**
     * @see java.lang.Thread#run()
     */
    @Override
    public void run()
    {
        runGame();
    }

    protected abstract void runGame();

    /**
     * @param handler
     * @see at.kubatsch.util.Event#addHandler(at.kubatsch.util.IEventHandler)
     */
    public void addStateUpdatedListener(IEventHandler<EventArgs> handler)
    {
        _stateUpdated.addHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#removeHandler(at.kubatsch.util.IEventHandler)
     */
    public void removeStateUpdatedListener(IEventHandler<EventArgs> handler)
    {
        _stateUpdated.removeHandler(handler);
    }

    /**
     * Update the gamestate
     */
    public void startUpdating()
    {
        if (_updateGameState != null)
            return; // already running

        System.out.println("Start Updating");
        setCurrentGameState(null);

        // initialize
        setRunning(true);

        _updateGameState = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                updateLoop();
            }
        }, "UpdateThread");
        _updateGameState.start();
    }

    /**
     * suspend updating the gamestate
     */
    public void suspendUpdating()
    {
        if (_updateGameState == null)
            return; // not running
        setRunning(false);
        System.out.println("Stop updating");

        try
        {
            _updateGameState.interrupt();
            _updateGameState.join();
        }
        catch (InterruptedException e)
        {
        }
        _updateGameState = null;
    }
}
