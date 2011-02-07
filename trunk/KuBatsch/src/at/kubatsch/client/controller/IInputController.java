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
 */
public interface IInputController
{
    /**
     * Returns the Current Position of the client where he is.
     * @return position of the client
     */
    public float getCurrentPosition();
    
    /**
     * Add a Position Changed Listner
     * @param handler EventHandler
     */
    public void addPositionChangedListener(IEventHandler<EventArgs> handler);
    
    /**
     * Remove the Position Changed Listner
     * @param handler which you want remove
     */
    public void removePositionChangedListener(IEventHandler<EventArgs> handler);
    
    /**
     * Enable the InputController
     */
    public void enable();
    
    /**
     * Disable the InputController
     */
    public void disable();
}
