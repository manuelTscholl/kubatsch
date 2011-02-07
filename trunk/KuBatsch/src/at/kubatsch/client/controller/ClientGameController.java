/**
 * author: Daniel Kuschny (dku2375)
 * created on: 05.02.2011
 * filename: ClientGameController.java
 * project: KuBatsch
 */
package at.kubatsch.client.controller;

import at.kubatsch.model.Color;
import at.kubatsch.model.GameState;
import at.kubatsch.model.PlayerPosition;
import at.kubatsch.util.GameControllerBase;

/**
 * The {@link ClientGameController} saves all the mappings. This are
 * relevant data for the client where his position is. He also interpolates
 * the all the posions of the objects. he also handles all the relevant 
 * color information
 * <br>
 * South index = 0<br>
 * North index = 1<br>
 * West index = 2<br>
 * East index = 3<br>
 * 
 * @author Daniel Kuschny (dku2375)
 */
public class ClientGameController extends GameControllerBase
{
    private static ClientGameController _instance;
    private PlayerPosition[]            _positionMappings;
    private int                         _clientUid;

    /**
     * returns the intstance of the {@link ClientGameController}
     * @return the instance of the {@link ClientGameController} 
     */
    public static ClientGameController getInstance()
    {
        if (_instance == null)
            _instance = new ClientGameController();
        return _instance;
    }

    /**
     * gets the Position information where the client is. The Client is at the 
     * South position. All the other players are at the right position
     * @return Position Map of the Client
     */
    public synchronized PlayerPosition[] getPositionMappings()
    {
        return _positionMappings;
    }

    /**
     * Sets the Position where the Client is. The Client is at the south position.
     * All the other players must be at the right position in the map
     * @param positionMappings position mappings for the client
     */
    private synchronized void setPositionMappings(PlayerPosition[] positionMappings)
    {
        _positionMappings = positionMappings;
    }

    /**
     * The {@link ClientGameController#_clientUid} is used to identify the Client on
     * the server. this is also used to identify the right Client in the view. Every 
     * client has an unique ID
     * @return
     */
    public int getClientUid()
    {
        return _clientUid;
    }

    /**
     * Sets the clientId. It has to be unique. It is used to identify the client 
     * and his packeges which he sends to the server. 
     * @param clientUid unique ID for the client
     */
    public void setClientUid(int clientUid)
    {
        _clientUid = clientUid;
    }

    /**
     * @see at.kubatsch.util.GameControllerBase#setCurrentGameState(at.kubatsch.model.GameState)
     */
    @Override
    public synchronized void setCurrentGameState(GameState currentGameState)
    {
        super.setCurrentGameState(currentGameState);
        if (currentGameState == null)
            return;
        // search index of myself.
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
     * Initializes a new instance of the  {@link ClientGameController} class.
     */
    private ClientGameController()
    {
        //
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
     * Get the Color form the ClientId
     * @param clientId ClientId which you want the color
     * @return the color of the client
     */
    public Color getColorForPlayer(int clientId)
    {
        // get client index
        int index = getCurrentGameState().getPlayerIndex(clientId);
        return getColorForIndex(index);
    }

    /**
     * Get the {@link Color} from a index. The index is the PlayerPosition
     * @param i index number of the PlayerPosition which you want the {@link Color}
     * @return the {@link Color}
     */
    public Color getColorForIndex(int index)
    {
        if (index < 0)
            return Color.GRAY;

        PlayerPosition position = getPositionMappings()[index];
        switch (position)
        {
            case NORTH:
                return ClientConfigController.getInstance().getConfig().getNorthColor();
            case SOUTH:
                return ClientConfigController.getInstance().getConfig().getSouthColor();
            case EAST:
                return ClientConfigController.getInstance().getConfig().getEastColor();
            case WEST:
                return ClientConfigController.getInstance().getConfig().getWestColor();
        }
        return Color.GRAY;
    }
}
