/**
 * author: Daniel Kuschny (dku2375)
 * created on: 05.02.2011
 * filename: ClientGameController.java
 * project: KuBatsch
 */
package at.kubatsch.client.controller;

import java.util.Date;

import at.kubatsch.model.Color;
import at.kubatsch.model.GameState;
import at.kubatsch.model.Player;
import at.kubatsch.model.PlayerPosition;
import at.kubatsch.util.GameControllerBase;

/**
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class ClientGameController extends GameControllerBase
{
    private static ClientGameController _instance;

    public synchronized static ClientGameController getInstance()
    {
        if (_instance == null)
            _instance = new ClientGameController();
        return _instance;
    }

    private PlayerPosition[] _positionMappings;

    public synchronized PlayerPosition[] getPositionMappings()
    {
        return _positionMappings;
    }

    private synchronized void setPositionMappings(
            PlayerPosition[] positionMappings)
    {
        _positionMappings = positionMappings;
    }

    private int _clientUid;

    public int getClientUid()
    {
        return _clientUid;
    }

    public void setClientUid(int clientUid)
    {
        _clientUid = clientUid;
    }
    
    public Player getCurrentPlayer()
    {
        GameState s = getCurrentGameState();
        if(s == null || _clientUid < 0) return null;
        int index = s.getPlayerIndex(_clientUid);
        if(index < 0) return null;
        return s.getPlayer()[index];        
    }

    /**
     * @see at.kubatsch.util.GameControllerBase#setCurrentGameState(at.kubatsch.model.GameState)
     */
    @Override
    public synchronized void setCurrentGameState(GameState currentGameState)
    {
        super.setCurrentGameState(currentGameState);
        if(currentGameState == null) return;
        // search index of myself.
        System.out.println(new Date().toString() + ": new gamestate");
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
     * @param clientId
     * @return
     */
    public Color getColorForPlayer(int clientId)
    {
        // get client index
        int index = getCurrentGameState().getPlayerIndex(clientId);
        return getColorForIndex(index);
    }

    /**
     * @param i
     * @return
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
     * @param clientId
     * @param newPosition
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
