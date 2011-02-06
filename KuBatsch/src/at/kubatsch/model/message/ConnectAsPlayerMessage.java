/**
 * author: Daniel Kuschny (dku2375)
 * created on: 05.02.2011
 * filename: ConnectAsPlayerMessage.java
 * project: KuBatsch
 */
package at.kubatsch.model.message;

/**
 * This message is sent from the client to the server if a client want's
 * to connect as a player.
 * @author Daniel Kuschny (dku2375)
 *
 */
public class ConnectAsPlayerMessage implements INetworkMessage 
{
    private static final long serialVersionUID = 565137445952171714L;

    public static final String MESSAGE_ID = "player-connect";
    
    private String _name;

    /**
     * Gets the name. 
     * @return the name
     */
    public String getName()
    {
        return _name;
    }

    /**
     * Initializes a new instance of the {@link ConnectAsPlayerMessage} class.
     */
    public ConnectAsPlayerMessage()
    {
        this("");
    }
    
    /**
     * Initializes a new instance of the {@link ConnectAsPlayerMessage} class.
     * @param name
     */
    public ConnectAsPlayerMessage(String name)
    {
        _name = name;
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
