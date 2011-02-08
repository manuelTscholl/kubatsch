/**
 * author: Daniel Kuschny (dku2375)
 * created on: 04.02.2011
 * filename: NetworkGameClientEventArgs.java
 * project: KuBatsch
 */
package at.kubatsch.server.controller;

import at.kubatsch.util.EventArgs;

/**
 * Network EventArgs for the Client
 * @author Daniel Kuschny (dku2375)
 */
public class NetworkGameClientEventArgs extends EventArgs
{
    private int _clientUid;

    /**
     * Gets the client.
     * @return the client
     */
    public int getClientUid()
    {
        return _clientUid;
    }

    /**
     * Initializes a new instance of the {@link NetworkGameClientEventArgs} class.
     * @param client
     */
    public NetworkGameClientEventArgs(int uid)
    {
        super();
        _clientUid = uid;
    }
}
