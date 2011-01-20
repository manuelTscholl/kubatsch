/**
 * author: Manuel Tscholl(mts3970)
 * created on: 19.01.2011
 * filename: Client.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.networkballserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import at.kubatsch.samples.uiperformance.Ball;

/**
 * @author Manuel Tscholl (mts3970)
 *
 */
public class Player
{
    OutputStream _outputStream;       
    ObjectOutputStream _objectOutputStream;
    /**
     * Initializes a new instance of the {@link Player} class.
     */
    public Player()
    {
    }



    public void update(Ball[] balls)
    {
        try
        {            
            System.out.println("client sends:"+balls.length);
            _objectOutputStream.writeObject(balls);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }       
        
    }



    /**
     * @param accept
     */
    public void setSocket(Socket socket)
    {
        try
        {
            _outputStream = socket.getOutputStream();
            _objectOutputStream = new ObjectOutputStream(_outputStream); 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
               
    }
}
