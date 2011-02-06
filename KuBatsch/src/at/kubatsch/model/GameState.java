/**
 * author: Manuel Tscholl(mts3970)
 * created on: 03.02.2011
 * filename: GameState.java
 * project: KuBaTsch
 */
package at.kubatsch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Manuel Tscholl (mts3970)
 * 
 */
public class GameState implements Serializable
{
    private static final long serialVersionUID = 4261251295591534186L;
  
    private List<Ball>        _balls;
    private List<SpecialItem> _specialItems;
    private Player[]          _player;
    private StatusLabel       _statusLbl;

    /**
     * Initializes a new instance of the {@link GameState} class.
     */
    public GameState()
    {
        _balls = Collections.synchronizedList(new ArrayList<Ball>());
        _specialItems = Collections
                .synchronizedList(new ArrayList<SpecialItem>());
        _player = new Player[4];
        
        for (int i = 0; i < _player.length; i++)
        {
            _player[i] = new Player(PlayerPosition.getPositionForIndex(i));
        }
        
        _statusLbl = new StatusLabel();
    }

    /**
     * Gets the statusLbl.
     * @return the statusLbl
     */
    public StatusLabel getStatusLbl()
    {
        return _statusLbl;
    }

    /**
     * Gets the balls.
     * @return the balls
     */
    public List<Ball> getBalls()
    {
        return _balls;
    }

    /**
     * Sets the balls.
     * @param balls the balls to set
     */
    public void setBalls(List<Ball> balls)
    {
        _balls = balls;
    }

    /**
     * Gets the specialItems.
     * @return the specialItems
     */
    public List<SpecialItem> getSpecialItems()
    {
        return _specialItems;
    }

    /**
     * Sets the specialItems.
     * @param specialItems the specialItems to set
     */
    public void setSpecialItems(List<SpecialItem> specialItems)
    {
        _specialItems = specialItems;
    }

    /**
     * Gets the player.
     * @return the player
     */
    public Player[] getPlayer()
    {
        return _player;
    }

    /**
     * Sets the player.
     * @param player the player to set
     */
    public void setPlayer(Player[] player)
    {
        _player = player;
    }

    public List<ICollidable> getAllCollidables()
    {
        // TODO: Try some caching of this data
        ArrayList<ICollidable> collidables = new ArrayList<ICollidable>();
        collidables.addAll(getBalls());
        collidables.addAll(getSpecialItems());
        for (Player player : getPlayer())
        {
            collidables.add(player.getPaddle());
            collidables.add(player.getWall());
            collidables.add(player.getPlayerHitArea());
        }
        collidables.add(_statusLbl);
        return collidables;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return String
                .format("GameState [_balls=%s, _specialItems=%s, _player=%s, _statusLbl=%s]",
                        _balls, _specialItems, Arrays.toString(_player),
                        _statusLbl);
    }

    /**
     * @return
     */
    public int getPlayerCount()
    {
        int count = 0;
        for (Player player : _player)
        {
            if (player.getUid() >= 0)
                count++;
        }
        return count;
    }

    /**
     * @param clientUid
     * @return
     */
    public int getPlayerIndex(int clientUid)
    {
        for (int i = 0; i < getPlayer().length; i++)
        {
            if (getPlayer()[i].getUid() == clientUid)
            {
                return i;
            }
        }
        return -1;
    }

}
