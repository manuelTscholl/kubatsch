/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: SimpleReflectRule.java
 *   project: KuBatsch
 */
package at.kubatsch.model;

/**
 * This rule describes a simple input-vector -> output-vector translation of a hitarea and a ball. 
 * Depending on the wall the ball hits onto, the x or y velocity will be inverted.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class HitPanelReflectRule extends AbstractRule
{
    /**
     * 
     */
    private static final long serialVersionUID = 468589354943752426L;

    /**
     * @see at.kubatsch.model.ICollisionRule#apply(at.kubatsch.model.ICollidable, at.kubatsch.model.ICollidable)
     */
    @Override
    public void apply(ICollidable toApply, ICollidable collidesWith)
    {
        if((collidesWith instanceof Ball) && (toApply instanceof PlayerHitArea))
        {
            PlayerHitArea hitArea = (PlayerHitArea)toApply;
            Ball ball = (Ball)collidesWith;

            float bounceOffset = 0.0002f;
            // TODO: Bounce position is not correct. need to place exactly the nearest collision point 
            switch(hitArea.getHitAreaPosition())
            {
                case NORTH:
                    ball.setVelocity(ball.getVelocity().x, -ball.getVelocity().y);
                    ball.setPosition(ball.getPosition().x, hitArea.getMaxPoint().y + bounceOffset);
                    break;
                case SOUTH:
                    ball.setVelocity(ball.getVelocity().x, -ball.getVelocity().y);
                    ball.setPosition(ball.getPosition().x, hitArea.getMinPoint().y - bounceOffset - ball.getSize());
                    break;
                case EAST:
                    ball.setVelocity(-ball.getVelocity().x, ball.getVelocity().y);
                    ball.setPosition(hitArea.getMinPoint().x - bounceOffset - ball.getSize(), 
                            ball.getPosition().y);
                    break;
                case WEST:
                    ball.setVelocity(-ball.getVelocity().x, ball.getVelocity().y);
                    ball.setPosition(hitArea.getMaxPoint().x + bounceOffset,
                            ball.getPosition().y);
                    break;
            }
            
            ball.setColor(Color.getColor(ball.getColor().getIndex() + 1));
        }
    }
}
