/**
 * author: Daniel Kuschny (dku2375)
 * created on: 28.01.2011
 * filename: PlayTickRule.java
 * project: KuBatsch
 */
package at.kubatsch.model;

import at.kubatsch.client.controller.AudioController;

/**
 * A rule which plays a sound if a ball collides with anything
 * @author Daniel Kuschny (dku2375)
 *
 */
public class PlayTickRule extends AbstractRule
{
    /**
     * @see at.kubatsch.model.ICollisionRule#apply(at.kubatsch.model.ICollidable, at.kubatsch.model.ICollidable)
     */
    @Override
    public void apply(ICollidable toApply, ICollidable collidesWith)
    {
        System.out.println("Play Tick");
        AudioController.getInstance().playTick();
    }
}
