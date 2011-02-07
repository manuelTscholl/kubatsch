/**
 * author: Daniel Kuschny (dku2375)
 * created on: 28.01.2011
 * filename: PlayOnlineController.java
 * project: KuBatsch
 */
package at.kubatsch.client.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import at.kubatsch.model.ServerInfo;
import at.kubatsch.model.message.ServerInfoMessage;
import at.kubatsch.server.controller.NetworkMessageEventArgs;
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
    private static URL                  _url;
    private static String _urlS = "http://kubatsch.googlecode.com/svn/trunk/KuBatsch/DedicatedServerList.xml";
    NetworkControllerClient _networkController;
    private  List<ServerInfo> _servers;

    /**
     * Returns a new instance of this class or the first initialized one
     * @param url
     * @return
     */
    public static PlayOnlineController getInstance(URL url)
    {        
        setUrl(url);
        return getInstance();
    }

    /**
     * Returns a new instance of this class or the first initialized one
     * @param url
     * @return
     */
    public static PlayOnlineController getInstance()
    {
        if (_instance == null)
        {
            try
            {
                setUrl(new URL(_urlS));
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            _instance = new PlayOnlineController();
        }
        return _instance;
    }

    /**
     * Gets the url.
     * @return the url
     */
    public static URL getUrl()
    {
        return _url;
    }

    /**
     * Sets the url.
     * @param url the url to set
     */
    public static void setUrl(URL url)
    {
        _url = url;
    }



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

    /**
     * Refreshes the serverlist with the Url property
     */
    public void refreshServers()
    {
        // the config reader
        DedicatedServerInfoController getXmlDefinition = DedicatedServerInfoController
                .getInstance(_url);

        List<ServerInfo> infos;
        try
        {
            infos = getXmlDefinition.getDedicatedServerInfo();

            for (ServerInfo serverInfo : infos)
            {// gets active Players of the Server
                if(!getServers().contains(serverInfo))
                {
                getServers().add(serverInfo);
                }
                
                getPlayersOf(serverInfo);
                
                serverInfo.setCurrentPlayers(serverInfo.getCurrentPlayers());
            }
            setServers(infos);
            _serversUpdated.fireEvent(EventArgs.Empty);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void getPlayersOf(ServerInfo serverInfo)
    {
        final ServerInfo serverInfoTemp = serverInfo;
        try
        {
            _networkController = new NetworkControllerClient(serverInfo.getServer(),serverInfo.getPort());
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }

        _networkController.addMessageReceivedListener(new IEventHandler<NetworkMessageEventArgs>()
        {            
            @Override
            public void fired(Object sender, NetworkMessageEventArgs e)
            {
                if(e.getMessage().getMessageId().equals(ServerInfoMessage.MESSAGE_ID))
                {//gets the serverStatusMessage
                    ServerInfoMessage infoMessage= (ServerInfoMessage)e.getMessage();
                    serverInfoTemp.setCurrentPlayers(infoMessage.getPlayers());
                    _networkController.setRunning(false);
                }                
            }
        });
        //the object which bill be sent back with the serverinformation
        _networkController.addToMessageStack(new ServerInfoMessage());
        _networkController.startWork();//asycron work
   
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
