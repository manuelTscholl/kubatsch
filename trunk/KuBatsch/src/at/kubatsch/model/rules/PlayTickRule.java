/**
 * author: Daniel Kuschny (dku2375)
 * created on: 28.01.2011
 * filename: PlayTickRule.java
 * project: KuBatsch
 */
package at.kubatsch.model.rules;

import at.kubatsch.client.controller.AudioController;
import at.kubatsch.model.ICollidable;

/**
 * A rule which plays a sound if a ball collides with anything
 * @author Daniel Kuschny (dku2375)
 *
 */
public class PlayTickRule extends AbstractRule
{
    private static final long serialVersionUID = 6125514617720936444L;

    /**
     * @see at.kubatsch.model.rules.ICollisionRule#apply(at.kubatsch.model.ICollidable, at.kubatsch.model.ICollidable)
     */
    @Override
    public void apply(ICollidable toApply, ICollidable collidesWith)
    {
        AudioController.getInstance().playTick();
    }
}
