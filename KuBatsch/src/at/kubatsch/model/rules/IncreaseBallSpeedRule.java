/**
 * author: Daniel Kuschny (dku2375)
 * created on: 04.02.2011
 * filename: IncreaseSpeedRule.java
 * project: KuBatsch
 */
package at.kubatsch.model.rules;

import at.kubatsch.model.Ball;
import at.kubatsch.model.ICollidable;

/**
 * @author Daniel Kuschny (dku2375)
 *
 */
public class IncreaseBallSpeedRule extends BallRule
{
    /**
     * 
     */
    private static final long serialVersionUID = 2292702591434491876L;
    private static final float MAX_SPEED = 0.05f;
    private static final float SPEED_STEP = 1.05f;
    /**
     * @see at.kubatsch.model.rules.BallRule#apply(at.kubatsch.model.Ball, at.kubatsch.model.ICollidable)
     */
    @Override
    protected void apply(Ball toApply, ICollidable collidesWith)
    {
        // don't be faster than the max speed
        float speed = (float)Math.sqrt(toApply.getVelocity().x*toApply.getVelocity().x + toApply.getVelocity().y*toApply.getVelocity().y);
        if(speed >= MAX_SPEED) return;
         
        toApply.setVelocity(toApply.getVelocity().x*SPEED_STEP,
                            toApply.getVelocity().y*SPEED_STEP);
        
        speed = (float)Math.sqrt(toApply.getVelocity().x*toApply.getVelocity().x + toApply.getVelocity().y*toApply.getVelocity().y);
    }
}
