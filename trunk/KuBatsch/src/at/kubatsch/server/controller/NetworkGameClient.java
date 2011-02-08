/**
 * author: Manuel Tscholl(mts3970)
 * created on: 26.01.2011
 * filename: NetworkGameClient.java
 * project: KuBaTsch
 */
package at.kubatsch.server.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

import org.apache.log4j.Logger;

import at.kubatsch.model.message.SetUniqueIdMessage;
import at.kubatsch.util.NetworkMessageController;

/**
 * For each Client which connects to an QuadPuck Server an Instance of this
 * class exists and manages the update between Server and Client For correct
 * working of this class a synchronized map and list is a must have!
 * @author Manuel Tscholl (mts3970)
 */
public class NetworkGameClient extends NetworkMessageController
{
    private static Logger LOGGER = Logger.getLogger(NetworkGameClient.class);
    
    /**
     * Initializes a new instance of the {@link NetworkGameClient} class.
     */
    public NetworkGameClient(Socket serverSocket,
            NetworkControllerServer serverConnectedTo)
    {
        super();
        setClientUid(Math.abs(UUID.randomUUID().hashCode()));

        setSocket(serverSocket);

        setNeedsReset(true);
        setInterval(30);
        
        // notify unique ID to client
        addToMessageStack(new SetUniqueIdMessage(getClientUid()));
    }

 
    /**
     * 
     * @param serverSocket the Socket of the server from which the outputstream
     *            will be requested
     */
    public void setSocket(Socket serverSocket)
    {
        try
        {
            OutputStream outputStream = serverSocket.getOutputStream();
            setObjectOutputStream(new ObjectOutputStream(outputStream));

            InputStream inputStream = serverSocket.getInputStream();
            setObjectInputStream(new ObjectInputStream(inputStream));
        }
        catch (IOException e)
        {
            LOGGER.fatal(e);
        }
    }
}
