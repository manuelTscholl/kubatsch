/**
 * This file is part of KuBatsch.
 * created on: 24.01.2011
 * filename: CollisionCalculator.java
 * project: KuBatsch
 */
package at.kubatsch.util;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import at.kubatsch.model.ICollidable;

/**
 * Calulator to detect Collisions from to different Collidables.
 * @author Daniel Kuschny (dku2375)
 */
public class CollisionCalculator
{
    /**
     * Checks if two Collidables collides
     * @param c1 first Collidable
     * @param c2 secound Collidable
     * @return
     */
    public static boolean isColliding(ICollidable c1, ICollidable c2)
    {
        Rectangle2D.Float c1Bounds = getBounds(c1);
        Rectangle2D.Float c2Bounds = getBounds(c2);

        // check for any intersection of boundary rectangles
        // if no -> no further matching is needed
        if (c1Bounds.getMinX() > c2Bounds.getMaxX()
                || c1Bounds.getMaxX() < c2Bounds.getMinX() ||

                c1Bounds.getMinY() > c2Bounds.getMaxY()
                || c1Bounds.getMaxY() < c2Bounds.getMinY())
        {
            return false;
        }

        
        // compare all edges of first collidable
        for (int first = 0; first < c1.getCollisionMap().length; first++)
        {
            Point2D.Float firstEdgeStart = c1.getCollisionMap()[first];
            Point2D.Float firstEdgeEnd;

            if (first < c1.getCollisionMap().length - 1)
            {
                firstEdgeEnd = c1.getCollisionMap()[first + 1];
            }
            else
            // closing edge
            {
                firstEdgeEnd = c1.getCollisionMap()[0];
            }

            // to all edges of second collidable
            for (int second = 0; second < c2.getCollisionMap().length; second++)
            {
                Point2D.Float secondEdgeStart = c2.getCollisionMap()[second];
                Point2D.Float secondEdgeEnd;

                if (second < c2.getCollisionMap().length - 1)
                {
                    secondEdgeEnd = c1.getCollisionMap()[second + 1];
                }
                else
                // closing edge
                {
                    secondEdgeEnd = c1.getCollisionMap()[0];
                }

                // do edges cross?
                if (doLinesCross(firstEdgeStart, firstEdgeEnd, secondEdgeStart,
                        secondEdgeEnd))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if two lines are Colliding. The line gepends of two Points
     * @param firstEdgeStart first edge for the first line
     * @param firstEdgeEnd secound edge for the first line
     * @param secondEdgeStart first edge for the secound line
     * @param secondEdgeEnd secound edge for the secound line
     * @return if the to line collide
     */
    private static boolean doLinesCross(Point2D.Float firstEdgeStart,
            Point2D.Float firstEdgeEnd, Point2D.Float secondEdgeStart,
            Point2D.Float secondEdgeEnd)
    {
        return Line2D.linesIntersect(firstEdgeStart.getX(),
                firstEdgeStart.getY(), firstEdgeEnd.getX(),
                firstEdgeEnd.getY(), secondEdgeStart.getX(),
                secondEdgeStart.getY(), secondEdgeEnd.getX(),
                secondEdgeEnd.getY());
    }

    /**
     * The bounds of the Collidable. This is the first thing which will be 
     * checked if two collidables collide
     * @param c the collidable which you want the bounds
     * @return bounds of the collidable
     */
    private static Rectangle2D.Float getBounds(ICollidable c)
    {
        float left = c.getPosition().x + c.getMinPoint().x;
        float top = c.getPosition().y + c.getMinPoint().y;
        float right = c.getPosition().x + c.getMaxPoint().x;
        float bottom = c.getPosition().y + c.getMaxPoint().y;
        return new Rectangle2D.Float(left, top, right - left, bottom - top);
    }
}
