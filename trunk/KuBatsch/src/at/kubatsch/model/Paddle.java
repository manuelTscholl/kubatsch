/**
 * This file is part of KuBatsch.
 *   created on: 26.01.2011
 *   filename: Paddle.java
 *   project: KuBatsch
 */
package at.kubatsch.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import at.kubatsch.model.CollidableBase;
import at.kubatsch.model.Color;
import at.kubatsch.model.IDrawable;
import at.kubatsch.model.PlayerPosition;
import at.kubatsch.uicontrols.KuBatschTheme;
import at.kubatsch.uicontrols.PaddlePainter;
import at.kubatsch.util.Event;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;
import at.kubatsch.util.KuBaTschUtils;

/**
 * This class represents a paddle which is used for the gameplay. 
 * A player can controll a paddle to reflect the ball. 
 * @author Martin Balter 
 */
public class Paddle extends CollidableBase implements IDrawable
{
    /**
     * 
     */
    private static final long serialVersionUID = 918808265954156583L;
    /**
     * This size will size the paddlewidth to 168 pixel on a 800x800 surface
     */
    private static final float           DEFAULT_PADDLE_WIDTH               = KuBatschTheme.PADDLE_SIZE.width
                                                                                    / (float) KuBatschTheme.MAIN_SIZE;
    /**
     * This size will size the paddlewidth to 31 pixel on a 800x800 surface
     */
    private static final float           DEFAULT_PADDLE_HEIGHT              = KuBatschTheme.PADDLE_SIZE.height
                                                                                    / (float) KuBatschTheme.MAIN_SIZE;

    private static final Point2D.Float[] PADDLE_COLLISION_REGION_HORIZONTAL = {
            new Point2D.Float(0.03f, 0.5f), // left
            new Point2D.Float(0.048f, 0.323f), // top-left rounding
            new Point2D.Float(0.072f, 0.162f), // top-left
            new Point2D.Float(0.923f, 0.162f), // top-right
            new Point2D.Float(0.953f, 0.323f), // top-right rounding
            new Point2D.Float(0.971f, 0.5f), // right
            new Point2D.Float(0.953f, 0.662f), // right-bottom rounding
            new Point2D.Float(0.923f, 0.807f), // right-bottom
            new Point2D.Float(0.072f, 0.807f), // left-bottom
            new Point2D.Float(0.048f, 0.662f)}; // left-bottom rounding                                        // left-bottom
                                                                                                                       // rounding

    private static final Point2D.Float[] PADDLE_COLLISION_REGION_VERTICAL   = {
            new Point2D.Float(0.5f, 0.03f), // Top
            new Point2D.Float(0.678f, 0.048f), // top-right rounding
            new Point2D.Float(0.839f, 0.072f), // top-right
            new Point2D.Float(0.839f, 0.923f), // bottom-right
            new Point2D.Float(0.678f, 0.953f), // bottom-right rounding
            new Point2D.Float(0.5f, 0.971f), // bottom
            new Point2D.Float(0.338f, 0.953f), // bottom-left rounding
            new Point2D.Float(0.194f, 0.923f), // bottom-left
            new Point2D.Float(0.194f, 0.072f), // top-left
            new Point2D.Float(0.338f, 0.048f) // top-left rounding
        };                                       
                                                                                                                       // rounding

    private float                        _x                                 = 0.5f;
    private PlayerPosition               _paddlePosition;

    /**
     * Initializes a new instance of the {@link Paddle} class.
     */
    public Paddle()
    {
        setPaddlePosition(PlayerPosition.SOUTH);
    }

    /**
     * Gets the paddlePosition.
     * @return the paddlePosition
     */
    public PlayerPosition getPaddlePosition()
    {
        return _paddlePosition;
    }

    public boolean isHorizontal()
    {
        return _paddlePosition == PlayerPosition.NORTH || _paddlePosition == PlayerPosition.SOUTH;
    }
    
    /**
     * Sets the paddlePosition.
     * @param paddlePosition the paddlePosition to set
     */
    public void setPaddlePosition(PlayerPosition paddlePosition)
    {
        _paddlePosition = paddlePosition;
        updateCollisionMap();
    }

