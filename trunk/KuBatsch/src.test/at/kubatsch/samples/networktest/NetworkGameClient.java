/**
 * author: Manuel Tscholl(mts3970)
 * created on: 26.01.2011
 * filename: NetworkGameClient.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.networktest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.kubatsch.model.Ball;
import at.kubatsch.model.ICollidable;

/**
 * For each Client which connects to an QuadPuck Server an Instance of this
 * class exists and manages the update between Server and Client
 * @author Manuel Tscholl (mts3970)
 * 
 */
public class NetworkGameClient
{

    OutputStream       _outputStream;
    ObjectOutputStream _objectOutputStream;

    /**
     * Initializes a new instance of the {@link NetworkGameClient} class.
     */
    public NetworkGameClient()
    {
        super();
    }
    
    

    /**
     * Initializes a new instance of the {@link NetworkGameClient} class.
     */
    public NetworkGameClient(Socket serverSocket)
    {
        super();
        setSocket(serverSocket);
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
     * Sends a new Array to this client
     * @param balls the updated List of balls which should be sent
     */
    public void updateCollidable(Map<String,List<ICollidable>> collidable)
    {
        try
        {
            System.out.println("Sending Balls:"+collidable.get("BALL").size());            
            _objectOutputStream.writeObject(collidable);
            _objectOutputStream.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
