/**
 * author: Manuel Tscholl(mts3970)
 * created on: 26.01.2011
 * filename: NetworkControllerServer.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.networktest.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import at.kubatsch.model.ICollidable;

/**
 * Handels the communication between server and client. The maximum of connected
 * clients which this Controler can have is 4
 * 
 * @author Manuel Tscholl (mts3970)
 * 
 */
public class NetworkControllerServer
{
    /**
     * the maximum of Players which connect to the server
     */
    final int               MAX_PLAYERS = 4;
    ServerSocket            _serverSocket;
    List<NetworkGameClient> _networkGameClients;
    Thread                  _waitForPlayers;
    boolean                 _isRunning;

    /**
     * Initializes a new instance of the {@link NetworkControllerServer} class.
     * @param portToListen the port to which the Server should listen
     * @throws IOException
     */
    public NetworkControllerServer(int portToListen) throws IOException
    {
        super();
        _serverSocket = new ServerSocket(portToListen);
        _networkGameClients = new ArrayList<NetworkGameClient>();

        _waitForPlayers = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                connectClient();
            }
        }, "PlayerConnecter");
        
        isRunning(true);
        _waitForPlayers.start();
    }

    /**
     * Accepts new clients als long as the server is not full
     * (clients<MAXPLAYERS)
     */
    public void connectClient()
    {
        while (isRunning())
        {
            if (countConnectedPlayers() < MAX_PLAYERS)
            {
                try
                {// clients connects to the server
                    NetworkGameClient client = new NetworkGameClient(
                            _serverSocket.accept(), this);//
                    _networkGameClients.add(client);
                    client.start();// client Thread is started
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("Client connected");
    }

    /**
     * 
     * @return the number of players which are connected to the server
     */
    public int countConnectedPlayers()
    {
        return _networkGameClients.size();
    }

    /**
     * 
     * @param collidable the new mapping of collidables to update to all clients
     *            (all types will be added/updated)
     */
    public void setCollidables(Map<String, List<ICollidable>> collidable)
    {
        synchronized (collidable)
        {
            for (NetworkGameClient client : _networkGameClients)
            {// updates each client packet
                client.setDataPacket(collidable);
            }
        }
    }

    /**
     * Disconnects a client from the server
     * @param networkGameClient
     */
    public void clientDisconnected(NetworkGameClient networkGameClient)
    {
        networkGameClient.isRunning(false);
        _networkGameClients.remove(networkGameClient);
        System.out.println("Client Disconnected");
    }

    /**
     * Gets the isRunning.
     * @return the isRunning
     */
    public synchronized boolean isRunning()
    {
        return _isRunning;
    }

    /**
     * Sets the isRunning.
     * @param isRunning the isRunning to set
     */
    public synchronized void isRunning(boolean isRunning)
    {
        _isRunning = isRunning;
    }

}
