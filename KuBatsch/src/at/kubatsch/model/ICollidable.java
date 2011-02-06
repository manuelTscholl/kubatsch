/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: ICollidable.java
 *   project: KuBatsch
 */
package at.kubatsch.model;

import java.awt.geom.Point2D;
import java.io.Serializable;

import at.kubatsch.model.rules.ICollisionRule;

/**
 * This class is a main part of the game logic implementation.
 * {@link ICollidable}s represent objects which can collide with any other
 * {@link ICollidable}. If two {@link ICollidable} collide is determined by the
 * {@link #collidesWith(ICollidable)} method. As soon this occures all
 * {@link ICollisionRule}s provided by {@link ICollidable#getCollisionRules()}
 * will be applied.
 * 
 * A {@link ICollidable} has a position and a collisionmap for detecting a
 * collision. A collision map is a list of points describing a polygon which
 * will be used for collision detection.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public interface ICollidable extends Serializable
{
    public Point2D.Float[] getCollisionMap();

    public Point2D.Float getPosition();

    public boolean collidesWith(ICollidable other);

    public Point2D.Float getMinPoint();

    public Point2D.Float getMaxPoint();

    public Iterable<ICollisionRule> getCollisionRules();

    public void addCollisionRule(ICollisionRule rule);

    public void removeCollisionRule(ICollisionRule rule);
}
