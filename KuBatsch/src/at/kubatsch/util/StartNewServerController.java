/**
 * author: Daniel Kuschny (dku2375)
 * created on: 27.01.2011
 * filename: StartNewServerController.java
 * project: KuBatsch
 */
package at.kubatsch.util;

import java.io.FileNotFoundException;
import java.io.IOException;

import at.kubatsch.client.controller.StartServerException;
import at.kubatsch.client.controller.ViewController;
import at.kubatsch.client.view.MenuView;
import at.kubatsch.model.GameState;
import at.kubatsch.server.controller.ServerGameController;
import at.kubatsch.server.model.ServerConfig;
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
 * @param port 
 * @param configName if empty than default config will be used
 * @throws StartServerException
 */
    public void startServer(int port, String configName) throws StartServerException
    {                
        if(configName.equals(""))
        {
            configName=ServerConfig.CONFIG_ID;            
        }
        //not in port range
        if (!(port > 0 && port <= 65535))
        {
            ConfigManager configManager = ConfigManager.getInstance(configName);
            ServerConfig serverConfig = null;

            try
            {
                serverConfig = configManager.loadConfig();

            }
            catch (FileNotFoundException e)
            {
                serverConfig = new ServerConfig();
            }
            catch (ClassCastException e)
            {
                e.printStackTrace();
            }

            port = serverConfig.getPort();

        }

        GameState start= new GameState();
        
        try
        {
            ServerGameController game = ServerGameController.getInstance(port);
            game.setCurrentGameState(start);
            game.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("Server started!");
        
        
       
    }

}
