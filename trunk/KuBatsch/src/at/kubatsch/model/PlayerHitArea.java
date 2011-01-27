/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: PlayerHitArea.java
 *   project: KuBatsch
 */
package at.kubatsch.model;

import java.awt.geom.Point2D;


/**
 * This area represents the outer borders of the gameboard. 
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class PlayerHitArea extends CollidableBase 
{
    private static final Point2D.Float[][] COLLISION_MAPS = {
            { 
                new Point2D.Float(0.00011f, 0.9999f), new Point2D.Float(0.99989f, 0.9999f),
                new Point2D.Float(0.99989f, 2f),new Point2D.Float(0.00011f, 2f)
            }, // south
            { 
                new Point2D.Float(0.00011f, 0.0001f), new Point2D.Float(0.99989f, 0.0001f),
                new Point2D.Float(0.99989f, -1f), new Point2D.Float(0.00011f, -1f),
            }, // north
            { 
                new Point2D.Float(0.0001f, 0.0001f), new Point2D.Float(0.0001f, 0.9999f),
                new Point2D.Float(-1f, 0.9999f), new Point2D.Float(-1f, 0.0001f),
            }, // west
            {
                new Point2D.Float(0.9999f, 0.0001f), new Point2D.Float(0.9999f, 0.9999f),
                new Point2D.Float(2f, 0.9999f), new Point2D.Float(2f, 0.0001f)
            }, // east
    };

    private PlayerPosition                 _hitAreaPosition;
    
    /**
     * Initializes a new instance of the {@link PlayerHitArea} class.
     */
    public PlayerHitArea()
    {
        this(PlayerPosition.SOUTH);
    }
    
    /**
     * Initializes a new instance of the {@link PlayerHitArea} class.
     * @param position the location of the area. 
     */
    public PlayerHitArea(PlayerPosition position)
    {
        setHitAreaPosition(position);
    }
    
    /**
     * Gets the position of the area.
     * @return the position of the area.
     */
    public PlayerPosition getHitAreaPosition()
    {
        return _hitAreaPosition;
    }

    /**
     * Sets the position of the area.
     * @param position the position of the area.
     */
    public void setHitAreaPosition(PlayerPosition position)
    {
        _hitAreaPosition = position;
        updateCollisionMap();
    }

    /**
     * Updates the collision map according to the current position. 
     */
    private void updateCollisionMap()
    {
        int i = 0;
        switch (_hitAreaPosition)
        {
            case SOUTH:
                i = 0;
                break;
            case NORTH:
                i = 1;
                break;
            case WEST:
                i = 2;
                break;
            case EAST:
                i = 3;
                break;
        }
        
        updateCollisionMap(COLLISION_MAPS[i], 1, 1);
    }
}
