/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: Ball.java
 *   project: KuBatsch
 */
package at.kubatsch.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.util.Random;

import at.kubatsch.uicontrols.KuBatschTheme;

/**
 * This class is the representation of a game ball which can have a position and
 * a velocity. Additionally it stores the information how often the ball was hit
 * and which color it currently has.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class Ball extends CollidableBase implements IDrawable, IUpdatable
{
    /**
     * 
     */
    private static final long            serialVersionUID      = 3283765169642426276L;

    /**
     * This size will size the ball to 24 pixel on a 800x800 surface
     */
    private static final float           DEFAULT_BALL_SIZE     = KuBatschTheme.BALL_SIZE
                                                                       / (float) KuBatschTheme.MAIN_SIZE;

    /**
     * This collision region for this ball. 
     */
    private static final Point2D.Float[] BALL_COLLISION_REGION = {
            new Point2D.Float(0.5f, 0.038f), // top
            new Point2D.Float(0.752f, 0.141f), // top-right
            new Point2D.Float(0.854f, 0.389f), // right
            new Point2D.Float(0.752f, 0.639f), // right-bottom
            new Point2D.Float(0.5f, 0.741f), // bottom
            new Point2D.Float(0.25f, 0.639f), // bottom-left
            new Point2D.Float(0.149f, 0.389f), // left
            new Point2D.Float(0.25f, 0.141f), // topleft
                                                               };

    private Point2D.Float                _position;
    private Point2D.Float                _velocity;
    private float                        _size;
    private int                          _hitCount;
    private at.kubatsch.model.Color      _color;

    /**
     * Initializes a new instance of the {@link Ball} class.
     */
    public Ball()
    {
        this(DEFAULT_BALL_SIZE);
    }

    /**
     * Initializes a new instance of the {@link Ball} class.
     * @param size the size of the ball.
     */
    public Ball(float size)
    {
        this(size, at.kubatsch.model.Color.GRAY);
    }

    /**
     * Initializes a new instance of the {@link Ball} class.
     * @param color the color of the ball
     */
    public Ball(at.kubatsch.model.Color color)
    {
        this(DEFAULT_BALL_SIZE, color);
    }

    /**
     * Initializes a new instance of the {@link Ball} class.
     * @param size the size of the ball.
     * @param color the color of the ball
     */
    public Ball(float size, at.kubatsch.model.Color color)
    {
        _position = new Point2D.Float();
        _velocity = new Point2D.Float();
        _color = color;
        setSize(size);
    }

    /**
     * Gets the color of the ball.
     * @return the color of the ball.
     */
    public at.kubatsch.model.Color getColor()
    {
        return _color;
    }

    /**
     * Sets the color of the ball.
     * @param color the new color.
     */
    public void setColor(at.kubatsch.model.Color color)
    {
        _color = color;
    }

    /**
     * @see at.kubatsch.model.CollidableBase#getPosition()
     */
    @Override
    public Point2D.Float getPosition()
    {
        return _position;
    }

    /**
     * Sets the position of the ball within the gameboard.
     * @param position the position
     */
    public void setPosition(Point2D.Float position)
    {
        _position.setLocation(position);
    }

    /**
     * Sets the position of the ball within the gameboard.
     * @param x the x position
     * @param y the y position
     */
    public void setPosition(float x, float y)
    {
        _position.setLocation(x, y);
    }

    /**
     * Gets the velocity of the ball.
     * @return the velocity
     */
    public Point2D.Float getVelocity()
    {
        return _velocity;
    }

    /**
     * Sets the velocity of the ball.
     * @param velocity the velocity.
     */
    public void setVelocity(Point2D.Float velocity)
    {
        _velocity.setLocation(velocity);
    }

    /**
     * Sets the velocity of the ball.
     * @param xd the x velocity
     * @param yd the y velocity
     */
    public void setVelocity(float xd, float yd)
    {
        _velocity.setLocation(xd, yd);
    }

    /**
     * Gets the size of the ball.
     * @return the size.
     */
    public float getSize()
    {
        return _size;
    }

    /**
     * Sets the size of the ball.
     * @param size the ball.
     */
    public void setSize(float size)
    {
        _size = size;
        updateCollisionMap(BALL_COLLISION_REGION, _size, _size);
    }

    /**
     * Gets the amount of hits the ball had.
     * @return the amount of hits.
     */
    public int getHitCount()
    {
        return _hitCount;
    }

    /**
     * Sets the amount of hits the ball had.
     * @param hitCount the amount of hits.
     */
    public void setHitCount(int hitCount)
    {
        _hitCount = hitCount;
    }

    /**
     * @see at.kubatsch.model.IDrawable#paint(java.awt.Graphics,
     *      java.awt.Dimension)
     */
    @Override
    public void paint(Graphics g, Dimension realSize)
    {
        // calculate real bounds
        int realX = (int) (_position.x * realSize.width);
        int realY = (int) (_position.y * realSize.height);
        int realSizeX = (int) (_size * realSize.width);
        int realSizeY = (int) (_size * realSize.height);

        // Draw image
         Image ballImage = KuBatschTheme.BALLS[_color.getIndex()];
         g.drawImage(ballImage, realX, realY, realX + realSizeX, realY +
         realSizeY,
         0,0, KuBatschTheme.BALL_SIZE, KuBatschTheme.BALL_SIZE, null);
    }

    /**
     * @see at.kubatsch.model.IUpdatable#update()
     */
    @Override
    public void update()
    {
        setPosition(getPosition().x + getVelocity().x, getPosition().y
                + getVelocity().y);
    }

    /**
     * creates a ball with a random color, centered and with a random speed.
     * @return
     */
    public static Ball createRandom()
    {
        Random rnd = new Random();

        at.kubatsch.model.Color[] colors = at.kubatsch.model.Color.values();
        at.kubatsch.model.Color rndColor = colors[rnd.nextInt(colors.length)];

        Ball newBall = new Ball(rndColor);
        newBall.setPosition(0.5f, 0.5f);
        // random direction with random speed
        float speedX = (rnd.nextInt(10) + 5) / 1000.0f;
        if (rnd.nextBoolean())
        {
            speedX = -speedX;
        }

        float speedY = (rnd.nextInt(10) + 5) / 1000.0f;
        if (rnd.nextBoolean())
        {
            speedY = -speedY;
        }

        newBall.setVelocity(speedX, speedY);
        return newBall;
    }
}
