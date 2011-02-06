/**
 * author: Daniel Kuschny (dku2375)
 * created on: 05.02.2011
 * filename: UpdateGameStateMessage.java
 * project: KuBatsch
 */
package at.kubatsch.model.message;

import at.kubatsch.model.GameState;

/**
 * @author Daniel Kuschny (dku2375)
 *
 */
public class UpdateGameStateMessage implements INetworkMessage
{
    private static final long serialVersionUID = 5851784482786718087L;

    public static final String MESSAGE_ID = "update-game";
    
    private GameState _gameState;
    
    /**
     * Gets the gameState.
     * @return the gameState
     */
    public GameState getGameState()
    {
        return _gameState;
    }

    /**
     * Initializes a new instance of the {@link UpdateGameStateMessage} class.
     * @param gameState
     */
    public UpdateGameStateMessage(GameState gameState)
    {
        _gameState = gameState;
    }



    /**
     * @see at.kubatsch.model.message.INetworkMessage#getMessageId()
     */
    @Override
    public String getMessageId()
    {
        return MESSAGE_ID;
    }
}
