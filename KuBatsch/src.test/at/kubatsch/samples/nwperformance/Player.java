/**
 * This file is part of KuBatsch.
 *   created on: 03.01.2011
 *   filename: Player.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.nwperformance;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Daniel Kuschny (dku2375)
 *
 */
public class Player implements Runnable
{
    private Socket _socket;
    private Server _server;
    
    private double _position;
    private Thread _clientHandler;
    private ObjectInputStream _input;
    private ObjectOutputStream _output;
    
    private PlayerInfo _info;
    
    /**
     * Gets the info.
     * @return the info
     */
    public synchronized PlayerInfo getInfo()
    {
        return _info;
    }

    /**
     * Sets the info.
     * @param info the info to set
     */
    public synchronized void setInfo(PlayerInfo info)
    {
        _info = info;
    }

    /**
     * Initializes a new instance of the {@link Player} class.
     * @param server 
     * @throws IOException 
     */
    public Player(Server server, Socket socket) throws IOException
    {
        _socket = socket;
        _server = server;
        _input = new ObjectInputStream(_socket.getInputStream());
        _output = new ObjectOutputStream(_socket.getOutputStream());
        _clientHandler = new Thread(this);
        _clientHandler.start();
        setInfo(new PlayerInfo());
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                Object obj = _input.readObject();
                if(obj instanceof PlayerInfo)
                    setInfo((PlayerInfo)_input.readObject());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param info
     */
    public void update(PlayerInfo[] info)
    {
        try
        {
            _output.writeObject(info);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    
    
    
}
