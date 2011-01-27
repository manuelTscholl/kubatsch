/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: CollidableBase.java
 *   project: KuBatsch
 */
package at.kubatsch.model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import at.kubatsch.util.CollisionCalculator;

/**
 * This is a base implementation of a {@link ICollidable} which 
 * allows an easy creation of new {@link ICollidable} implementations.
 * It automatically calculates the bounding box around the collision region provided by
 * {@link CollidableBase#updateCollisionMap(java.awt.geom.Point2D.Float[], float, float)}
 * @author Daniel Kuschny (dku2375)
 * 
 */
public abstract class CollidableBase implements ICollidable 
{
    private Point2D.Float _minPoint;
    private Point2D.Float _maxPoint;

    private Point2D.Float[]                _collisionMap;
    
    private List<ICollisionRule> _rules;
    
    /**
     * Initializes a new instance of the {@link CollidableBase} class.
     */
    public CollidableBase()
    {
        _rules = new ArrayList<ICollisionRule>();
    }
    
    /**
     * @see at.kubatsch.model.ICollidable#getCollisionMap()
     */
    @Override
    public Point2D.Float[] getCollisionMap()
    {
        return _collisionMap;
    }
    
    /**
     * @see at.kubatsch.model.ICollidable#getPosition()
     */
    @Override
    public Point2D.Float getPosition()
    {
        return new Point2D.Float();
    }

    /**
     * @see at.kubatsch.model.ICollidable#collidesWith(at.kubatsch.model.ICollidable)
     */
    @Override
    public boolean collidesWith(ICollidable other)
    {
        return CollisionCalculator.isColliding(this, other);
    }

    /**
     * @see at.kubatsch.model.ICollidable#getMinPoint()
     */
    @Override
    public Point2D.Float getMinPoint()
    {
        return _minPoint;
    }

    /**
     * @see at.kubatsch.model.ICollidable#getMaxPoint()
     */
    @Override
    public Point2D.Float getMaxPoint()
    {
        return _maxPoint;
    }
    
    /**
     * Updates the collisionmap used by this instance and calculates the 
     * new bounding box. 
     * @param collisionMap the points, describing the polygon of the collision map.
     * @param xSize the x scale which will be used to map the collisionmap 
     * @param ySize the y scale which will be used to map the collisionmap 
     */
    protected void updateCollisionMap(Point2D.Float[] collisionMap, float xSize, float ySize)
    {
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;
        
        _collisionMap = new Point2D.Float[collisionMap.length];
        for (int i = 0; i < collisionMap.length; i++)
        {
            Point2D.Float mapPoint = collisionMap[i];
            _collisionMap[i] = new Point2D.Float(mapPoint.x * xSize, mapPoint.y * ySize);
            
            minX = Math.min(_collisionMap[i].x, minX);
            minY = Math.min(_collisionMap[i].y, minY);
            maxX = Math.max(_collisionMap[i].x, maxX);
            maxY = Math.max(_collisionMap[i].y, maxY);
        }
        
        _minPoint = new Point2D.Float(minX, minY);
        _maxPoint = new Point2D.Float(maxX, maxY);
    }
    
    /**
     * @see at.kubatsch.model.ICollidable#getCollisionRules()
     */
    public List<ICollisionRule> getCollisionRules()
    {
        return _rules;
    }
    
    /**
     * @see at.kubatsch.model.ICollidable#addCollisionRule(at.kubatsch.model.ICollisionRule)
     */
    @Override
    public void addCollisionRule(ICollisionRule rule)
    {
        _rules.add(rule);
    }
    
    /**
     * @see at.kubatsch.model.ICollidable#removeCollisionRule(at.kubatsch.model.ICollisionRule)
     */
    @Override
    public void removeCollisionRule(ICollisionRule rule)
    {
        _rules.remove(rule);
    }
}
