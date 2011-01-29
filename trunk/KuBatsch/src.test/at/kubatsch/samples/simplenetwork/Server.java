/**
 * author: Daniel Kuschny (dku2375)
 * created on: 27.01.2011
 * filename: Server.java
 * project: KuBatsch
 */
package at.kubatsch.samples.simplenetwork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class Server
{
    private boolean             _isRunning;
    private List<ClientHandler> _clients;

    private List<DataPackage>   _packages;

    private synchronized List<DataPackage> getPackages()
    {
        return _packages;
    }

    private synchronized List<ClientHandler> getClients()
    {
        return _clients;
    }

    public Server(int port)
    {
        _clients = new ArrayList<ClientHandler>();
        _packages = new ArrayList<DataPackage>();

        try
        {
            System.out.println("Starting Server");
            ServerSocket socket = new ServerSocket(port);
            setRunning(true);
            // timer for adding elements
            
            new Thread(new Runnable()
            {
                
                @Override
                public void run()
                {
                    while(true)
                    {
                        System.out.println("Add new package");
                        Random rnd = new Random();
                        getPackages().add(
                                new DataPackage(rnd.nextInt(100), rnd.nextInt(100),
                                        rnd.nextInt(100), rnd.nextInt(100)));
                        updateClients();        
                        try
                        {
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            while (true)
            {
                System.out.println("Waiting for Clients");
                Socket client = socket.accept();
                System.out.println("New Client Accepted, Start working");
                ClientHandler handler = new ClientHandler(this, client);
                getClients().add(handler);
                handler.start();
            }
        }
        catch (IOException e)
        {
            System.out.println("Error starting server" + e.toString());
        }
        setRunning(false);

    }

    private void updateClients()
    {
        List<DataPackage> packages = getPackages();
        for (ClientHandler handler : getClients())
        {
            handler.setPackages(packages);
        }
    }

    public static void main(String[] args)
    {
        Server server = new Server(26001);
    }

    public synchronized boolean isRunning()
    {
        return _isRunning;
    }

    public synchronized void setRunning(boolean isRunning)
    {
        _isRunning = isRunning;
    }

}
