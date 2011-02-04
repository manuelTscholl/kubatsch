/**
 * author: Martin Balter
 * created on: 04.02.2011
 * filename: ServerConfigController.java
 * project: KuBaTsch
 */
package at.kubatsch.server.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import at.kubatsch.client.controller.ClientConfigController;
import at.kubatsch.server.model.ServerConfig;
import at.kubatsch.util.ConfigManager;

/**
 * This is the {@link ServerConfigController} for the Server. This Config include all
 * game relvate setting, like the Collisionrules or the Serverport.
 * The Config can be saved or loaded from the Jar / Projekt dirctionary.
 * @author Martin Balter
 * @see ClientConfigController
 */
public class ServerConfigController
{
    private ServerConfig                  _config;
    private ConfigManager                 _manager;

    private static ServerConfigController _serverConfigController = null;
    
    /**
     * Singelton of the {@link ServerConfigController}
     * Initializes a new instance of the {@link ServerConfigController} class.
     * @param configFile the filename of the config. The config is allways at the
     * jar or project directory
     */
    private ServerConfigController(String configFile)
    {
        // Configmanager with the new instance
        _manager = ConfigManager.getInstance(configFile);
        try
        {
            // set the defaultconfig
            _config = _manager.loadConfig();
        }
        catch (Exception e)
        {
            _config = new ServerConfig();
        }
    }

    /**
     * Initializes a new instance of the {@link ServerConfigController} class without a 
     * parameter. The default name of the config is the {@link ServerConfig#CONFIG_ID}
     */
    public ServerConfigController()
    {
        this(ServerConfig.CONFIG_ID);
    }

    /**
     * Get the instance of the {@link ServerConfigController}
     * @param configFile the filename of the config. The config is allways at the
     * jar or project directory
     * @return The instance of the {@link ServerConfigController}
     */
    public static ServerConfigController getInstance(String configFile)
    {
        if (_serverConfigController == null)
        {
            _serverConfigController = new ServerConfigController(configFile);
        }

        return _serverConfigController;
    }

    /**
     * Get the instance of the {@link ServerConfigController} with the defaulf filename.
     * The default filename of the config depends on the {@link ServerConfig#CONFIG_ID}
     * @return The instance of the {@link ServerConfigController}
     */
    public static ServerConfigController getInstance()
    {
        return ServerConfigController.getInstance(ServerConfig.CONFIG_ID);
    }

    /**
     * Load the Config from the ConfigFile. Were the ConfigFile comes, how you gets the Instance of 
     * the {@link ServerConfigController}
     * @return the {@link ServerConfig}
     * @throws FileNotFoundException if the {@link ConfigManager} cant find the ConfigFile
     * @throws ClassCastException if the ConfigFile is not a {@link ServerConfig} 
     * @see ServerConfigController#getInstance()
     * @see ServerConfigController#getInstance(String)
     */
    public ServerConfig loadConfig() throws FileNotFoundException, ClassCastException
    {
        _config = _manager.loadConfig();

        return _manager.loadConfig();
    }

    /**
     * Wirte the current Config of the {@link ServerConfigController} into the File
     * @throws IOException if the {@link ConfigManager} cant write the File
     */
    public void writeConfig() throws IOException
    {
        this.writeConfig(_config);
    }

    /**
     * Write the parameter {@link ServerConfig} into the file
     * @param config which you want to write into the file. The Controller writes the config
     * into the file of the Instance
     * @throws IOException if the {@link ConfigManager} cant write the file
     */
    public void writeConfig(ServerConfig config) throws IOException
    {
        _manager.saveConfig(config);
    }

    /**
     * Gets the config.
     * @return the config
     */
    public ServerConfig getConfig()
    {
        return _config;
    }

    /**
     * Sets the config.
     * @param config the config to set
     */
    public void setConfig(ServerConfig config)
    {
        _config = config;
    }

}
