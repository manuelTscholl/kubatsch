/**
 * author: Manuel Tscholl(mts3970)
 * created on: 03.02.2011
 * filename: Wall.java
 * project: KuBaTsch
 */
package at.kubatsch.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import at.kubatsch.uicontrols.HealthBarPainter;
import at.kubatsch.uicontrols.KuBatschTheme;

/**
 * @author Manuel Tscholl (mts3970)
 *
 */
public class Wall extends  CollidableBase implements IDrawable
{
    private static final long serialVersionUID = 827491940525047597L;

    private static final float WALL_HEIGHT = 15f / KuBatschTheme.MAIN_SIZE;

    private Player _player;
    
    /**
     * Initializes a new instance of the {@link Wall} class.
     * @param player
     */
    public Wall(Player player)
    {
        _player = player;
        updateCollisionMap();
    }
    
    /**
     * Get the WallPositino
     * @return wall postion
     */
    public PlayerPosition getWallPosition()
    {
        return _player.getPosition();
    }

    /**
     * Update the Collision Map
     */
    public void updateCollisionMap()
    {
//        int i = 0;
//        switch (getWallPosition())
//        {
//            case SOUTH:
//                i = 0;
//                break;
//            case NORTH:
//                i = 1;
//                break;
//            case WEST:
//                i = 2;
//                break;
//            case EAST:
//                i = 3;
//                break;
//        }

        // TODO: Collision with wall
        updateCollisionMap(new Point2D.Float[0], 1, 1);
    }
    
    /**
     * The Painting position of the wall
     * @return postion which you should paint the port
     */
    public Point2D.Float getPaintPosition()
    {
        float x = 0;
        float y = 0;

        switch (getWallPosition())
        {
            case NORTH:
            case SOUTH:
                x = 0;
                y = 1 - WALL_HEIGHT;
                break;
            case EAST:
            case WEST:
                x = 0;
                y = 1 - WALL_HEIGHT;
                break;
        }

        return new Point2D.Float(x, y);
    }


    /**
     * @see at.kubatsch.model.IDrawable#paint(java.awt.Graphics, java.awt.Dimension)
     */
    @Override
    public void paint(Graphics g, Dimension realSize)
    {
        Point2D.Float pos = getPaintPosition();
        
        int w = 0;
        int h = 0;
        if(_player.isHorizontal())
        {
            w = realSize.width;
            h = (int)(WALL_HEIGHT * realSize.height);
        }
        else
        {
            h = (int)(WALL_HEIGHT * realSize.height);
            w = realSize.height;
        }
        
        Rectangle bounds = new Rectangle((int)(pos.x * realSize.width),(int)(pos.y * realSize.height), 
                            w,h);

        HealthBarPainter.paint(g, bounds, at.kubatsch.model.Color.GRAY, 1f);
    }

}