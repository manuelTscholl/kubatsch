/**
 * author: Daniel Kuschny (dku2375)
 * created on: 05.02.2011
 * filename: ServerInfoMessage.java
 * project: KuBatsch
 */
package at.kubatsch.model.message;

/**
 * A message sent from the server to the client if a client requests the server
 * info using an instance of this class containing
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class ServerInfoMessage implements INetworkMessage
{
    private static final long serialVersionUID = -7359592164656464544L;

    public static final String MESSAGE_ID       = "server-info";

    private int                _players;

    /**
     * Gets the players.
     * @return the players
     */
    public int getPlayers()
    {
        return _players;
    }

    /**
     * Initializes a new instance of the {@link ServerInfoMessage} class.
     * @param players
     */
    public ServerInfoMessage()
    {
        this(-1);
    }

    /**
     * Initializes a new instance of the {@link ServerInfoMessage} class.
     * @param players
     */
    public ServerInfoMessage(int players)
    {
        _players = players;
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
