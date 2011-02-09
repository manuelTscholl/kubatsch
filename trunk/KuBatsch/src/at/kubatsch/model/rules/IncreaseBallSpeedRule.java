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
 * This rule increases  the speed of the ball
 * @author Daniel Kuschny (dku2375)
 *
 */
public class IncreaseBallSpeedRule extends BallRule
{
    private static final long  serialVersionUID = 2292702591434491876L;

    public static final float  MAX_SPEED        = 0.013f;
    private static final float SPEED_STEP       = 0.0003f;

    /**
     * @see at.kubatsch.model.rules.BallRule#apply(at.kubatsch.model.Ball, at.kubatsch.model.ICollidable)
     */
    @Override
    protected boolean apply(Ball toApply, ICollidable collidesWith)
    {
        // don't be faster than the max speed
        float speed = (float) Math.sqrt(toApply.getVelocity().x * toApply.getVelocity().x
                + toApply.getVelocity().y * toApply.getVelocity().y);
        if (speed >= MAX_SPEED)
        {
            return false;
        }

        float stepX = SPEED_STEP;
        if (toApply.getVelocity().x < 0)
            stepX = -stepX;
        float stepY = SPEED_STEP;
        if (toApply.getVelocity().y < 0)
            stepY = -stepY;
        toApply.setVelocity(toApply.getVelocity().x + stepX, toApply.getVelocity().y
                + stepY);
        return true;
    }
}
