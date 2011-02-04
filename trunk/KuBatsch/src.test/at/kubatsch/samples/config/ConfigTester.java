/**
 * author: Martin Balter
 * created on: 23.01.2011
 * filename: ConfigTester.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.config;

import java.io.IOException;

import at.kubatsch.client.model.ClientConfig;
import at.kubatsch.util.ConfigManager;

/**
 * @author Martin Balter
 * Little program to test the {@link ConfigManager}
 */
public class ConfigTester
{

    /**
     * Programm to test the ConfigManager und the ConfigModule
     * @param args
     */
    public static void main(String[] args)
    {

        writeConfig(); // write the standard config into into the file
                       // config.xml
        readPrintConfig(); // read the config from the file config.xml
    }

    /**
     * Read the Config from the file
     */
    private static void readPrintConfig()
    {
        ConfigManager cm;

        try
        {
            cm = ConfigManager.getInstance();
            ClientConfig cc = cm.loadConfig(); // Load the config

            // output from the config
            System.out.println(cc.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Write the config into the file
     */
    private static void writeConfig()
    {
        ConfigManager cm;
        try
        {
            cm = ConfigManager.getInstance();
            ClientConfig cc = new ClientConfig();
            System.out.println(cc.toString());
            cm.saveConfig(cc);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
