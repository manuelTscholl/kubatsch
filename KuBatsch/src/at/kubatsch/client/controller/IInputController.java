/**
 * author: Daniel Kuschny (dku2375)
 * created on: 04.02.2011
 * filename: IInputController.java
 * project: KuBatsch
 */
package at.kubatsch.client.controller;

import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

/**
 * The base interface for creating new input controllers which 
 * can controll the player inputs.
 * @author Daniel Kuschny (dku2375)
 *
 */
public interface IInputController
{
    public float getCurrentPosition();
    public void addPositionChangedListener(IEventHandler<EventArgs> handler);
    public void removePositionChangedListener(IEventHandler<EventArgs> handler);
    
    public void enable();
    public void disable();
}
