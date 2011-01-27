/**
 * author: Manuel Tscholl(mts3970)
 * created on: 26.01.2011
 * filename: NetworkControllerServer.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.networktest.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import at.kubatsch.model.Ball;
import at.kubatsch.model.ICollidable;
import at.kubatsch.samples.networktest.MaximumPlayerReachedException;
import at.kubatsch.samples.networktest.NetworkGameClient;

/**
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
     */
    public NetworkControllerClient(String host, int portToListen)
            throws IOException
    {
        super();
        _clientSocket = new Socket(host, portToListen);
        _objectInputStream = new ObjectInputStream(
                _clientSocket.getInputStream());
        _collidables = new HashMap<String, List<ICollidable>>();
        _isRunning = true;

        _updateCollidables = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (_isRunning)
                {
                    try
                    {                        
                        Object temp = _objectInputStream.readObject();
                        @SuppressWarnings("unchecked")
                        Map<String, List<ICollidable>> tempCollidables = (Map<String, List<ICollidable>>) temp;
                        System.out.println("Client bekommt Bälle:"
                                + tempCollidables.get("BALL").size());
                        for (Entry<String, List<ICollidable>> collidable : tempCollidables
                                .entrySet())
                        {
                            _collidables.put(collidable.getKey(),
                                    collidable.getValue());
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    catch (ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        }, "UpdateCollidables");

        _updateCollidables.start();

    }

    /**
     * @param object
     */
    public void setCollidables(Map<String, List<ICollidable>> collidable)
    {
        _collidables = collidable;
    }

    /**
     * Gets the collidables.
     * @return the collidables
     */
    public synchronized Map<String, List<ICollidable>> getCollidables()
    {
        return _collidables;
    }

}
