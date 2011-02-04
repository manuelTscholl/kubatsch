/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: BallRule.java
 *   project: KuBatsch
 */
package at.kubatsch.model.rules;

import at.kubatsch.model.AbstractRule;
import at.kubatsch.model.Ball;
import at.kubatsch.model.ICollidable;
import at.kubatsch.model.ICollisionRule;

/**
 * This is the base class for creating rules which will be applied to a ball.
 * The {@link BallRule#apply(Ball, ICollidable)} Method will be called if the
 * rule is assigned to a ball and the rule needs to be applied. 
 * @author Daniel Kuschny (dku2375)
 *
 */
public abstract class BallRule extends AbstractRule
{

    /**
     * 
     */
    private static final long serialVersionUID = -5499704803685187836L;

    /**
     * @see at.kubatsch.model.ICollisionRule#apply(at.kubatsch.model.ICollidable, at.kubatsch.model.ICollidable)
     */
    @Override
    public void apply(ICollidable toApply, ICollidable collidesWith)
    {
        if(toApply instanceof Ball)
        {
            apply((Ball)toApply, collidesWith);
        }
    }
    
    /**
     * Applies the current rule to the specified ball. This method is called
     * as the specified ball collided with the {@link ICollidable} provided by the second parameter. 
     * @param toApply The ball which collided with a {@link ICollidable}.
     * @param collidesWith the {@link ICollidable} the ball colided with. 
     */
    protected abstract void apply(Ball toApply, ICollidable collidesWith);
}
