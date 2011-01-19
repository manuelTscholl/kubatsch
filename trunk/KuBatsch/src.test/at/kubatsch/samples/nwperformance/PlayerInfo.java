/**
 * This file is part of KuBatsch.
 *   created on: 03.01.2011
 *   filename: PlayerInfo.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.nwperformance;

import java.io.Serializable;

/**
 * @author Daniel Kuschny (dku2375)
 *
 */
public class PlayerInfo implements Serializable
{
    private static final long serialVersionUID = -5714638685830527912L;
    
    private String _name;
    private double _position;
    private int _health;
    
    /**
     * Gets the health.
     * @return the health
     */
    public int getHealth()
    {
        return _health;
    }
    /**
     * Sets the health.
     * @param health the health to set
     */
    public void setHealth(int health)
    {
        _health = health;
    }
    /**
     * Gets the name.
     * @return the name
     */
    public String getName()
    {
        return _name;
    }
    /**
     * Sets the name.
     * @param name the name to set
     */
    public void setName(String name)
    {
        _name = name;
    }
    /**
     * Gets the position.
     * @return the position
     */
    public double getPosition()
    {
        return _position;
    }
    /**
     * Sets the position.
     * @param position the position to set
     */
    public void setPosition(double position)
    {
        _position = position;
    }
    
    
    /**
     * Initializes a new instance of the {@link PlayerInfo} class.
     */
    public PlayerInfo()
    {
        _name = "";
        _position = 0.5;
        _health = 200;
    }
    
    
}
