/**
 * author: Manuel Tscholl(mts3970)
 * created on: 03.02.2011
 * filename: Player.java
 * project: KuBaTsch
 */
package at.kubatsch.model;

import java.io.Serializable;

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
    
    private String _name;
    private String _uid;
    private float _paddlePos;
    private float _health;
    private int _wins;
    private String[] _meta;
    
    private Paddle _paddle;
    private PlayerHitArea _playerHitArea;
    private Wall _wall;
    /**
     * Initializes a new instance of the {@link Player} class.
     * @param name
     * @param uid
     * @param paddlePos
     * @param health
     * @param wins
     * @param meta
     * @param paddle
     * @param playerHitArea
     * @param wall
     */
    public Player(String name, String uid, float paddlePos, float health,
            int wins, String[] meta, Paddle paddle,
            PlayerHitArea playerHitArea, Wall wall)
    {
        super();
        _name = name;
        _uid = uid;
        _paddlePos = paddlePos;
        _health = health;
        _wins = wins;
        _meta = meta;
        _paddle = paddle;
        _playerHitArea = playerHitArea;
        _wall = wall;
    }
    
    public Player(){
        
        
    }

    /**
     * Gets the name.
     * @return the name
     */
    public synchronized String getName()
    {
        return _name;
    }

    /**
     * Sets the name.
     * @param name the name to set
     */
    public synchronized void setName(String name)
    {
        _name = name;
    }

    /**
     * Gets the uid.
     * @return the uid
     */
    public synchronized String getUid()
    {
        return _uid;
    }

    /**
     * Sets the uid.
     * @param uid the uid to set
     */
    public synchronized void setUid(String uid)
    {
        _uid = uid;
    }

    /**
     * Gets the paddlePos.
     * @return the paddlePos
     */
    public synchronized float getPaddlePos()
    {
        return _paddlePos;
    }

    /**
     * Sets the paddlePos.
     * @param paddlePos the paddlePos to set
     */
    public synchronized void setPaddlePos(float paddlePos)
    {
        _paddlePos = paddlePos;
    }

    /**
     * Gets the health.
     * @return the health
     */
    public synchronized float getHealth()
    {
        return _health;
    }

    /**
     * Sets the health.
     * @param health the health to set
     */
    public synchronized void setHealth(float health)
    {
        _health = health;
    }

    /**
     * Gets the wins.
     * @return the wins
     */
    public synchronized int getWins()
    {
        return _wins;
    }

    /**
     * Sets the wins.
     * @param wins the wins to set
     */
    public synchronized void setWins(int wins)
    {
        _wins = wins;
    }

    /**
     * Gets the meta.
     * @return the meta
     */
    public synchronized String[] getMeta()
    {
        return _meta;
    }

    /**
     * Sets the meta.
     * @param meta the meta to set
     */
    public synchronized void setMeta(String[] meta)
    {
        _meta = meta;
    }

    /**
     * Gets the paddle.
     * @return the paddle
     */
    public synchronized Paddle getPaddle()
    {
        return _paddle;
    }

    /**
     * Sets the paddle.
     * @param paddle the paddle to set
     */
    public synchronized void setPaddle(Paddle paddle)
    {
        _paddle = paddle;
    }

    /**
     * Gets the playerHitArea.
     * @return the playerHitArea
     */
    public synchronized PlayerHitArea getPlayerHitArea()
    {
        return _playerHitArea;
    }

    /**
     * Sets the playerHitArea.
     * @param playerHitArea the playerHitArea to set
     */
    public synchronized void setPlayerHitArea(PlayerHitArea playerHitArea)
    {
        _playerHitArea = playerHitArea;
    }

    /**
     * Gets the wall.
     * @return the wall
     */
    public synchronized Wall getWall()
    {
        return _wall;
    }

    /**
     * Sets the wall.
     * @param wall the wall to set
     */
    public synchronized void setWall(Wall wall)
    {
        _wall = wall;
    }
    

}
