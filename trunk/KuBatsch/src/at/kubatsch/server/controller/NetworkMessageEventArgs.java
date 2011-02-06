/**
 * author: Daniel Kuschny (dku2375)
 * created on: 04.02.2011
 * filename: PaddleMovedEventArgs.java
 * project: KuBatsch
 */
package at.kubatsch.server.controller;

import at.kubatsch.model.message.INetworkMessage;

/**
 * @author Daniel Kuschny (dku2375)
 *
 */
public class NetworkMessageEventArgs extends NetworkGameClientEventArgs
{
    private INetworkMessage _message;

    /**
     * Gets the message.
     * @return the message
     */
    public INetworkMessage getMessage()
    {
        return _message;
    }

    /**
     * Initializes a new instance of the {@link NetworkMessageEventArgs} class.
     * @param message
     */
    public NetworkMessageEventArgs(int uid, INetworkMessage message)
    {
        super(uid);
        _message = message;
    }
    /**
     * Initializes a new instance of the {@link NetworkMessageEventArgs} class.
     * @param message
     */
    public NetworkMessageEventArgs(INetworkMessage message)
    {
        this(-1, message);
    }



}
