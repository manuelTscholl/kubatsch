/**
 * author: Manuel Tscholl(mts3970)
 * created on: 26.01.2011
 * filename: NetworkControllerServer.java
 * project: KuBaTsch
 */
package at.kubatsch.client.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.StreamHandler;

import at.kubatsch.model.ICollidable;
import at.kubatsch.util.Event;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;
import at.kubatsch.util.StreamUtils;

/**
 * This class manages the communication between client and server, when new data
 * from the server came in it will fire a event.
 * 
 * @author Manuel Tscholl (mts3970)
 * 
 */
public class NetworkControllerClient
{

    // the maximum of Players which connect to the server
    Socket                         _clientSocket;
    Thread                         _updateCollidables;
    Map<String, List<ICollidable>> _collidables;
    ObjectInputStream              _objectInputStream;
    boolean                        _isRunning;
    private Event<EventArgs>       _stateUpdated = new Event<EventArgs>(this);
    private Event<EventArgs>       _disconnected = new Event<EventArgs>(this);

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

    /**
     * Initializes a new instance of the {@link NetworkControllerServer} class.
     * @param portToListen the port to which the Server should listen
     * @throws IOException
     * @throws UnknownHostException  
     */
    public NetworkControllerClient(String host, int port) throws IOException
    {
        super();
        _clientSocket = new Socket(host, port);
        
        _objectInputStream = new ObjectInputStream(
                _clientSocket.getInputStream());
        _collidables = new HashMap<String, List<ICollidable>>();
        setRunning(true);

        // updating Thread for the colidabals
        _updateCollidables = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (isRunning())
                {
                    try
                    {
                        // the object which the server sent
                        Object temp = _objectInputStream.readObject();
                        if (temp != null)
                        {
                            @SuppressWarnings("unchecked")
                            Map<String, List<ICollidable>> tempCollidables = (Map<String, List<ICollidable>>) temp;
                            setCollidables(tempCollidables);
                        }
                    }
                    catch (IOException e)
                    {
                        setRunning(false);

                    }
                    catch (ClassNotFoundException e)
                    {
                    }
                }
                
                disconnect();
            }

        }, "UpdateCollidables");

        _updateCollidables.start();

    }
    
    /**
     * Disconnects the client from the server
     */
    public void disconnect()
    {
        setRunning(false);
        if(_clientSocket != null)
        {
            StreamUtils.close(_clientSocket);
        }
        _disconnected.fireEvent(EventArgs.Empty);
    }

    /**
     * 
     * @param collidable the new mapping of collidables to update (all types
     *            will be added/updated)
     */
    public void setCollidables(Map<String, List<ICollidable>> collidable)
    {

        // replaces or inserts each type
        for (Entry<String, List<ICollidable>> tempCollidable : collidable
                .entrySet())
        {
            _collidables
                    .put(tempCollidable.getKey(), tempCollidable.getValue());
        }
        _stateUpdated.fireEvent(EventArgs.Empty);
    }

    /**
     * Gets the collidables.
     * @return the collidables
     */
    public synchronized Map<String, List<ICollidable>> getCollidables()
    {
        return _collidables;
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#addHandler(at.kubatsch.util.IEventHandler)
     */
    public void addStateUpdatedHandler(IEventHandler<EventArgs> handler)
    {
        _stateUpdated.addHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#removeHandler(at.kubatsch.util.IEventHandler)
     */
    public void removeStateUpdatedHandler(IEventHandler<EventArgs> handler)
    {
        _stateUpdated.removeHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#addHandler(at.kubatsch.util.IEventHandler)
     */
    public void addDisconnectedListener(IEventHandler<EventArgs> handler)
    {
        _disconnected.addHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#removeHandler(at.kubatsch.util.IEventHandler)
     */
    public void removeDisconnectedListener(IEventHandler<EventArgs> handler)
    {
        _disconnected.removeHandler(handler);
    }
    
    

}
