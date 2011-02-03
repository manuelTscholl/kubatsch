/**
 * author: Manuel Tscholl(mts3970)
 * created on: 03.02.2011
 * filename: SpecialItem.java
 * project: KuBaTsch
 */
package at.kubatsch.model;

import java.awt.geom.Point2D.Float;

/**
 * @author Manuel Tscholl (mts3970)
 *
 */
public class SpecialItem implements ICollidable
{

    /**
     * 
     */
    private static final long serialVersionUID = 84010523470059271L;

    /**
     * @see at.kubatsch.model.ICollidable#getCollisionMap()
     */
    @Override
    public Float[] getCollisionMap()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see at.kubatsch.model.ICollidable#getPosition()
     */
    @Override
    public Float getPosition()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see at.kubatsch.model.ICollidable#collidesWith(at.kubatsch.model.ICollidable)
     */
    @Override
    public boolean collidesWith(ICollidable other)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see at.kubatsch.model.ICollidable#getMinPoint()
     */
    @Override
    public Float getMinPoint()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see at.kubatsch.model.ICollidable#getMaxPoint()
     */
    @Override
    public Float getMaxPoint()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see at.kubatsch.model.ICollidable#getCollisionRules()
     */
    @Override
    public Iterable<ICollisionRule> getCollisionRules()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see at.kubatsch.model.ICollidable#addCollisionRule(at.kubatsch.model.ICollisionRule)
     */
    @Override
    public void addCollisionRule(ICollisionRule rule)
    {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see at.kubatsch.model.ICollidable#removeCollisionRule(at.kubatsch.model.ICollisionRule)
     */
    @Override
    public void removeCollisionRule(ICollisionRule rule)
    {
        // TODO Auto-generated method stub
        
    }

}
