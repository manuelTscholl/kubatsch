/**
 * author: Manuel Tscholl(mts3970)
 * created on: 26.01.2011
 * filename: NetworkGameClient.java
 * project: KuBaTsch
 */
package at.kubatsch.server.model;

import java.awt.Component.BaselineResizeBehavior;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.kubatsch.model.Ball;
import at.kubatsch.model.GameState;
import at.kubatsch.model.ICollidable;
import at.kubatsch.server.controller.NetworkControllerServer;

/**
 * For each Client which connects to an QuadPuck Server an Instance of this
 * class exists and manages the update between Server and Client
 * For correct working of this class a synchronized map and list is a must have!
 * @author Manuel Tscholl (mts3970)
 * 
 */
public class NetworkGameClient extends Thread
{

    OutputStream                    _outputStream;
    ObjectOutputStream              _objectOutputStream;
    private boolean                 _isRunning;

    private GameState _dataPacket;
    private NetworkControllerServer _server;

    /**
     * Initializes a new instance of the {@link NetworkGameClient} class.
     */
    public NetworkGameClient(NetworkControllerServer serverConnectedTo)
    {
        super();
        _server = serverConnectedTo;

    }

    /**
     * Initializes a new instance of the {@link NetworkGameClient} class.
     */
    public NetworkGameClient(Socket serverSocket,
            NetworkControllerServer serverConnectedTo)
    {
        super();
        _server = serverConnectedTo;
        setSocket(serverSocket);
    }

    /**
     * Gets the dataPacket.
     * @return the dataPacket
     */
    public GameState getDataPacket()
    {
        return _dataPacket;        
    }

    /**
     * Needs a synchronized map and list!
     * Sets the dataPacket.
     * @param dataPacket the dataPacket to set
     */
    public void setDataPacket(GameState dataPacket)
    {
        _dataPacket = dataPacket;
    }

    /**
     * @see java.lang.Thread#run()
     */
    @Override
    public void run()
    {
        while (_isRunning)
        {
            if (_dataPacket != null)
            {
                GameState data = _dataPacket;
                if (data != null)
                {
                    updateCollidable(data);
                    setDataPacket(null);
                }
            }

            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
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
            _outputStream = serverSocket.getOutputStream();
            _objectOutputStream = new ObjectOutputStream(_outputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Sends the new mapping to this connected clients
     * @param balls the updated List of balls which should be sent
     */
    public void updateCollidable(GameState collidable)
    {
        try
        {
            // System.out.println("Sending Balls:"+collidable.get("BALL").size());
            _objectOutputStream.writeObject(collidable);
            _objectOutputStream.flush();
            _objectOutputStream.reset();//deletes the cache of the stream
        }
        catch (IOException e)
        {
            _server.clientDisconnected(this);
        }
    }
    

    /**
     * @see java.lang.Thread#start()
     */
    @Override
    public synchronized void start()
    {
        super.start();
        System.out.println("new client accepted");
        isRunning(true);
        
    }
    
    /**
     * It is possible to stop the update process of the thread with this method
     * @param isRunning
     */
    public void isRunning(boolean isRunning)
    {
        _isRunning = isRunning;

    }

    /**
     * 
     * @return the running state
     */
    public boolean isRunning()
    {
        return _isRunning;
    }

}
