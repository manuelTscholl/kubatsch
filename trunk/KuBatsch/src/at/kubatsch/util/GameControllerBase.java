/**
 * author: Manuel Tscholl(mts3970)
 * created on: 03.02.2011
 * filename: MainController.java
 * project: KuBaTsch
 */
package at.kubatsch.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import at.kubatsch.model.Ball;
import at.kubatsch.model.GameState;
import at.kubatsch.model.ICollidable;
import at.kubatsch.model.ICollisionRule;
import at.kubatsch.model.IUpdatable;
import at.kubatsch.model.SpecialItem;

/**
 * @author Manuel Tscholl (mts3970)
 * 
 */
public abstract class GameControllerBase
{
    protected static GameControllerBase _mainController         = null;
    protected static int                UPDATE_INTERVAL = 30;
    private GameState                   _currentGameState;
    protected Thread                    _updateGameState;
    
    private boolean                     _isRunning;
    private List<ICollidable>           _collidables;
    protected Event<EventArgs>          _stateUpdated;
    private Object                      _lastUpdateLock         = new Object();
    private long                        _lastUpdate;

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
     * 
     * Initializes a new instance of the {@link GameControllerBase} class.
     * @param portToListen
     * @throws IOException
     */
    protected GameControllerBase() throws IOException
    {
        _currentGameState = new GameState();
        _collidables = new ArrayList<ICollidable>();
        _stateUpdated = new Event<EventArgs>(this);

        setRunning(true);
        _updateGameState = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                long updateTime = getLastUpdate();
                long startTime = System.currentTimeMillis();
                updateGameState();

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
        });
        _updateGameState.setName("updateGameState");
        _updateGameState.start();
    }

    private void updateGameState()
    {

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
        for (ICollidable[] current : toApply)
        {
            applyAllRules(current[0], current[1]);
        }

        _stateUpdated.fireEvent(EventArgs.Empty);

    }

    private void applyAllRules(ICollidable toApply, ICollidable collidesWith)
    {
        for (ICollisionRule rule : toApply.getCollisionRules())
        {
            rule.apply(toApply, collidesWith);
        }
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
    }

    public void addBall(Ball ballToAdd)
    {
        getCurrentGameState().getBalls().add(ballToAdd);
    }

    public void addSpecialItem(SpecialItem item)
    {
        getCurrentGameState().getSpecialItems().add(item);
    }

    public void removeBall(Ball ballToRemove)
    {
        getCurrentGameState().getBalls().remove(ballToRemove);
    }

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
    
    

    // Countdown
    // Regeln f�r spielrunde
    // Kollision berechnen
    // start
    // stoppen

}
