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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import at.kubatsch.model.message.INetworkMessage;
import at.kubatsch.util.Event;
import at.kubatsch.util.IEventHandler;

/**
 * Handels the communication between server and client. The maximum of connected
 * clients which this Controler can have is 4
 * 
 * @author Manuel Tscholl (mts3970)
 * 
 */
public final class NetworkControllerServer
{
    /**
     * the maximum of Players which connect to the server
     */
    private static final int                  MAX_PLAYERS             = 4;
    private ServerSocket                      _serverSocket;
    private List<NetworkGameClient>           _networkGameClients;
    private Thread                            _waitForPlayers;
    private boolean                           _isRunning;

    private Event<NetworkGameClientEventArgs> _clientConnectedEvent;
    private Event<NetworkMessageEventArgs>    _clientMessageEvent;
    private Event<NetworkGameClientEventArgs> _clientDisconnectedEvent;

    private Lock                              _networkGameClientsLock = new ReentrantLock();

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

        _clientConnectedEvent = new Event<NetworkGameClientEventArgs>(this);
        _clientMessageEvent = new Event<NetworkMessageEventArgs>(this);
        _clientDisconnectedEvent = new Event<NetworkGameClientEventArgs>(this);

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

                    _networkGameClientsLock.lock();
                    try
                    {
                        _networkGameClients.add(client);
                    }
                    finally
                    {
                        _networkGameClientsLock.unlock();
                    }

                    client.addMessageReceivedListener(new IEventHandler<NetworkMessageEventArgs>()
                    {

                        @Override
                        public void fired(Object sender, NetworkMessageEventArgs e)
                        {
                            _clientMessageEvent.fireEvent(e);
                        }
                    });

                    client.addConnectionLostListener(new IEventHandler<NetworkGameClientEventArgs>()
                    {

                        @Override
                        public void fired(Object sender, NetworkGameClientEventArgs e)
                        {
                            clientDisconnected(((NetworkGameClient) sender));
                        }
                    });

                    client.startWork();

                    _clientConnectedEvent.fireEvent(new NetworkGameClientEventArgs(client
                            .getClientUid()));
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
        _networkGameClientsLock.lock();
        try
        {
            return _networkGameClients.size();
        }
        finally
        {
            _networkGameClientsLock.unlock();
        }
    }

    /**
     * 
     * @param collidable the new mapping of collidables to update to all clients
     *            (all types will be added/updated)
     */
    public void sendMessageToClients(INetworkMessage message)
    {
        _networkGameClientsLock.lock();
        try
        {
            for (NetworkGameClient client : _networkGameClients)
            {// updates each client packet
                client.addToMessageStack(message);
            }
        }
        finally
        {
            _networkGameClientsLock.unlock();
        }
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#addHandler(at.kubatsch.util.IEventHandler)
     */
    public void addClientConnectedListener(
            IEventHandler<NetworkGameClientEventArgs> handler)
    {
        _clientConnectedEvent.addHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#removeHandler(at.kubatsch.util.IEventHandler)
     */
    public void removeClientConnectedListener(
            IEventHandler<NetworkGameClientEventArgs> handler)
    {
        _clientConnectedEvent.removeHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#addHandler(at.kubatsch.util.IEventHandler)
     */
    public void addClientDisconnectedListener(
            IEventHandler<NetworkGameClientEventArgs> handler)
    {
        _clientDisconnectedEvent.addHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#removeHandler(at.kubatsch.util.IEventHandler)
     */
    public void removedClientDisconnectedListener(
            IEventHandler<NetworkGameClientEventArgs> handler)
    {
        _clientDisconnectedEvent.removeHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#addHandler(at.kubatsch.util.IEventHandler)
     */
    public void addClientMessageListener(IEventHandler<NetworkMessageEventArgs> handler)
    {
        _clientMessageEvent.addHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#removeHandler(at.kubatsch.util.IEventHandler)
     */
    public void removeClientMessageListener(IEventHandler<NetworkMessageEventArgs> handler)
    {
        _clientMessageEvent.removeHandler(handler);
    }

    /**
     * Disconnects a client from the server
     * @param networkGameClient
     */
    public void clientDisconnected(NetworkGameClient networkGameClient)
    {
        _networkGameClientsLock.lock();
        try
        {
            if (!_networkGameClients.contains(networkGameClient))
                return;
            _networkGameClients.remove(networkGameClient);
        }
        finally
        {
            _networkGameClientsLock.unlock();
        }

        System.out.println("Client Disconnect (nwcs)");
        networkGameClient.setRunning(false);
        // TODO: cleanup registered listeners for this instance
        _clientDisconnectedEvent.fireEvent(new NetworkGameClientEventArgs(
                networkGameClient.getClientUid()));

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
