/**
 * This file is part of KuBatsch.
 *   created on: 25.01.2011
 *   filename: CollisionController.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.collisionperformance;

import java.util.ArrayList;
import java.util.List;

import at.kubatsch.model.ICollidable;
import at.kubatsch.model.ICollisionRule;
import at.kubatsch.model.IUpdatable;
import at.kubatsch.util.Event;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

/**
 * A controller performing collision detection in some kind of game loop where
 * all {@link IUpdatable}s will updated too.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class CollisionController
{
    private List<ICollidable> _collidables;
    private Thread            _updateThread;
    private boolean           _running;

    private Event<EventArgs>  _stateUpdated = new Event<EventArgs>(this);

    /**
     * Initializes a new instance of the {@link CollisionController} class.
     */
    public CollisionController()
    {
        _collidables = new ArrayList<ICollidable>();
    }

    /**
     * Gets a value indicating whether the controller is running.
     * @return
     */
    public synchronized boolean isRunning()
    {
        return _running;
    }

    /**
     * Sets whether the controller is running.
     * @param running
     */
    public synchronized void setRunning(boolean running)
    {
        _running = running;
    }

    /**
     * Gets a list of collidables within this controller.
     * @return
     */
    public synchronized List<ICollidable> getCollidables()
    {
        return _collidables;
    }

    /**
     * Adds a new collidable to the controller.
     * @param collidable the collidable to add
     */
    public void addCollidable(ICollidable collidable)
    {
        getCollidables().add(collidable);
        _stateUpdated.fireEvent(EventArgs.Empty);
    }

    /**
     * Removes the specified collidable from the controller.
     * @param collidable the collidable to remove
     */
    public void removeCollidable(ICollidable collidable)
    {
        getCollidables().remove(collidable);
        _stateUpdated.fireEvent(EventArgs.Empty);
    }

    /**
     * Starts the execution loop.
     */
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

    /**
     * Stopps the execution loop.
     */
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

    /**
     * The application loop which updates the states.
     */
    private void doUpdateWork()
    {
        while (isRunning())
        {
            updateState();
            try
            {
                Thread.sleep(30);
            }
            catch (InterruptedException e)
            {
            }
        }
    }

    /**
     * Updates the current state by updating all {@link IUpdatable}s and
     * performing a collisiondetection and applying all {@link ICollisionRule}.
     */
    private void updateState()
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
     * Applies all rules of the specified element. 
     * @param toApply the element containing the rules. 
     * @param collidesWith the element which collided with .
     */
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
