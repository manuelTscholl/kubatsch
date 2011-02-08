/**
pl * author: Daniel Kuschny (dku2375)
 * created on: 04.02.2011
 * filename: PaddleMovedMessage.java
 * project: KuBatsch
 */
package at.kubatsch.model.message;

/**
 * @author Daniel Kuschny (dku2375)
 *
 */
public class PaddleMovedMessage implements INetworkMessage
{
    private static final long  serialVersionUID = 8068554139613375084L;
    public static final String MESSAGE_ID       = "paddle-moved";

    private float              _newPosition;
    private int                _clientId;

    /**
     * @see at.kubatsch.model.message.INetworkMessage#getMessageId()
     */
    @Override
    public String getMessageId()
    {
        return MESSAGE_ID;
    }

    /**
     * Gets the clientId.
     * @return the clientId
     */
    public int getClientId()
    {
        return _clientId;
    }

    /**
     * Gets the newPosition.
     * @return the newPosition
     */
    public float getNewPosition()
    {
        return _newPosition;
    }

    /**
     * Initializes a new instance of the {@link PaddleMovedMessage} class.
     * @param newPosition
     */
    public PaddleMovedMessage(int clientId, float newPosition)
    {
        _clientId = clientId;
        _newPosition = newPosition;
    }

    /**
     * Initializes a new instance of the {@link PaddleMovedMessage} class.
     * @param newPosition
     */
    public PaddleMovedMessage(float newPosition)
    {
        _newPosition = newPosition;
    }
}
