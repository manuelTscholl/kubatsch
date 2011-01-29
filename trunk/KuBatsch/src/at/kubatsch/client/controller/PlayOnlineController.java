/**
 * author: Daniel Kuschny (dku2375)
 * created on: 28.01.2011
 * filename: PlayOnlineController.java
 * project: KuBatsch
 */
package at.kubatsch.client.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import at.kubatsch.model.ServerInfo;
import at.kubatsch.util.Event;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

/**
 * This controller handles loading of online servers.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class PlayOnlineController
{
    private static PlayOnlineController _instance;

    public static PlayOnlineController getInstance()
    {
        if (_instance == null)
            _instance = new PlayOnlineController();
        return _instance;
    }

    private List<ServerInfo> _servers;

    /**
     * Gets the servers.
     * @return the servers
     */
    public synchronized List<ServerInfo> getServers()
    {
        return _servers;
    }

    /**
     * Sets the servers.
     * @param servers the servers to set
     */
    private synchronized void setServers(List<ServerInfo> servers)
    {
        _servers = servers;
    }

    /**
     * Initializes a new instance of the {@link PlayOnlineController} class.
     */
    private PlayOnlineController()
    {
        _servers = new ArrayList<ServerInfo>();
    }

    public void refreshServers()
    {
        ServerInfo[] infos = new ServerInfo[] {
                new ServerInfo("KuBaTsch Forever", "194.208.17.83", 4),
                new ServerInfo("Try To Kill Us", "194.208.17.82", 2),
                new ServerInfo("Nobody Survives", "194.208.17.81", 1)};
        
        Random rnd = new Random();
        for (ServerInfo serverInfo : infos)
        {
            serverInfo.setCurrentPlayers(rnd.nextInt(5));
        }
        setServers(Arrays.asList(infos));
        _serversUpdated.fireEvent(EventArgs.Empty);
    }

    private Event<EventArgs> _serversUpdated = new Event<EventArgs>(this);

    /**
     * @param handler
     * @see at.kubatsch.util.Event#addHandler(at.kubatsch.util.IEventHandler)
     */
    public void addUpdatedListeners(IEventHandler<EventArgs> handler)
    {
        _serversUpdated.addHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#removeHandler(at.kubatsch.util.IEventHandler)
     */
    public void removeUpdatedListeners(IEventHandler<EventArgs> handler)
    {
        _serversUpdated.removeHandler(handler);
    }

}
