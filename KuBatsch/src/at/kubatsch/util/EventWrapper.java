/**
 * This file is part of KuBatsch.
 *   created on: 22.12.2010
 *   filename: EventWrapper.java
 *   project: KuBatsch
 */
package at.kubatsch.util;

/**
 * This class is used to provide a public interface to {@link Event}
 * subscribers. It ensures the {@link Event} can only be raised within it's
 * owner. 
 * @author Daniel Kuschny (dku2375)
 */
// using this wrapper reduces the amount of code 
// required to create a new event
public class EventWrapper<T extends EventArgs>
{
    private Event<T> _event;
    
    /**
     * Initializes a new instance of the {@link EventWrapper} class.
     * @param event The event which should be used for adding new handlers.
     */
    public EventWrapper(Event<T> event)
    {
        _event = event;
    }
    
    /**
     * Adds a new {@link IEventHandler} to the list of items which 
     * will be notified as the event get's fired.
     * @param handler the listener to be added
     */
    public void addHandler(IEventHandler<T> handler)
    {
        _event.addHandler(handler);
    }
    
    /**
     * Removes the specified {@link IEventHandler} from the list of items which 
     * will be notified as the event get's fired.
     * @param handler the listener to be removed
     */
    public void removeHandler(IEventHandler<T> handler)
    {
        _event.removeHandler(handler);
    }
}
