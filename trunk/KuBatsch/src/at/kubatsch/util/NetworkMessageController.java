/**
 * author: Daniel Kuschny (dku2375)
 * created on: 05.02.2011
 * filename: NetworkTransmitter.java
 * project: KuBatsch
 */
package at.kubatsch.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import at.kubatsch.model.message.INetworkMessage;
import at.kubatsch.server.controller.NetworkGameClientEventArgs;
import at.kubatsch.server.controller.NetworkMessageEventArgs;

/**
 * The {@link NetworkMessageController} handles all Messages throught
 * network so that the server and the client can interact with them.
 * @author Daniel Kuschny (dku2375)
 */
public abstract class NetworkMessageController
{
    private static final int                  UPDATE_INTERVAL  = 100;

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

    private int                               _messageSendingInterval        = UPDATE_INTERVAL;

    private boolean                           _needsReset;

    /**
     * Gets the message sending interval in milliseconds.
     * The messageWriter will write the messages using this millisecond interval.
     * @return the interval
     */
    public synchronized int getMessageSendingInterval()
    {
        return _messageSendingInterval;
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
     * Sets the message sending interval in milliseconds.
     * The messageWriter will write the messages in the millisecond interval
     * specified by this call.
     * @param interval the interval to set
     */
    public synchronized void setMessageSendingInterval(int interval)
    {
        _messageSendingInterval = interval;
    }

    /**
     * Adds a {@link INetworkMessage} on the stack so the the Network controller can
     * send it to the Server / Client
     * @param msg which you want to send
     */
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
            catch (IOException e)
            {
                _connectionLost.fireEvent(new NetworkGameClientEventArgs(getClientUid()));
                setRunning(false);
            }

            if (updateTime == getLastUpdate())
            {
                // calculate how many time we need to wait
                long timeElapsed = System.currentTimeMillis() - startTime;
                long waitTime = _messageSendingInterval - timeElapsed;
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
                _messageReceived.fireEvent(new NetworkMessageEventArgs(getClientUid(),
                        msg));
            }
            catch (Exception e)
            {
                // on connection error fire event
                _connectionLost.fireEvent(new NetworkGameClientEventArgs(getClientUid()));
                setRunning(false);
            }
        }
    }

    /**
     * flag that the {@link NetworkMessageController} send Messages through the network.
     * @return if the {@link NetworkMessageController} is sending Message through the network
     */
    public boolean isRunning()
    {
        return _isRunning;
    }

    /**
     * Sets the Running flag
     * @see NetworkMessageController#isRunning()
     * @param isRunning
     */
    public void setRunning(boolean isRunning)
    {
        _isRunning = isRunning;
    }

    /**
     * get the last update which the {@link NetworkMessageController} has send
     * @return last update
     */
    protected long getLastUpdate()
    {
        synchronized (_lastUpdateLock)
        {
            return _lastUpdate;
        }
    }

    /**
     * Set the last update that the {@link NetworkMessageController} has send
     * @param lastUpdate
     */
    protected void setLastUpdate(long lastUpdate)
    {
        synchronized (_lastUpdateLock)
        {
            _lastUpdate = lastUpdate;
        }
    }

    /**
     * Get the ClientUid
     * @return ClientUid
     */
    public int getClientUid()
    {
        return _clientUid;
    }

    /**
     * sets the clientUid
     * @param clientUid
     */
    public void setClientUid(int clientUid)
    {
        _clientUid = clientUid;
    }

    /**
     * Set the {@link InputStream} for the Network connection. This should normally be the {@link Socket#getInputStream()}.
     * @param objectInputStream {@link InputStream} (normal the {@link Socket#getInputStream()}
     */
    protected void setObjectInputStream(ObjectInputStream objectInputStream)
    {
        _objectInputStream = objectInputStream;
    }

    /**
     * Set the {@link OutputStream} for the network connection. This should normal be the {@link Socket#getOutputStream()}.
     * @param objectOutputStream {@link OutputStream} normally the {@link Socket#getOutputStream()}
     */
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
    public void addMessageReceivedListener(IEventHandler<NetworkMessageEventArgs> handler)
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
