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

import at.kubatsch.client.controller.StartServerException;
import at.kubatsch.model.Ball;
import at.kubatsch.model.Color;
import at.kubatsch.model.GameState;
import at.kubatsch.server.controller.ServerGameController;
import at.kubatsch.server.model.ServerConfig;
import at.kubatsch.util.ConfigManager;
import at.kubatsch.util.StartNewServerController;

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
            if (args[i].equalsIgnoreCase("-p "))
            {
                if (i + 1 < args.length)
                    port = Integer.parseInt(args[i + 1]);

            }
            else if (args[i].equalsIgnoreCase("-c "))
            {
                if (i + 1 < args.length)
                    configName = args[i + 1];
            }
        }
        try
        {
            StartNewServerController.getInstance().startServer(port, configName);
        }
        catch (StartServerException e)
        {
            e.printStackTrace();
        }
    }

}
