/**
 * This file is part of KuBatsch.
 *   created on: 25.01.2011
 *   filename: BallBallCollision.java
 *   project: KuBatsch
 */
package at.kubatsch.model;

import java.awt.geom.Point2D;

/**
 * A collision rule which applies if two balls collide.
 * The two balls will change their reflect according to their velocity vectors. 
 * The first ball will takeover the direction of the second ball.
 * The second ball will takeover the direction of the first ball. 
 * Both balls will keep their velocity. 
 * @author Daniel Kuschny (dku2375)
 */
public class BallBallCollisionRule extends BallRule
{

    /**
     * @see at.kubatsch.model.BallRule#apply(at.kubatsch.model.Ball,
     *      at.kubatsch.model.ICollidable)
     */
    @Override
    protected void apply(Ball toApply, ICollidable collidesWith)
    {
        if (collidesWith instanceof Ball) // collides with another ball?
        {
            Ball otherBall = (Ball) collidesWith;

            // calculate length of vectors
            float v1 = (float) Math.sqrt((toApply.getVelocity().x * toApply
                    .getVelocity().x)
                    + (toApply.getVelocity().y * toApply.getVelocity().y));
            float v2 = (float) Math.sqrt((otherBall.getVelocity().x * otherBall
                    .getVelocity().x)
                    + (otherBall.getVelocity().y * otherBall.getVelocity().y));
            
            
            // calculate entity vectors 
            Point2D.Float entity1 = new Point2D.Float(toApply.getVelocity().x / v1, toApply.getVelocity().y / v1);
            Point2D.Float entity2 = new Point2D.Float(otherBall.getVelocity().x / v2, otherBall.getVelocity().y / v2);
            
            toApply.setVelocity(entity2.x * v1, entity2.y * v1);
            otherBall.setVelocity(entity1.x * v2, entity1.y * v2);
            
            toApply.update();
            otherBall.update();
        }
    }
}