    private void updateCollisionMap()
    {
        Point2D.Float[] map;
        float xSize;
        float ySize;
        if (_paddlePosition == PlayerPosition.EAST
                || _paddlePosition == PlayerPosition.WEST)
        {
            map = PADDLE_COLLISION_REGION_VERTICAL;
            ySize = DEFAULT_PADDLE_WIDTH;
            xSize = DEFAULT_PADDLE_HEIGHT;
        }
        else
        {
            map = PADDLE_COLLISION_REGION_HORIZONTAL;
            xSize = DEFAULT_PADDLE_WIDTH;
            ySize = DEFAULT_PADDLE_HEIGHT;
        }
        updateCollisionMap(map, xSize, ySize);
    }

    /**
     * Gets the x.
     * @return the x
     */
    public float getAbsolutePosition()
    {
        return _x;
    }

    /**
     * @see at.kubatsch.model.CollidableBase#getPosition()
     */
    @Override
    public Point2D.Float getPosition()
    {
        float x = 0;
        float y = 0;
        
        switch (_paddlePosition)
        {
            case NORTH:
                x = 1 - _x - DEFAULT_PADDLE_WIDTH;
                y = -getMinPoint().y;
                break;
            case SOUTH:
                x = _x;
                y = 1 - getMaxPoint().y;
                break;

            case WEST:
                x = - getMinPoint().x;
                y = _x;
                break;
            case EAST:
                x = 1 - getMaxPoint().x;
                y =1 - _x - DEFAULT_PADDLE_WIDTH;
                break;
        }

        return new Point2D.Float(x, y);
    }
    
    public Point2D.Float getPaintPosition()
    {
        float x = 0;
        float y = 0;
        
        switch (_paddlePosition)
        {
            case NORTH:
            case SOUTH:
                x = _x;
                y = 1 - getMaxPoint().y;
                break;

            case WEST:
            case EAST:
                x = _x;
                y = 1 - getMaxPoint().x;
                break;
        }

        return new Point2D.Float(x, y);
    }

    /**
     * Updates the paddle position to the correct position.
     * @param x the x position as value from 0-1
     */
    public void setRelativePosition(float x)
    {
        float minValue = 0;
        float maxValue = 0;
        
        // 1 -> paddleSize
        // x -> ?
        if(isHorizontal())
        {
            float w = getMaxPoint().x - getMinPoint().x;
            minValue = -getMinPoint().x;
            maxValue = 1f - w - getMinPoint().x;
        }
        else
        {
            float w = getMaxPoint().y - getMinPoint().y;
            minValue = -getMinPoint().y;
            maxValue = 1f - w - getMinPoint().y;
            
        }
        
        // minValue -> 0
        // maxValue -> 1
        // x -> ?
        _x = KuBaTschUtils.getValueBetweenRange((maxValue - minValue) * x + minValue, minValue, maxValue);
        _paddleMoved.fireEvent(EventArgs.Empty);
    }

    public void movePaddle(float way)
    {
        setRelativePosition(getAbsolutePosition() + way);
    }

    private Event<EventArgs> _paddleMoved = new Event<EventArgs>(this);

    /**
     * @param handler
     * @see at.kubatsch.util.Event#addHandler(at.kubatsch.util.IEventHandler)
     */
    public void addPaddleMovedListener(IEventHandler<EventArgs> handler)
    {
        _paddleMoved.addHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#removeHandler(at.kubatsch.util.IEventHandler)
     */
    public void removePaddleMovedListener(IEventHandler<EventArgs> handler)
    {
        _paddleMoved.removeHandler(handler);
    }

    /**
     * @see at.kubatsch.model.IDrawable#paint(java.awt.Graphics,
     *      java.awt.Dimension)
     */
    @Override
    public void paint(Graphics g, Dimension realSize)
    {

        Point2D.Float pos = getPaintPosition();
        int x = (int) (realSize.width * pos.x);
        int y = (int) (realSize.height * pos.y);
        
        AffineTransform t = ((Graphics2D) g).getTransform();


        int rotation = 0;

        switch (_paddlePosition)
        {
            case NORTH:
                rotation = 180;
                break;
            case WEST:
                rotation = 90;
                break;
            case EAST:
                rotation = 270;
                break;
        }

        if (rotation > 0)
        {
            ((Graphics2D) g).transform(AffineTransform
                    .getRotateInstance((double) (rotation * Math.PI) / 180, 
                            realSize.width/2, realSize.height/2));
        }

        PaddlePainter.paint(g, new Rectangle(x,y, 
                (int) (realSize.width * DEFAULT_PADDLE_WIDTH),
                (int) (realSize.height * DEFAULT_PADDLE_HEIGHT)), Color.RED,
                0.7f);

        ((Graphics2D) g).setTransform(t);
    }
}
