/**
 * author: Manuel Tscholl(mts3970)
 * created on: 26.01.2011
 * filename: NetworkGameClient.java
 * project: KuBaTsch
 */
package at.kubatsch.server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import at.kubatsch.model.GameState;
import at.kubatsch.model.Paddle;
import at.kubatsch.server.controller.NetworkControllerServer;
import at.kubatsch.util.Event;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;
import at.kubatsch.util.PaddleEventArgs;

/**
 * For each Client which connects to an QuadPuck Server an Instance of this
 * class exists and manages the update between Server and Client For correct
 * working of this class a synchronized map and list is a must have!
 * @author Manuel Tscholl (mts3970)
 * 
 */
public class NetworkGameClient extends Thread
{

    private OutputStream            _outputStream;
    private ObjectOutputStream      _objectOutputStream;
    private ObjectInputStream       _objectInputStream;
    private boolean                 _isRunning;
    @SuppressWarnings("unused")
    private Thread                  _sendingMyPaddle;
    private final int               UPDATE_INTERVAL = 100;
    private Event<EventArgs> _newPaddleArrivedEvent;
    private GameState               _dataPacket;
    private NetworkControllerServer _server;

    private Object                  _lastUpdateLock = new Object();
    private long                    _lastUpdate;

    public long getLastUpdate()
    {
        synchronized (_lastUpdateLock)
        {
            return _lastUpdate;
        }
    }

    public synchronized void setLastUpdate(long lastUpdate)
    {
        synchronized (_lastUpdateLock)
        {
            _lastUpdate = lastUpdate;
        }
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
        _newPaddleArrivedEvent = new Event<EventArgs>(this);
        _sendingMyPaddle = new Thread(new Runnable()
        {

            @Override
            public void run()
            {

                while (_isRunning)
                {
                    try
                    {
                        Paddle tempPaddel = (Paddle) _objectInputStream
                                .readObject();
                        PaddleEventArgs newPaddle = new PaddleEventArgs(tempPaddel);
                        _newPaddleArrivedEvent.fireEvent(newPaddle);
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
        });
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
     * Gets the dataPacket.
     * @return the dataPacket
     */
    public GameState getDataPacket()
    {
        return _dataPacket;
    }

    /**
     * Needs a synchronized map and list! Sets the dataPacket.
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

            long updateTime = getLastUpdate();
            long startTime = System.currentTimeMillis();

            if (_dataPacket != null)
            {
                GameState data = _dataPacket;
                if (data != null)
                {
                    updateGameState(data);
                    setDataPacket(null);
                }
            }

            if (updateTime == getLastUpdate())
            {
                // calculate how many time we need to wait
                long timeElapsed = System.currentTimeMillis() - startTime;
                long waitTime = UPDATE_INTERVAL - timeElapsed;
                if (waitTime > 0)
                {
                    try
                    {
                        Thread.sleep(waitTime);
                    }
                    catch (InterruptedException e)
                    {
                    }
                }
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
            _objectInputStream = new ObjectInputStream(
                    serverSocket.getInputStream());
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
    public void updateGameState(GameState gameState)
    {
        try
        {
            // System.out.println("Sending Balls:"+collidable.get("BALL").size());
            _objectOutputStream.writeObject(gameState);
            _objectOutputStream.flush();
            _objectOutputStream.reset();// deletes the cache of the stream
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
