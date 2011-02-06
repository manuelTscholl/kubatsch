/**
 * author: Daniel Kuschny (dku2375)
 * created on: 05.02.2011
 * filename: NetworkTransmitter.java
 * project: KuBatsch
 */
package at.kubatsch.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import at.kubatsch.model.message.INetworkMessage;
import at.kubatsch.server.controller.NetworkGameClientEventArgs;
import at.kubatsch.server.controller.NetworkMessageEventArgs;

/**
 * @author Daniel Kuschny (dku2375)
 * 
 */
public abstract class NetworkMessageController
{
    private final int                         UPDATE_INTERVAL  = 100;

    private ObjectOutputStream                _objectOutputStream;
    private ObjectInputStream                 _objectInputStream;

    private boolean                           _isRunning;

    private Thread                            _messageReader;
    private Thread                            _messageWriter;

    private Stack<INetworkMessage>            _messagesToSend  = new Stack<INetworkMessage>();

    private Event<NetworkMessageEventArgs>    _messageReceived = new Event<NetworkMessageEventArgs>(
                                                                       this);
    private Event<NetworkGameClientEventArgs> _connectionLost  = new Event<NetworkGameClientEventArgs>(
                                                                       this);

    private Object                            _lastUpdateLock  = new Object();
    private long                              _lastUpdate;
    private int                               _clientUid;

    private int                               _interval        = UPDATE_INTERVAL;

    private boolean                           _needsReset;

    /**
     * Gets the interval.
     * @return the interval
     */
    public synchronized int getInterval()
    {
        return _interval;
    }

    /**
     * Gets the needsReset.
     * @return the needsReset
     */
    protected boolean needsReset()
    {
        return _needsReset;
    }

    /**
     * Sets the needsReset.
     * @param needsReset the needsReset to set
     */
    protected void setNeedsReset(boolean needsReset)
    {
        _needsReset = needsReset;
    }

    /**
     * Sets the interval.
     * @param interval the interval to set
     */
    public synchronized void setInterval(int interval)
    {
        _interval = interval;
    }

    public void addToMessageStack(INetworkMessage msg)
    {
        synchronized (_messagesToSend)
        {
            _messagesToSend.push(msg);
            setLastUpdate(System.currentTimeMillis());
        }
    }

    /**
     * Starts the message handler to receive and send messages
     */
    public void startWork()
    {
        setRunning(true);
        // start reading
        _messageReader = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                messageReaderLoop();
            }
        }, "MessageReader");
        _messageReader.start();
        // start writing
        _messageWriter = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                messageWriterLoop();
            }
        }, "MessageWriter");
        _messageWriter.start();
    }

    /**
     * Writes messages to the current end point in a fixed interval. Each
     * message type will only sent once. the newest messages will be sent first.
     */
    private void messageWriterLoop()
    {
        while (isRunning())
        {
            long updateTime = getLastUpdate();
            long startTime = System.currentTimeMillis();

            try
            {
                synchronized (_messagesToSend)
                {
                    Set<String> sendMessageTypes = new HashSet<String>();
                    while (!_messagesToSend.isEmpty())
                    {
                        INetworkMessage data = _messagesToSend.pop();
                        if (data != null
                                && !sendMessageTypes.contains(data.getMessageId()))
                        {
                            submitMessage(data);
                            sendMessageTypes.add(data.getMessageId());
                        }
                    }
                }
            }
            catch(IOException e)
            {
                _connectionLost.fireEvent(new NetworkGameClientEventArgs(
                        getClientUid()));
                setRunning(false);
            }

            if (updateTime == getLastUpdate())
            {
                // calculate how many time we need to wait
                long timeElapsed = System.currentTimeMillis() - startTime;
                long waitTime = _interval - timeElapsed;
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
     * Submits a message to the endpoint
     * @param data
     * @throws IOException
     */
    private void submitMessage(INetworkMessage data) throws IOException
    {
        _objectOutputStream.writeObject(data);
        _objectOutputStream.flush();
        if (_needsReset)
            _objectOutputStream.reset();
    }

    /**
     * Reads messages from the endpoint and notifies the receive using an event.
     */
    private void messageReaderLoop()
    {
        while (isRunning())
        {
            try
            {
                // get message from end point
                Object o = _objectInputStream.readObject();

                if (o == null)
                    continue;

                if (!(o instanceof INetworkMessage))
                {
                    System.out.printf("Got an unknown object: %s%n", o);
                    continue;
                }

                INetworkMessage msg = (INetworkMessage) o;
                _messageReceived.fireEvent(new NetworkMessageEventArgs(
                        getClientUid(), msg));
            }
            catch (IOException e)
            {
                // on connection error fire event
                _connectionLost.fireEvent(new NetworkGameClientEventArgs(
                        getClientUid()));
                setRunning(false);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean isRunning()
    {
        return _isRunning;
    }

    public void setRunning(boolean isRunning)
    {
        _isRunning = isRunning;
    }

    protected long getLastUpdate()
    {
        synchronized (_lastUpdateLock)
        {
            return _lastUpdate;
        }
    }

    protected void setLastUpdate(long lastUpdate)
    {
        synchronized (_lastUpdateLock)
        {
            _lastUpdate = lastUpdate;
        }
    }

    public int getClientUid()
    {
        return _clientUid;
    }

    public void setClientUid(int clientUid)
    {
        _clientUid = clientUid;
    }

    protected void setObjectInputStream(ObjectInputStream objectInputStream)
    {
        _objectInputStream = objectInputStream;
    }

    protected void setObjectOutputStream(ObjectOutputStream objectOutputStream)
    {
        _objectOutputStream = objectOutputStream;
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#addHandler(at.kubatsch.util.IEventHandler)
     */
    public void addConnectionLostListener(
            IEventHandler<NetworkGameClientEventArgs> handler)
    {
        _connectionLost.addHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#removeHandler(at.kubatsch.util.IEventHandler)
     */
    public void removeConnectionLostListener(
            IEventHandler<NetworkGameClientEventArgs> handler)
    {
        _connectionLost.removeHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#addHandler(at.kubatsch.util.IEventHandler)
     */
    public void addMessageReceivedListener(
            IEventHandler<NetworkMessageEventArgs> handler)
    {
        _messageReceived.addHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#removeHandler(at.kubatsch.util.IEventHandler)
     */
    public void removeMessageReceivedListener(
            IEventHandler<NetworkMessageEventArgs> handler)
    {
        _messageReceived.removeHandler(handler);
    }
}
