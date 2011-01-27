/**
 * This file is part of KuBatsch.
 *   created on: 19.01.2011
 *   filename: OnlineServer.java
 *   project: KuBatsch
 */
package at.kubatsch.model;

/**
 * This class is used to store all the required information for online servers
 * of KuBatsch.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class ServerInfo
{
    private String _name;
    private String _server;
    private int    _currentPlayers;

    /**
     * Initializes a new instance of the {@link ServerInfo} class.
     */
    public ServerInfo()
    {
        super();
    }

    /**
     * Initializes a new instance of the {@link ServerInfo} class.
     * @param name
     * @param server
     * @param currentPlayers
     */
    public ServerInfo(String name, String server, int currentPlayers)
    {
        super();
        _name = name;
        _server = server;
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
        return _server;
    }

    /**
     * Sets the server address to connect to.
     * @param server the server address to connect to.
     */
    public void setServer(String server)
    {
        _server = server;
    }

    /**
     * Gets the count of current players on the server.
     * @return the count of current players on the server.
     */
    public int getCurrentPlayers()
    {
        return _currentPlayers;
    }

    /**
     * Sets the count of current players on the server.
     * @param currentPlayers the count of current players on the server.
     */
    public void setCurrentPlayers(int currentPlayers)
    {
        _currentPlayers = currentPlayers;
    }
}
