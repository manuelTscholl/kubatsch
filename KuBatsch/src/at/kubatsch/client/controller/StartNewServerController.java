/**
 * author: Daniel Kuschny (dku2375)
 * created on: 27.01.2011
 * filename: StartNewServerController.java
 * project: KuBatsch
 */
package at.kubatsch.client.controller;

import at.kubatsch.client.view.MenuView;
import at.kubatsch.server.view.ConsoleServer;

/**
 * Controller to start a new game server on the local maschine.
 * It can be used by clients and the server.
 * @author Daniel Kuschny (dku2375)
 */
public class StartNewServerController
{
    private static StartNewServerController _instance;

    /**
     * Get a instance of the {@link StartNewServerController}
     * @return instance of the {@link StartNewServerController}
     */
    public static StartNewServerController getInstance()
    {
        if (_instance == null)
            _instance = new StartNewServerController();
        return _instance;
    }

    /**
     * Initializes a new instance of the {@link StartNewServerController} class.
     */
    private StartNewServerController()
    {
    }

    /**
     * Starts a new KuBaTsch server with a specified port
     * @param port of the server
     */
    public void startServer(int port) throws StartServerException
    {
        ConsoleServer.main(new String[]{"-p ",Integer.toString(port)});
        ViewController.getInstance().switchToView(MenuView.PANEL_ID);
    }

}
