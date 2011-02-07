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
 */
public class PlayerHitArea extends CollidableBase 
{
    private static final long              serialVersionUID = -3046112050291545127L;

    private static final float             INSET            = 0.0001f;
    private static final Point2D.Float[][] COLLISION_MAPS   = {
            { new Point2D.Float(2 * INSET, 1 - INSET),
            new Point2D.Float(1 - (2 * INSET), 1 - INSET),
            new Point2D.Float(1 - (2 * INSET), 2f),
            new Point2D.Float(2 * INSET, 2f) }, // south
            { new Point2D.Float(2 * INSET, INSET),
            new Point2D.Float(1 - (2 * INSET), INSET),
            new Point2D.Float(1 - (2 * INSET), -1f),
            new Point2D.Float(2 * INSET, -1f), }, // north
            { new Point2D.Float(INSET, INSET),
            new Point2D.Float(INSET, 1 - INSET),
            new Point2D.Float(-1f, 1 - INSET), new Point2D.Float(-1f, INSET), }, // west
            { new Point2D.Float(1 - INSET, INSET), new Point2D.Float(2, INSET),
            new Point2D.Float(2f, 1 - INSET),
            new Point2D.Float(1 - INSET, 1 - INSET) }, // east
                                                            };

    private Player                         _player;

    /**
     * Initializes a new instance of the {@link PlayerHitArea} class.
     */
    public PlayerHitArea(Player player)
    {
        _player = player;
        updateCollisionMap();
    }

    /**
     * Gets the player.
     * @return the player
     */
    public Player getPlayer()
    {
        return _player;
    }


    /**
     * Gets the position of the area.
     * @return the position of the area.
     */
    public PlayerPosition getHitAreaPosition()
    {
        return _player.getPosition();
    }


    /**
     * Updates the collision map according to the current position.
     */
    public void updateCollisionMap()
    {
        int i = 0;
        switch (getHitAreaPosition())
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
