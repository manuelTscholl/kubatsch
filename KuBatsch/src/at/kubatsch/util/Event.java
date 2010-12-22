/**
 * This file is part of KuBatsch.
 *   created on: 22.12.2010
 *   filename: Event.java
 *   project: KuBatsch
 */
package at.kubatsch.util;

import javax.swing.event.EventListenerList;


/**
 * This class is used to create a new event which notifies a list of
 * {@link IEventHandler} objects. 
 * Used with an {@link EventWrapper} to encapsulate the possibilty
 * of raising an event.
 * 
 * @author Daniel Kuschny (dku2375)
 * 
 * @param <T> The type of {@link EventArgs} passed to the {@link IEventHandler}s.
 */
public class Event<T extends EventArgs> 
{
    private EventListenerList _listeners;
    private Object _sender;
    
    /**
     * Initializes a new instance of the {@link Event} class.
     * @param sender The owner or sender of this event.
     */
    public Event(Object sender)
    {
        _sender = sender;
        _listeners = new EventListenerList();
    }
    
    /**
     * Adds a new {@link IEventHandler} to the list of items which 
     * will be notified as the event get's fired.
     * @param handler the listener to be added
     */
    public void addHandler(IEventHandler<T> handler)
    {
        _listeners.add(IEventHandler.class, handler);
    }
    
    /**
     * Removes the specified {@link IEventHandler} from the list of items which 
     * will be notified as the event get's fired.
     * @param handler the listener to be removed
     */
    public void removeHandler(IEventHandler<T> handler)
    {
        _listeners.remove(IEventHandler.class, handler);
    }
    
    /**
     * Gets a list of the {@link IEventHandler}s registered in this
     * event.
     * @return the list of {@link IEventHandler}s
     */
    @SuppressWarnings("unchecked")
    public IEventHandler<T>[] getHandlers()
    {
        return _listeners.getListeners(IEventHandler.class);
    }
    
    /**
     * Fires the event and notifies all registered
     * {@link IEventHandler}s using the specified {@link EventArgs} instance.
     * @param e The {@link EventArgs} instance to send to all items.
     */
    public void fireEvent(T e)
    {
        // call all event handlers
        for (IEventHandler<T> handler : getHandlers())
        {
            handler.fired(_sender, e);
        }
    }
}
