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

import at.kubatsch.model.Ball;
import at.kubatsch.model.ICollidable;
import at.kubatsch.samples.networktest.MaximumPlayerReachedException;
import at.kubatsch.samples.networktest.NetworkGameClient;

/**
 * @author Manuel Tscholl (mts3970)
 * 
 */
public class NetworkControllerServer
{

    // the maximum of Players which connect to the server
    final int                      MAXPLAYERS = 4;
    ServerSocket                   _serverSocket;
    List<NetworkGameClient>        _networkGameClients;
    Thread                         _waitForPlayers;
    Thread                         _updatePlayers;
    Map<String, List<ICollidable>> _collidables;
    Object                         _collidablesLock;

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
        _collidablesLock = new Object();

        _waitForPlayers = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                connectClient();
            }
        }, "PlayerConnecter");

        _updatePlayers = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                updateClients(_collidables);
            }
        }, "UpdateCollidables");

        _waitForPlayers.start();
        _updatePlayers.start();

    }

    /**
     * Checks all WAITINGPLAYERSECONDS if a new player wants to connect
     */
    public void connectClient()
    {
        if (countConnectedPlayers() < MAXPLAYERS)
        {
            try
            {// clients connects to the server
                NetworkGameClient client = new NetworkGameClient(
                        _serverSocket.accept());
                _networkGameClients.add(client);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            throw new MaximumPlayerReachedException(
                    "The maximum of players has reached not able to connect mor clients");
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

    public void updateClients(Map<String, List<ICollidable>> collidable)
    {
        synchronized (_collidablesLock)
        {
            if (collidable != null)
            {
                for (NetworkGameClient client : _networkGameClients)
                {
                    client.updateCollidable(collidable);
                }
                setCollidables(null);
            }
        }
        try
        {
            Thread.sleep(4000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        updateClients(_collidables);

    }

    /**
     * @param object
     */
    public void setCollidables(Map<String, List<ICollidable>> collidable)
    {
        synchronized (_collidablesLock)
        {
            _collidables = collidable;
        }
    }

}
