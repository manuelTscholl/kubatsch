/**
 * This file is part of KuBatsch.
 *   created on: 02.01.2011
 *   filename: Server.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.nwperformance;

import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class Server
{
    private static final int PLAYER_COUNT = 4;
    private ServerSocket _server;
    private List<Player> _clients;

    /**
     * Initializes a new instance of the {@link Server} class.
     */
    public Server(int port) throws IOException
    {
        System.out.printf("Starting server on Port %d%n", port);
        _server = new ServerSocket(port);
        _clients = new ArrayList<Player>(PLAYER_COUNT);
    }

    private void start()
    {
        // get 4 clients
        waitForClients();
        
        notifyLoop();
    }
    
    private void notifyLoop()
    {
        while(true)
        {
            // update clients
            PlayerInfo[] info = new PlayerInfo[PLAYER_COUNT];
            for (int i = 0; i < PLAYER_COUNT; i++)
            {
                info[i] = _clients.get(i).getInfo();
            }
            
            for (Player player : _clients)
            {
                player.update(info);
            }
            
            try
            {
                Thread.sleep(20);
            }
            catch (InterruptedException e)
            {
            }
        }
    }

    private synchronized int clientCount()
    {
        return _clients.size();
    }

    private void waitForClients()
    {
        while (clientCount() < PLAYER_COUNT)
        {
            Socket client = null;
            try
            {
                System.out.println("Waiting for more Clients");
                client = _server.accept();
                System.out.println("New Client accepted");
                _clients.add(new Player(this, client));                
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        Server server = new Server(25000);
        server.start();
    }

}
