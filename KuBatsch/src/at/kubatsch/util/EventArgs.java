/**
 * This file is part of KuBatsch.
 *   created on: 22.12.2010
 *   filename: EventArgs.java
 *   project: KuBatsch
 */
package at.kubatsch.util;

/**
 * {@link EventArgs} is the base class for classes containing event data.
 * 
 * This class contains no event data. Derived types are used to pass metadata to
 * an {@link IEventHandler} as an {@link Event} is raised.
 * 
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class EventArgs
{
    /**
     * An empty {@link EventArgs} object containing no event data.
     */
    public static final EventArgs Empty = new EventArgs();

}
