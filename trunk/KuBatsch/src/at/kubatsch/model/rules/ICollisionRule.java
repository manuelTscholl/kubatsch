/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: ICollisionRule.java
 *   project: KuBatsch
 */
package at.kubatsch.model.rules;

import java.io.Serializable;

import at.kubatsch.model.ICollidable;

/**
 * A collision rule will be as soon a {@link ICollidable} collides with another
 * {@link ICollidable}.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public interface ICollisionRule extends Serializable
{
    /**
     * A rule is reset within any gameloop interval. 
     * This allows to store any metadata in a rule during one timeslice. 
     */
    public void reset();
    
    
    /**
     * Called as soon the rule needs to be applied. 
     * @param toApply The element which the rule is assigned to. 
     * @param collidesWith The element which the first element collided with. 
     */
    public void apply(ICollidable toApply, ICollidable collidesWith);
}
