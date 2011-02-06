/**
 * author: Daniel Kuschny (dku2375)
 * created on: 05.02.2011
 * filename: SetUniqueIdMessage.java
 * project: KuBatsch
 */
package at.kubatsch.model.message;


/**
 * This message is sent from the server to the client to 
 * notify which UID the client has. 
 * @author Daniel Kuschny (dku2375)
 *
 */
public class SetUniqueIdMessage implements INetworkMessage
{  
    private static final long serialVersionUID = 472039824079658880L;

    public static final String MESSAGE_ID = "set-uid";
    
    private int _uniqueId;
    
    /**
     * Gets the uniqueId.
     * @return the uniqueId
     */
    public int getUniqueId()
    {
        return _uniqueId;
    }

    /**
     * Initializes a new instance of the {@link SetUniqueIdMessage} class.
     * @param uniqueId
     */
    public SetUniqueIdMessage(int uniqueId)
    {
        _uniqueId = uniqueId;
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
