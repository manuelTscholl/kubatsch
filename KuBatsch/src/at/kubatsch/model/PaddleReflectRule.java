/**
 * author: Daniel Kuschny (dku2375)
 * created on: 03.02.2011
 * filename: PaddleReflectRule.java
 * project: KuBatsch
 */
package at.kubatsch.model;

import java.awt.geom.Point2D;

import at.kubatsch.samples.motion.Paddle;

/**
 * This rule reflects a ball if it collides with a panel.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class PaddleReflectRule extends AbstractRule
{
    private static final float LEFT_EDGE_ANGLE  = 145;
    private static final float RIGHT_EDGE_ANGLE = 25;
    private static final float GRAD_RAD_FACTOR  = (float) Math.PI / 180;

    /**
     * 
     */
    private static final long  serialVersionUID = 8671041808905399550L;

    /**
     * @see at.kubatsch.model.AbstractRule#apply(at.kubatsch.model.ICollidable,
     *      at.kubatsch.model.ICollidable)
     */
    @Override
    public void apply(ICollidable toApply, ICollidable collidesWith)
    {
        if ((collidesWith instanceof Ball) && (toApply instanceof Paddle))
        {
            Paddle paddle = (Paddle) toApply;
            Point2D.Float paddlePos = paddle.getPosition();
            Ball ball = (Ball) collidesWith;
            Point2D.Float ballPos = ball.getPosition();

            float paddleWidth = 0;
            float ballHeight = 0;
            float ballHitX = 0;

            float newAngle = 0;
            float inSpeed = 0;

            float bounceOffset = 0.0001f;
            switch (paddle.getPaddlePosition())
            {
                case NORTH:

                    // get sizes
                    paddleWidth = paddle.getMaxPoint().x
                            - paddle.getMinPoint().x;
                    ballHeight = ball.getMaxPoint().y - ball.getMinPoint().y;
                    ballHitX = ((paddlePos.x + paddle.getMaxPoint().x) - ballPos.x)
                            / paddleWidth;

                    // calculate which angle to use now
                    newAngle = (RIGHT_EDGE_ANGLE - LEFT_EDGE_ANGLE) * ballHitX
                            + LEFT_EDGE_ANGLE;
                    inSpeed = (float) Math.sqrt(ball.getVelocity().x
                            * ball.getVelocity().x + ball.getVelocity().y
                            * ball.getVelocity().y);

                    // update velocity and position
                    ball.setVelocity(
                            -(inSpeed * (float) Math.cos(newAngle
                                    * GRAD_RAD_FACTOR)),
                            (inSpeed * (float) Math.sin(newAngle
                                    * GRAD_RAD_FACTOR)));
                    ball.setPosition(ball.getPosition().x,
                            paddlePos.y + paddle.getMaxPoint().y + bounceOffset
                                    - ball.getMinPoint().y);

                    break;
                case SOUTH:

                    // get sizes
                    paddleWidth = paddle.getMaxPoint().x
                            - paddle.getMinPoint().x;
                    ballHeight = ball.getMaxPoint().y - ball.getMinPoint().y;
                    ballHitX = (ballPos.x - paddlePos.x) / paddleWidth;

                    // calculate which angle to use now
                    newAngle = (RIGHT_EDGE_ANGLE - LEFT_EDGE_ANGLE) * ballHitX
                            + LEFT_EDGE_ANGLE;
                    inSpeed = (float) Math.sqrt(ball.getVelocity().x
                            * ball.getVelocity().x + ball.getVelocity().y
                            * ball.getVelocity().y);

                    // update velocity and position
                    ball.setVelocity(
                            inSpeed
                                    * (float) Math.cos(newAngle
                                            * GRAD_RAD_FACTOR),
                            -(inSpeed * (float) Math.sin(newAngle
                                    * GRAD_RAD_FACTOR)));
                    ball.setPosition(ball.getPosition().x,
                            paddlePos.y + paddle.getMinPoint().y - bounceOffset
                                    - ballHeight);

                    break;
                case EAST:

                    // get sizes
                    paddleWidth = paddle.getMaxPoint().y
                            - paddle.getMinPoint().y;
                    ballHeight = ball.getMaxPoint().x - ball.getMinPoint().x;
                    ballHitX = ((paddlePos.y + paddle.getMaxPoint().y) - ballPos.y)
                            / paddleWidth;

                    // calculate which angle to use now
                    newAngle = (RIGHT_EDGE_ANGLE - LEFT_EDGE_ANGLE) * ballHitX
                            + LEFT_EDGE_ANGLE;
                    // rotate 90° counterclockwise
                    newAngle -= 90;
                    inSpeed = (float) Math.sqrt(ball.getVelocity().x
                            * ball.getVelocity().x + ball.getVelocity().y
                            * ball.getVelocity().y);

                    // update velocity and position
                    ball.setVelocity(
                            -(inSpeed * (float) Math.cos(newAngle
                                    * GRAD_RAD_FACTOR)),
                            (inSpeed * (float) Math.sin(newAngle
                                    * GRAD_RAD_FACTOR)));

                    ball.setPosition(paddlePos.x - ballHeight - bounceOffset,
                            ballPos.y);

                    break;
                case WEST:

                    // get sizes
                    paddleWidth = paddle.getMaxPoint().y
                            - paddle.getMinPoint().y;
                    ballHeight = ball.getMaxPoint().x - ball.getMinPoint().x;
                    ballHitX = (ballPos.y - paddlePos.y) / paddleWidth;

                    // calculate which angle to use now
                    newAngle = (RIGHT_EDGE_ANGLE - LEFT_EDGE_ANGLE) * ballHitX
                            + LEFT_EDGE_ANGLE;
                    // rotate 90° clockwise
                    newAngle += 90;
                    inSpeed = (float) Math.sqrt(ball.getVelocity().x
                            * ball.getVelocity().x + ball.getVelocity().y
                            * ball.getVelocity().y);

                    // update velocity and position
                    ball.setVelocity(
                            -(inSpeed * (float) Math.cos(newAngle
                                    * GRAD_RAD_FACTOR)),
                            (inSpeed * (float) Math.sin(newAngle
                                    * GRAD_RAD_FACTOR)));
                    ball.setPosition(paddlePos.x + paddle.getMaxPoint().x
                            + bounceOffset - ball.getMinPoint().x, ballPos.y);

                    break;
            }

            ball.setColor(Color.getColor(ball.getColor().getIndex() + 1));
        }
    }

}
