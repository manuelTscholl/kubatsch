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
import java.util.List;

import org.apache.log4j.Logger;

import at.kubatsch.model.ServerInfo;
import at.kubatsch.model.message.ServerInfoMessage;
import at.kubatsch.server.controller.NetworkGameClientEventArgs;
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
    private static Logger LOGGER = Logger.getLogger(PlayOnlineController.class);
    
    private static PlayOnlineController _instance;
    private static URL                  _url;
    private static String               _urlS = "http://kubatsch.googlecode.com/svn/trunk/KuBatsch/DedicatedServerList.xml";
    private List<ServerInfo>            _servers;

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
    public synchronized static PlayOnlineController getInstance()
    {
        if (_instance == null)
        {
            try
            {
                setUrl(new URL(_urlS));
            }
            catch (MalformedURLException e)
            {
                LOGGER.error(e);
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
                getPlayersOf(serverInfo);
            }
            setServers(infos);
            _serversUpdated.fireEvent(EventArgs.Empty);
        }
        catch (IOException e)
        {
            LOGGER.error(e);
        }
    }

    /**
     * Return the Player
     * @param serverInfo
     */
    private void getPlayersOf(ServerInfo serverInfo)
    {
        final ServerInfo serverInfoTemp = serverInfo;
        try
        {
            final NetworkControllerClient networkController = new NetworkControllerClient(
                    serverInfo.getServer(), serverInfo.getPort());
            networkController
                    .addMessageReceivedListener(new IEventHandler<NetworkMessageEventArgs>()
                    {
                        @Override
                        public void fired(Object sender,
                                NetworkMessageEventArgs e)
                        {
                            if (e.getMessage().getMessageId()
                                    .equals(ServerInfoMessage.MESSAGE_ID))
                            {// gets the serverStatusMessage
                                ServerInfoMessage infoMessage = (ServerInfoMessage) e
                                        .getMessage();
                                serverInfoTemp.setCurrentPlayers(infoMessage
                                        .getPlayers());
                                networkController.disconnect();
                            }
                            _serversUpdated.fireEvent(EventArgs.Empty);
                        }
                    });
            networkController.addConnectionLostListener(new IEventHandler<NetworkGameClientEventArgs>()
            {
                
                @Override
                public void fired(Object sender, NetworkGameClientEventArgs e)
                {
                    serverInfoTemp.setCurrentPlayers(-1);
                    networkController.setRunning(false);
                    _serversUpdated.fireEvent(EventArgs.Empty);
                }
            });
            // the object which bill be sent back with the serverinformation
            networkController.addToMessageStack(new ServerInfoMessage());
            networkController.startWork();// asycron work
        }
        catch (IOException e1)
        {
            serverInfo.setCurrentPlayers(-1);
            LOGGER.warn(e1);
        }

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
