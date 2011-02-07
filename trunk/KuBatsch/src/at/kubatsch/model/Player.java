/**
 * author: Manuel Tscholl(mts3970)
 * created on: 03.02.2011
 * filename: Player.java
 * project: KuBaTsch
 */
package at.kubatsch.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import at.kubatsch.uicontrols.HealthBarPainter;

/**
 * @author Manuel Tscholl (mts3970)
 * 
 */
public class Player implements Serializable
{ 

    /**
     * 
     */
    private static final long serialVersionUID = -7405085160073165238L;

    private String            _name;
    private int               _uid;                                    // uid
                                                                        // -1
                                                                        // means
                                                                        // there
                                                                        // is no
                                                                        // player
                                                                        // on
                                                                        // this
                                                                        // position
    private int               _wins;

    private PlayerPosition    _position;
    private Paddle            _paddle;
    private float             _health;
    private float             _paddlePosition;
    private PlayerHitArea     _playerHitArea;
    private Wall              _wall;

    private Set<String>       _meta;

    /**
     * Initializes a new instance of the {@link Player} class.
     */
    public Player(PlayerPosition position)
    {
        reset(position);
    }
    
    public boolean isAlive()
    {
        return _health > 0 && getUid() >= 0;
    }

    /**
     * Gets the health.
     * @return the health
     */
    public float getHealth()
    {
        return _health;
    }

    /**
     * Sets the health.
     * @param health the health to set
     */
    public void setHealth(float health)
    {
        _health = health;
    }

    /**
     * Gets the meta.
     * @return the meta
     */
    public Set<String> getMeta()
    {
        return _meta;
    }

    /**
     * Gets the paddlePosition.
     * @return the paddlePosition
     */
    public float getPaddlePosition()
    {
        return _paddlePosition;
    }

    /**
     * Sets the paddlePosition.
     * @param paddlePosition the paddlePosition to set
     */
    public void setPaddlePosition(float paddlePosition)
    {
        _paddlePosition = paddlePosition;
        _paddle.setRelativePosition(_paddlePosition);
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
     * Gets the uid.
     * @return the uid
     */
    public int getUid()
    {
        return _uid;
    }

    /**
     * Sets the uid.
     * @param uid the uid to set
     */
    public void setUid(int uid)
    {
        _uid = uid;
    }

    /**
     * Gets the wins.
     * @return the wins
     */
    public int getWins()
    {
        return _wins;
    }

    /**
     * Sets the wins.
     * @param wins the wins to set
     */
    public void setWins(int wins)
    {
        _wins = wins;
    }

    /**
     * Gets the position.
     * @return the position
     */
    public PlayerPosition getPosition()
    {
        return _position;
    }

    /**
     * Sets the position.
     * @param position the position to set
     */
    public void setPosition(PlayerPosition position)
    {
        _position = position;
        _paddle.updateCollisionMap();
        _playerHitArea.updateCollisionMap();
        _wall.updateCollisionMap();
    }
    

    public boolean isHorizontal()
    {
        return _position == PlayerPosition.NORTH
                || _position == PlayerPosition.SOUTH;
    }


    /**
     * Gets the paddle.
     * @return the paddle
     */
    public Paddle getPaddle()
    {
        return _paddle;
    }

    /**
     * Gets the playerHitArea.
     * @return the playerHitArea
     */
    public PlayerHitArea getPlayerHitArea()
    {
        return _playerHitArea;
    }

    /**
     * Gets the wall.
     * @return the wall
     */
    public Wall getWall()
    {
        return _wall;
    }

    /**
     * 
     */
    public void reset(PlayerPosition position)
    {
        _name = "---";
        _uid = -1;
        _position = position;
        _paddle = new Paddle(this);
        _health = 1;
        _playerHitArea = new PlayerHitArea(this);
        _meta = new HashSet<String>();
        //_paddlePosition = 0.5f;
        _wall = new Wall(this);
    }
}
