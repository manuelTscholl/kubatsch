/**
 * author: Daniel Kuschny (dku2375)
 * created on: 05.02.2011
 * filename: ClientGameController.java
 * project: KuBatsch
 */
package at.kubatsch.client.controller;

import java.util.Date;

import org.apache.log4j.Logger;

import at.kubatsch.model.Color;
import at.kubatsch.model.GameState;
import at.kubatsch.model.Paddle;
import at.kubatsch.model.Player;
import at.kubatsch.model.PlayerPosition;
import at.kubatsch.util.GameControllerBase;

/**
 * 
 * Interpolates the game and sets the player to the correct position.
 * {@link GameControllerBase}
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class ClientGameController extends GameControllerBase
{
    private static Logger LOGGER = Logger.getLogger(ClientGameController.class);
    
    private static ClientGameController _instance;
    private PlayerPosition[] _positionMappings;

    /**
     * Singelton of {@link ClientConfigController}
     * @return
     */
    public synchronized static ClientGameController getInstance()
    {
        if (_instance == null)
            _instance = new ClientGameController();
        return _instance;
    }


    /**
     * Returns the Position of the paddles 
     * @return Returns a position of a {@link Paddle}
     */
    public synchronized PlayerPosition[] getPositionMappings()
    {
        return _positionMappings;
    }

    /**
     * Sets the correct position of the {@link Paddle}s
     * @param positionMappings
     */
    private synchronized void setPositionMappings(
            PlayerPosition[] positionMappings)
    {
        _positionMappings = positionMappings;
    }

    private int _clientUid;

    /**
     * The unic ID of this Client
     * @return
     */
    public int getClientUid()
    {
        return _clientUid;
    }

    /**
     * Setting a new Unic id
     * @param clientUid
     */
    public void setClientUid(int clientUid)
    {
        _clientUid = clientUid;
    }
    
    /**
     * 
     * @return The {@link Player} of this Instance will be returned
     */
    public Player getCurrentPlayer()
    {
        GameState s = getCurrentGameState();
        if(s == null || _clientUid < 0) return null;
        int index = s.getPlayerIndex(_clientUid);
        if(index < 0) return null;
        return s.getPlayer()[index];        
    }

    /**
     * Is used for the correct positioning of the {@link Player} 
     * @see at.kubatsch.util.GameControllerBase#setCurrentGameState(at.kubatsch.model.GameState)
     */
    @Override
    public synchronized void setCurrentGameState(GameState currentGameState)
    {
        super.setCurrentGameState(currentGameState);
        if(currentGameState == null) return;
        // search index of myself.
        LOGGER.info(new Date().toString() + ": new gamestate");
//        System.out.println(new Date().toString() + ": new gamestate");
        int index = currentGameState.getPlayerIndex(_clientUid);

        // server stores 0->south, 1->north, 2->east, 3->west
        // we need to rotate this map to determine the correct colors

        PlayerPosition[] position = new PlayerPosition[currentGameState.getPlayer().length];

        if (index == 0) // south
        { // no rotation
            position[0] = PlayerPosition.SOUTH;
            position[1] = PlayerPosition.NORTH;
            position[2] = PlayerPosition.WEST;
            position[3] = PlayerPosition.EAST;
        }
        else if (index == 1) // north
        {
            // rotate 180°
            position[0] = PlayerPosition.NORTH;
            position[1] = PlayerPosition.SOUTH;
            position[2] = PlayerPosition.EAST;
            position[3] = PlayerPosition.WEST;
        }
        else if (index == 2) // west
        {
            // rotate 270°
            position[0] = PlayerPosition.EAST;
            position[1] = PlayerPosition.WEST;
            position[2] = PlayerPosition.SOUTH;
            position[3] = PlayerPosition.NORTH;
        }
        else
        // east
        {
            // rotate 90°
            position[0] = PlayerPosition.WEST;
            position[1] = PlayerPosition.EAST;
            position[2] = PlayerPosition.NORTH;
            position[3] = PlayerPosition.SOUTH;
        }

        setPositionMappings(position);
    }
    
    /**
     * 
     * Initializes a new instance of the {@link ClientGameController} class.
     */
    private ClientGameController()
    {
        setStateUpdateInterval(1);
    }

    /**
     * @see at.kubatsch.util.GameControllerBase#runGame()
     */
    @Override
    protected void runGame()
    {
        // simply let the controller interpolate the
        // gamestate
    }

    /**
     * 
     * @param clientId
     * @return the {@link Color} of the specified Id
     */
    public Color getColorForPlayer(int clientId)
    {
        // get client index
        int index = getCurrentGameState().getPlayerIndex(clientId);
        return getColorForIndex(index);
    }

    /**
     * @param i
     * @return the {@link Color} for the specified index
     */
    public Color getColorForIndex(int index)
    {
        if(index < 0) return Color.GRAY;
        
        PlayerPosition position = getPositionMappings()[index];
        switch (position)
        {
            case NORTH:
                return ClientConfigController.getInstance()
                        .getConfig().getNorthColor();
            case SOUTH:
                return ClientConfigController.getInstance()
                        .getConfig().getSouthColor();
            case EAST:
                return  ClientConfigController.getInstance()
                        .getConfig().getEastColor();
            case WEST:
                return  ClientConfigController.getInstance()
                        .getConfig().getWestColor();
        }
        return Color.GRAY;
    }

    /**
     * @param clientId The client which the position should be setted
     * @param newPosition the new {@link Paddle} position 
     */
    public void updatePaddlePosition(int clientId, float newPosition)
    {
        if(clientId == _clientUid) return;
        
        int index = getCurrentGameState().getPlayerIndex(clientId);
        if(index >= 0)
        {
            getCurrentGameState().getPlayer()[index].setPaddlePosition(newPosition);
        }
        
    }

}
