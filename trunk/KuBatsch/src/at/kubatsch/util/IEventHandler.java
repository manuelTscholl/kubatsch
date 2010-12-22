/**
 * This file is part of KuBatsch.
 *   created on: 22.12.2010
 *   filename: IEventHandler.java
 *   project: KuBatsch
 */
package at.kubatsch.util;

import java.util.EventListener;

/**
 * An {@link IEventHandler} pepresents an {@link EventListener} which can
 * receive an event notification fired by an {@link Event}. 
 * 
 * {@link IEventHandler} listeners receive the sender of an event and 
 * a {@link EventArgs} object containing the Event MetaData.
 * 
 * @author Daniel Kuschny (dku2375)
 * 
 */
public interface IEventHandler<T extends EventArgs> extends EventListener
{
    /**
     * This method is called as the event is fired.
     * @param sender The sender of the event.
     * @param e The {@link EventArgs} instance containing the event's metadata.
     */
    public void fired(Object sender, T e);
}
