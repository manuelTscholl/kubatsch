/**
 * author: Manuel Tscholl(mts3970)
 * created on: 26.01.2011
 * filename: NetworkControllerServer.java
 * project: KuBaTsch
 */
package at.kubatsch.client.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import at.kubatsch.server.controller.NetworkControllerServer;
import at.kubatsch.server.controller.NetworkGameClientEventArgs;
import at.kubatsch.util.IEventHandler;
import at.kubatsch.util.NetworkMessageController;
import at.kubatsch.util.StreamUtils;

/**
 * This class manages the communication between client and server, when new data
 * from the server came in it will fire a event.
 * @author Manuel Tscholl (mts3970)
 */
public class NetworkControllerClient extends NetworkMessageController
{
    private static Logger LOGGER   = Logger.getLogger(NetworkControllerClient.class);
    /**
     * Send messages all 15ms to the server
     */
    private static int    INTERVAL = 15;

    private Socket        _serverConnection;

    /**
     * Initializes a new instance of the {@link NetworkControllerServer} class.
     * @param portToListen the port to which the Server should listen
     * @throws IOException when the connection to the {@link Socket} went wrong
     * @throws UnknownHostException when the host is wrong
     */
    public NetworkControllerClient(String host, int port) throws IOException
    {
        super();
        _serverConnection = new Socket(host, port);

        OutputStream outputStream = _serverConnection.getOutputStream();
        setObjectOutputStream(new ObjectOutputStream(outputStream));

        InputStream inputStream = _serverConnection.getInputStream();
        setObjectInputStream(new ObjectInputStream(inputStream));

        setMessageSendingInterval(INTERVAL);

        addConnectionLostListener(new IEventHandler<NetworkGameClientEventArgs>()
        {
            @Override
            public void fired(Object sender, NetworkGameClientEventArgs e)
            {
                LOGGER.info("Client Disconnected");
            }
        });
    }

    /**
     * Disconnects the client from the server
     */
    public void disconnect()
    {
        setRunning(false);
        if (_serverConnection != null)
        {
            StreamUtils.close(_serverConnection);
        }
    }
}
