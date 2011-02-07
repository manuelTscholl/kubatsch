/**
 * author: Daniel Kuschny (dku2375)
 * created on: 28.01.2011
 * filename: JoinServerController.java
 * project: KuBatsch
 */
package at.kubatsch.client.controller;

import java.io.IOException;

import at.kubatsch.client.view.GameView;
import at.kubatsch.model.ServerInfo;
import at.kubatsch.util.KuBaTschUtils;

/**
 * This controllers handles connecting to game server.
 * @author Daniel Kuschny (dku2375)
 */
public class JoinServerController
{
    private static JoinServerController _instance;

    /**
     * Returns the instance of the {@link JoinServerController}
     * @return instance of the {@link JoinServerController}
     */
    public static JoinServerController getInstance()
    {
        if (_instance == null)
            _instance = new JoinServerController();
        return _instance;
    }

    /**
     * Initializes a new instance of the {@link JoinServerController} class.
     */
    private JoinServerController()
    {}

    /**
     * Join to a server with a string like "127.0.0.1:25000", "IP:Port"
     * @param server ServerIP:port string
     * @throws JoinServerException if the controller can connect to the server or the string 
     * is wrong
     */
    public void joinServer(String server) throws JoinServerException
    {
        // resolve ip and host
        int portIndex = server.lastIndexOf(':');
        if (portIndex < 0)
        {
            throw new JoinServerException(JoinServerException.INVALID_HOST_FORMAT);
        }

        String host = null;
        int port = 0;
        try
        {
            host = server.substring(0, portIndex);
            port = Integer.parseInt(server.substring(portIndex + 1));
        }
        catch (Exception e)
        {
            throw new JoinServerException(JoinServerException.INVALID_HOST_FORMAT, e);
        }

        joinServer(host, port);
    }

    /**
     * Join to a server with the {@link ServerInfo}
     * @param info {@link ServerInfo}
     * @throws JoinServerException if the ServerInfo is null or the Server is full.
     */
    public void joinServer(ServerInfo info) throws JoinServerException
    {
        if (info == null)
        {
            throw new JoinServerException(JoinServerException.SERVER_INFO_NULL);
        }

        if (info.getCurrentPlayers() == KuBaTschUtils.MAX_PLAYERS)
        {
            throw new JoinServerException(JoinServerException.SERVER_FULL);
        }
        joinServer(info.getServer(), info.getPort());
    }

    /**
     * Join the Server with a IP and a Port
     * @param host ServerIP
     * @param port ServerPort
     * @throws JoinServerException if the Controller cant connect to ther Server
     */
    public void joinServer(String host, int port) throws JoinServerException
    {
        try
        {
            GameView gameView = ViewController.getInstance().getView(GameView.PANEL_ID);
            gameView.connect(host, port);
            ViewController.getInstance().switchToView(GameView.PANEL_ID);
        }
        catch (IOException e)
        {
            throw new JoinServerException(JoinServerException.UNKNOWN, e);
        }
    }
}
