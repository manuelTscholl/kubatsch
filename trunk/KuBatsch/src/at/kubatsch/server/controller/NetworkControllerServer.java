/**
 * author: Manuel Tscholl(mts3970)
 * created on: 26.01.2011
 * filename: NetworkControllerServer.java
 * project: KuBaTsch
 */
package at.kubatsch.server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import at.kubatsch.model.GameState;
import at.kubatsch.model.ICollidable;
import at.kubatsch.server.model.NetworkGameClient;
import at.kubatsch.util.Event;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;
import at.kubatsch.util.PaddleEventArgs;

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
    private final int               MAX_PLAYERS = 4;
    private ServerSocket            _serverSocket;
    private List<NetworkGameClient> _networkGameClients;
    private Thread                  _waitForPlayers;
    private boolean                 _isRunning;
    private Event<EventArgs>        _newPaddleArrivedEvent;

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
        _newPaddleArrivedEvent = new Event<EventArgs>(this);
        _waitForPlayers = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                connectClient();
            }
        }, "PlayerConnecter");

        setRunning(true);
        _waitForPlayers.start();
    }

    /**
     * Gets the newPaddleArrivedEvent.
     * @return the newPaddleArrivedEvent
     */
    public void addNewPaddleArrivedHandler(IEventHandler<EventArgs> handler)
    {
        _newPaddleArrivedEvent.addHandler(handler);
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

                    client.addNewPaddleArrivedHandler(
                            new IEventHandler<EventArgs>()
                            {
                                @Override
                                public void fired(Object sender, EventArgs e)
                                {
                                    _newPaddleArrivedEvent.fireEvent(e);
                                }
                            });

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
    public void setGameState(GameState state)
    {
        synchronized (state)
        {
            for (NetworkGameClient client : _networkGameClients)
            {// updates each client packet
                client.setDataPacket(state);
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
    public synchronized void setRunning(boolean isRunning)
    {
        _isRunning = isRunning;
    }

}
