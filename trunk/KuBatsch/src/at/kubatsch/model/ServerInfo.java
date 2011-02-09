/**
 * This file is part of KuBatsch.
 * created on: 19.01.2011
 * filename: OnlineServer.java
 * project: KuBatsch
 */
package at.kubatsch.model;

import java.io.Serializable;
import java.net.Socket;

import at.kubatsch.client.controller.NetworkControllerClient;
import at.kubatsch.model.message.ServerInfoMessage;
import at.kubatsch.server.controller.NetworkMessageEventArgs;
import at.kubatsch.util.IEventHandler;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class is used to store all the required information for online servers
 * of KuBatsch.
 * @author Daniel Kuschny (dku2375)
 */
public class ServerInfo implements Serializable
{
    private static final long serialVersionUID = 896616323528049864L;

    @XStreamAlias(value = "serverName")
    private String            _name;
    @XStreamAlias(value = "port")
    private int               _port;
    @XStreamAlias(value = "serverAddress")
    private String            _host;
    @XStreamAlias(value = "currentPlayers")
    private int               _currentPlayers;


    /**
     * Initializes a new instance of the {@link ServerInfo} class.
     * @param name of the server
     * @param server ipaddress of the server
     * @param port of the server
     */
    public ServerInfo(String name, String server, int port)
    {
        this(name, server, port, 0);
    }

    /**
     * Initializes a new instance of the  {@link ServerInfo} class.
     * @param name of the server
     * @param server ipaddress of the server
     * @param port of the server
     * @param currentPlayers current players one the servers
     */
    public ServerInfo(String name, String host, int port, int currentPlayers)
    {
        super();
        _name = name;
        _host = host;
        _port = port;
        _currentPlayers = currentPlayers;
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName()
    {
        return _name;
    }

    /**
     * Sets the name.
     * @param name the name to set
     */
    public void setName(String name)
    {
        _name = name;
    }

    /**
     * Gets the server address to connect to.
     * @return the server address to connect to
     */
    public String getServer()
    {
        return _host;
    }

    /**
     * Sets the server address to connect to.
     * @param server the server address to connect to.
     */
    public void setServer(String server)
    {
        _host = server;
    }

    /**
     * Gets the port to connect to.
     * @return the port to connect to
     */
    public int getPort()
    {

        return _port;
    }

    /**
     * Sets the port to connect to.
     * @param port the port to set
     */
    public void setPort(int port)
    {
        _port = port;
    }

    /**
     * Gets the currentPlayers.
     * @return the currentPlayers
     */
    public int getCurrentPlayers()
    {
        return _currentPlayers;
    }
    
    /**
     * Sets the currentPlayers.
     * @param currentPlayers the currentPlayers to set
     */
    public synchronized void setCurrentPlayers(int currentPlayers)
    {
        _currentPlayers = currentPlayers;
    }

    /**
     * Gets the host.
     * @return the host
     */
    public String getHost()
    {
        return _host;
    }

    /**
     * Sets the host.
     * @param host the host to set
     */
    public void setHost(String host)
    {
        _host = host;
    }


}
