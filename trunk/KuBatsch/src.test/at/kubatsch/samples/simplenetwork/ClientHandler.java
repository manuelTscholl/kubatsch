/**
 * author: Daniel Kuschny (dku2375)
 * created on: 27.01.2011
 * filename: ClientHandler.java
 * project: KuBatsch
 */
package at.kubatsch.samples.simplenetwork;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import at.kubatsch.util.Event;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

/**
 * @author Daniel Kuschny (dku2375)
 *
 */
public class ClientHandler extends Thread
{
    private Server _server;
    private Socket _client;
    private ObjectOutputStream _out;
    private List<DataPackage> _toSend;
    
    private synchronized List<DataPackage> getPackages()
    {
        return _toSend;
    }

    /**
     * Initializes a new instance of the {@link ClientHandler} class.
     * @param client
     */
    public ClientHandler(Server server, Socket client)
    {
        super();
        _server = server;
        _client = client;
        try
        {
            _out = new ObjectOutputStream(client.getOutputStream());
        }
        catch(Exception e)
        {
            System.out.println("Could not open client connection: " + e.toString());
        }
    }
    
    private boolean isRunning()
    {
        return _server.isRunning() && _out != null;
    }
    
    /**
     * @see java.lang.Thread#run()
     */
    @Override
    public void run()
    {
        while(_server.isRunning())
        {
            try
            {
                List<DataPackage> packages = getPackages();
                if(packages != null)
                {
                    try
                    {
                        _out.writeObject(packages);
                    }
                    catch (IOException e)
                    {
                        System.out.println("error sending data");
                    }
                    try
                    {
                        _out.reset();
                    }
                    catch (IOException e)
                    {
                    }
                    setPackages(null);
                    _clientUpdated.fireEvent(EventArgs.Empty);
                }
                wait();
            }
            catch (InterruptedException e)
            {
            }
        }
        
        System.out.println("Client exiting");
    }

    /**
     * @param packages
     */
    public synchronized void setPackages(List<DataPackage> packages)
    {
        _toSend = packages;
        notify();
    }
    
    private Event<EventArgs> _clientUpdated = new Event<EventArgs>(this);

    /**
     * @param handler
     * @see at.kubatsch.util.Event#addHandler(at.kubatsch.util.IEventHandler)
     */
    public void addClientChangedListener(IEventHandler<EventArgs> handler)
    {
        _clientUpdated.addHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#removeHandler(at.kubatsch.util.IEventHandler)
     */
    public void removeClientChangedListener(IEventHandler<EventArgs> handler)
    {
        _clientUpdated.removeHandler(handler);
    }
    
}
