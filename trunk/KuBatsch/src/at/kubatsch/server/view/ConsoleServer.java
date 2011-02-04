/**
 * author: Manuel Tscholl(mts3970)
 * created on: 03.02.2011
 * filename: ConsoleServer.java
 * project: KuBaTsch
 */
package at.kubatsch.server.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.kubatsch.model.Ball;
import at.kubatsch.model.Color;
import at.kubatsch.model.GameState;
import at.kubatsch.server.controller.GameController;
import at.kubatsch.server.model.ServerConfig;
import at.kubatsch.util.ConfigManager;

/**
 * @author Manuel Tscholl (mts3970)
 * 
 */
public class ConsoleServer
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        int port = Integer.MIN_VALUE;
        String configName = "";
        for (int i = 0; i < args.length; i++)
        {
            if (args[i] == "-p")
            {
                if (i + 1 < args.length)
                    port = Integer.parseInt(args[i + 1]);

            }
            else if (args[i] == "-c")
            {
                if (i + 1 < args.length)
                    configName = args[i + 1];
            }

        }

        
        port=25000;
        
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

        List<Ball> balls = new ArrayList<Ball>();
        balls.add(new Ball(10, Color.BLUE));
        balls.add(new Ball(15, Color.RED));
        
        GameState start= new GameState();
        start.setBalls(balls);
        
        try
        {
            GameController game = GameController.getInstance(port);
            game.setCurrentGameState(start);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("Server started!");
    }

}
