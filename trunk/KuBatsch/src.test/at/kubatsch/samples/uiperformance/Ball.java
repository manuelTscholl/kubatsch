/**
 * This file is part of KuBatsch.
 *   created on: 02.01.2011
 *   filename: Ball.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.uiperformance;

import java.awt.Color;
import java.io.Serializable;

/**
 * @author Daniel Kuschny (dku2375)
 *
 */
public class Ball implements Serializable
{
    private double _size;
    
    private double _posX;
    private double _posY;
    
    private double _speedX;
    private double _speedY;
    
    private Color _color;
    
    /**
     * Initializes a new instance of the {@link Ball} class.
     */
    public Ball()
    {
        this(Color.black);
    }
    
    /**
     * Initializes a new instance of the {@link Ball} class.
     * @param color
     */
    public Ball(Color color)
    {
        _size = 0.025;
        _posX = 0;
        _posY = 0;
        _speedX = 0.001;
        _speedY = 0.001;
        _color = color;
    }
    
    
    
    /**
     * Gets the speedX.
     * @return the speedX
     */
    public double getSpeedX()
    {
        return _speedX;
    }



    /**
     * Sets the speedX.
     * @param speedX the speedX to set
     */
    public void setSpeedX(double speedX)
    {
        _speedX = speedX;
    }



    /**
     * Gets the speedY.
     * @return the speedY
     */
    public double getSpeedY()
    {
        return _speedY;
    }



    /**
     * Sets the speedY.
     * @param speedY the speedY to set
     */
    public void setSpeedY(double speedY)
    {
        _speedY = speedY;
    }



    /**
     * Gets the color.
     * @return the color
     */
    public Color getColor()
    {
        return _color;
    }
    /**
     * Sets the color.
     * @param color the color to set
     */
    public void setColor(Color color)
    {
        _color = color;
    }
    /**
     * Gets the size.
     * @return the size
     */
    public double getSize()
    {
        return _size;
    }
    /**
     * Sets the size.
     * @param size the size to set
     */
    public void setSize(double size)
    {
        _size = size;
    }
    /**
     * Gets the posX.
     * @return the posX
     */
    public double getPosX()
    {
        return _posX;
    }
    /**
     * Sets the posX.
     * @param posX the posX to set
     */
    public void setPosX(double posX)
    {
        _posX = posX;
    }
    /**
     * Gets the posY.
     * @return the posY
     */
    public double getPosY()
    {
        return _posY;
    }
    /**
     * Sets the posY.
     * @param posY the posY to set
     */
    public void setPosY(double posY)
    {
        _posY = posY;
    }



    /**
     */
    public void move()
    {
        _posX += _speedX;
        _posY += _speedY;
    }
    
    
    
}
