/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: ICollisionRule.java
 *   project: KuBatsch
 */
package at.kubatsch.model;

/**
 * A collision rule will be as soon a {@link ICollidable} collides with another
 * {@link ICollidable}.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public interface ICollisionRule
{
    /**
     * Called as soon the rule needs to be applied. 
     * @param toApply The element which the rule is assigned to. 
     * @param collidesWith The element which the first element collided with. 
     */
    public void apply(ICollidable toApply, ICollidable collidesWith);
}
