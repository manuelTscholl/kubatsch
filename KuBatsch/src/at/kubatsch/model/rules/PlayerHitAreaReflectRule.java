/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: SimpleReflectRule.java
 *   project: KuBatsch
 */
package at.kubatsch.model.rules;

import at.kubatsch.model.Ball;
import at.kubatsch.model.ICollidable;
import at.kubatsch.model.PlayerHitArea;

/**
 * This rule describes a simple input-vector -> output-vector translation of a hitarea and a ball. 
 * Depending on the wall the ball hits onto, the x or y velocity will be inverted.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class PlayerHitAreaReflectRule extends AbstractRule
{
    private static final long  serialVersionUID = 468589354943752426L;

    private static final float HEALTH_DECREASE  = 0.2f;

    /**
     * @see at.kubatsch.model.rules.ICollisionRule#apply(at.kubatsch.model.ICollidable, at.kubatsch.model.ICollidable)
     */
    @Override
    public boolean apply(ICollidable toApply, ICollidable collidesWith)
    {
        if ((collidesWith instanceof Ball) && (toApply instanceof PlayerHitArea))
        {
            PlayerHitArea hitArea = (PlayerHitArea) toApply;
            Ball ball = (Ball) collidesWith;

            float realBallHeight = ball.getMaxPoint().y - ball.getMinPoint().y;
            float realBallWidth = ball.getMaxPoint().x - ball.getMinPoint().x;

            if (ball.collidesWith(hitArea.getPlayer().getPaddle()))
                return false;

            float bounceOffset = 0.005f;
            switch (hitArea.getHitAreaPosition())
            {
                case NORTH:
                    ball.setVelocity(ball.getVelocity().x, -ball.getVelocity().y);
                    ball.setPosition(ball.getPosition().x, hitArea.getMaxPoint().y
                            + bounceOffset - ball.getMinPoint().y);
                    break;
                case SOUTH:
                    ball.setVelocity(ball.getVelocity().x, -ball.getVelocity().y);
                    ball.setPosition(ball.getPosition().x, hitArea.getMinPoint().y
                            - bounceOffset - realBallHeight);
                    break;
                case EAST:
                    ball.setVelocity(-ball.getVelocity().x, ball.getVelocity().y);
                    ball.setPosition(hitArea.getMinPoint().x - bounceOffset
                            - realBallWidth, ball.getPosition().y);
                    break;
                case WEST:
                    ball.setVelocity(-ball.getVelocity().x, ball.getVelocity().y);
                    ball.setPosition(
                            hitArea.getMaxPoint().x + bounceOffset - ball.getMinPoint().x,
                            ball.getPosition().y);
                    break;
            }

            if (hitArea.getPlayer().isAlive())
            {
                // -20 health if speed = MAX_SPEED
                // x health if speed
                float speed = (float) Math.sqrt(ball.getVelocity().x
                        * ball.getVelocity().x + ball.getVelocity().y
                        * ball.getVelocity().y);

                float health = (speed * HEALTH_DECREASE)
                        / IncreaseBallSpeedRule.MAX_SPEED;

                hitArea.getPlayer().setHealth(hitArea.getPlayer().getHealth() - health);
            }
            return true;
        }
        return false;
    }
}
