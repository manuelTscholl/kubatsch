/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: Ball.java
 *   project: KuBatsch
 */
package at.kubatsch.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Random;

import at.kubatsch.client.controller.ClientConfigController;
import at.kubatsch.client.controller.ClientGameController;
import at.kubatsch.client.controller.ViewController;
import at.kubatsch.client.view.GameView;
import at.kubatsch.model.rules.PaddleReflectRule;
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
    private int                          _lastHitBy;

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
        setSize(size);
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
     * Gets the lastHitBy.
     * @return the lastHitBy
     */
    public int getLastHitBy()
    {
        return _lastHitBy;
    }

    /**
     * Sets the lastHitBy.
     * @param lastHitBy the lastHitBy to set
     */
    public void setLastHitBy(int lastHitBy)
    {
        _lastHitBy = lastHitBy;
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

        AffineTransform t = ((Graphics2D) g).getTransform();

        Color ballColor = Color.GRAY;
        if (_lastHitBy >= 0)
        {
            ballColor = ClientGameController.getInstance().getColorForPlayer(_lastHitBy);
        }
        
        GameView view = ViewController.getInstance().getView(
                GameView.PANEL_ID);
        int clientId = view.getClientId();
        if (clientId > 0)
        {
            int index = ClientGameController.getInstance().getCurrentGameState().getPlayerIndex(clientId);
            int rotation = PlayerPosition.getRotationForPosition(PlayerPosition.getPositionForIndex(index));
            if (rotation > 0)
            {
                float w = getMaxPoint().x - getMinPoint().x;
                float h = getMaxPoint().y - getMinPoint().y;
                
                float centerBallX = _position.x + getMinPoint().x + w/2;
                float centerBallY = _position.y + getMinPoint().y + h/2;
                
                ((Graphics2D) g).transform(AffineTransform.getRotateInstance(
                        (double) rotation * PaddleReflectRule.GRAD_RAD_FACTOR,
                        (centerBallX * realSize.width), (centerBallY * realSize.height)));
            }
        }

        // Draw image
        Image ballImage = KuBatschTheme.BALLS[ballColor.getIndex()];
        g.drawImage(ballImage, realX, realY, realX + realSizeX, realY
                + realSizeY, 0, 0, KuBatschTheme.BALL_SIZE,
                KuBatschTheme.BALL_SIZE, null);
        
        ((Graphics2D) g).setTransform(t);

//        paintHitArea(g, this, realSize);
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

        //
        float speed = 0.003f;
        int angle = rnd.nextInt(360);

        float speedY = (float) Math.sin(angle
                * PaddleReflectRule.GRAD_RAD_FACTOR)
                * speed;
        float speedX = (float) Math.cos(angle
                * PaddleReflectRule.GRAD_RAD_FACTOR)
                * speed;

        newBall.setVelocity(speedX, speedY);
        return newBall;
    }
}
