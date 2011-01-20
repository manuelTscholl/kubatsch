/**
 * author: Manuel Tscholl(mts3970)
 * created on: 19.01.2011
 * filename: networkballserver.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.networkballserver;

import java.io.Console;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.util.ArrayList;

import at.kubatsch.samples.nwperformance.Server;
import at.kubatsch.samples.uiperformance.Ball;
import at.kubatsch.samples.uiperformance.GamePanel;

/**
 * @author Manuel Tscholl (mts3970)
 * 
 */
public class ServerGamePanel extends GamePanel
{

    ArrayList<Player> _clients;
    ServerSocket      _server;

    /**
     * Initializes a new instance of the {@link ServerGamePanel} class.
     * @param clients
     * @param server
     */
    public ServerGamePanel(int port) throws IOException
    {
        super();
        _clients = new ArrayList<Player>();
        _server = new ServerSocket(port);
    }

    /**
     * 
     * @throws IOException 
     * @see at.kubatsch.samples.uiperformance.GamePanel#start()
     */
    public void startServer() throws IOException
    {
        start();
        Player client = new Player();
        client.setSocket(_server.accept());
        _clients.add(client);
        System.out.println("client Connected");
        setRunning(true);
    } 

    /**
     * Gets the clients.
     * @return the clients
     */
    public ArrayList<Player> getClients()
    {
        return _clients;
    }

    /**
     * Sets the clients.
     * @param clients the clients to set
     */
    public void setClients(ArrayList<Player> clients)
    {
        _clients = clients;
    }

    /**
     * @see at.kubatsch.samples.uiperformance.GamePanel#run()
     */
    @Override
    public void run()
    {        
        updateNetwork();
    }

    /**
     * 
     */
    private void updateNetwork()
    {
        while(isRunning())
        {            
            for (Player client : _clients)
            {
                System.out.println("Client update"+getBalls().size());
                client.update(getBalls().toArray(new Ball[getBalls().size()]));
            }
            
            super.run();
        }
    }
    
    /**
     * Sets the running.
     * @param running the running to set
     */
    public synchronized void setRunning(boolean running)
    {
        super.setRunning(running);
    }

}
