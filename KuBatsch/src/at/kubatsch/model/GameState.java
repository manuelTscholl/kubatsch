/**
 * author: Manuel Tscholl(mts3970)
 * created on: 03.02.2011
 * filename: GameState.java
 * project: KuBaTsch
 */
package at.kubatsch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Manuel Tscholl (mts3970)
 * 
 */
public class GameState implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 7550857821770022559L;

    private List<Ball>        _balls;
    private List<SpecialItem> _specialItems;
    private Player[]          _player;

    /**
     * Initializes a new instance of the {@link GameState} class.
     * @param balls
     * @param specialItems
     * @param player
     */
    public GameState(List<Ball> balls, List<SpecialItem> specialItems,
            Player[] player)
    {
        super();

        _balls = balls;
        _specialItems = specialItems;
        _player = player;
    }

    /**
     * Initializes a new instance of the {@link GameState} class.
     */
    public GameState()
    {
        super();

        _balls = new ArrayList<Ball>();
        _specialItems = new ArrayList<SpecialItem>();
        _player = new Player[4];
    }

    /**
     * Gets the balls.
     * @return the balls
     */
    public synchronized List<Ball> getBalls()
    {
        return _balls;
    }

    /**
     * Sets the balls.
     * @param balls the balls to set
     */
    public synchronized void setBalls(List<Ball> balls)
    {
        _balls = balls;
    }

    /**
     * Gets the specialItems.
     * @return the specialItems
     */
    public synchronized List<SpecialItem> getSpecialItems()
    {
        return _specialItems;
    }

    /**
     * Sets the specialItems.
     * @param specialItems the specialItems to set
     */
    public synchronized void setSpecialItems(List<SpecialItem> specialItems)
    {
        _specialItems = specialItems;
    }

    /**
     * Gets the player.
     * @return the player
     */
    public synchronized Player[] getPlayer()
    {
        return _player;
    }

    /**
     * Sets the player.
     * @param player the player to set
     */
    public synchronized void setPlayer(Player[] player)
    {
        _player = player;
    }

    public List<ICollidable> getAllCollidables()
    {
        ArrayList<ICollidable> collidables = new ArrayList<ICollidable>();
        collidables.addAll(getBalls());
        collidables.addAll(getSpecialItems());
        for (Player player : getPlayer())
        {
            if (player != null)
            {
                collidables.add(player.getPaddle());
                collidables.add(player.getWall());
                collidables.add(player.getPlayerHitArea());
            }
        }
        return collidables;
    }

}
