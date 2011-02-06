/**
 * author: Daniel Kuschny (dku2375)
 * created on: 29.01.2011
 * filename: AbstractRule.java
 * project: KuBatsch
 */
package at.kubatsch.model.rules;

import at.kubatsch.model.ICollidable;

/**
 * An empty rule implementation.
 * @author Daniel Kuschny (dku2375)
 *
 */
public abstract class AbstractRule implements ICollisionRule
{
    private static final long serialVersionUID = -2226125330938730864L;

    /**
     * @see at.kubatsch.model.rules.ICollisionRule#reset()
     */
    @Override
    public void reset()
    {
    }

    /**
     * @see at.kubatsch.model.rules.ICollisionRule#apply(at.kubatsch.model.ICollidable, at.kubatsch.model.ICollidable)
     */
    @Override
    public void apply(ICollidable toApply, ICollidable collidesWith)
    {
    }
}
