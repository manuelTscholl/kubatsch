/**
 * author: Manuel Tscholl(mts3970)
 * created on: 03.02.2011
 * filename: ConsoleServer.java
 * project: KuBaTsch
 */
package at.kubatsch.server.view;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import at.kubatsch.client.controller.StartServerException;
import at.kubatsch.util.StartNewServerController;

/**
 * The {@link ConsoleServer} start the KuBaTsch Server
 * he has specific parameters: <br>
 * <ul>
 *      <li>-p port</li>
 *      <li>-c config filename</li>
 * </ul>
 * 
 * @author Manuel Tscholl (mts3970)
 */
public class ConsoleServer
{
    private static Logger LOGGER = Logger.getLogger(ConsoleServer.class);
    
    /**
     * Starts the Config server
     * <ul>
     *      <li>-p port</li>
     *      <li>-c config filename</li>
     * </ul>
     * @param args
     */
    public static void main(String[] args)
    {
        // initialize log4j
        DOMConfigurator.configure(ConsoleServer.class.getResource("server_log4j.xml"));

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
            LOGGER.fatal(e);
        }
    }

}
