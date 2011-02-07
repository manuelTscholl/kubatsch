/**
 * author: Martin Balter
 * created on: 03.02.2011
 * filename: ClientConfigController.java
 * project: KuBaTsch
 */
package at.kubatsch.client.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import at.kubatsch.client.model.ClientConfig;
import at.kubatsch.util.ConfigManager;

/**
 * This is the {@link ClientConfigController} for the Client. This Config include all
 * user specifice configuration like the Nickname, the Mouse or Keyboard sensivity.
 * The Config can be saved or loaded from the Jar / Projekt dirctionary.
 * @author Martin Balter
 */
public class ClientConfigController
{
    private ClientConfig                  _config;
    private ConfigManager                 _manager;

    private static ClientConfigController _clientConfigController = null;

    /**
     * Singelton of the {@link ClientConfigController}
     * Initializes a new instance of the @see ClientConfigController class.
     * @param configFile the filename of the config. The config is allways at the
     * jar or project directory
     */
    private ClientConfigController(String configFile)
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
            _config = new ClientConfig();
        }
    }

    /**
     * Initializes a new instance of the @see ClientConfigController class without a 
     * parameter. The default name of the config is the {@link ClientConfig#CONFIG_ID}
     */
    public ClientConfigController()
    {
        this(ClientConfig.CONFIG_ID);
    }

    /**
     * Get the instance of the {@link ClientConfigController}
     * @param configFile the filename of the config. The config is allways at the
     * jar or project directory
     * @return The instance of the {@link ClientConfigController}
     */
    public static ClientConfigController getInstance(String configFile)
    {
        if (_clientConfigController == null)
        {
            _clientConfigController = new ClientConfigController(configFile);
        }

        return _clientConfigController;
    }

    /**
     * Get the instance of the {@link ClientConfigController} with the defaulf filename.
     * The default filename of the config depends on the {@link ClientConfig#CONFIG_ID}
     * @return The instance of the {@link ClientConfigController}
     */
    public static ClientConfigController getInstance()
    {
        return ClientConfigController.getInstance(ClientConfig.CONFIG_ID);
    }

    /**
     * Load the Config from the ConfigFile. Were the ConfigFile comes, how you gets the Instance of 
     * the {@link ClientConfigController}
     * @return the {@link ClientConfig}
     * @throws FileNotFoundException if the {@link ConfigManager} cant find the ConfigFile
     * @throws ClassCastException if the ConfigFile is not a {@link ClientConfig} 
     * @see ClientConfigController#getInstance()
     * @see ClientConfigController#getInstance(String)
     */
    public ClientConfig loadConfig() throws FileNotFoundException, ClassCastException
    {
        _config = _manager.loadConfig();

        return _manager.loadConfig();
    }

    /**
     * Wirte the Config into the File
     * @throws IOException if the {@link ConfigManager} cant write the File
     */
    public void writeConfig() throws IOException
    {
        this.writeConfig(_config);
    }

    /**
     * Write the parameter {@link ClientConfig} into the file
     * @param config which you want to write into the file. The Controller writes the config
     * into the file of the Instance
     * @throws IOException if the {@link ConfigManager} cant write the file
     */
    public void writeConfig(ClientConfig config) throws IOException
    {
        _manager.saveConfig(config);
    }

    /**
     * Gets the config.
     * @return the config
     */
    public ClientConfig getConfig()
    {
        return _config;
    }

    /**
     * Sets the config.
     * @param config the config to set
     */
    public void setConfig(ClientConfig config)
    {
        _config = config;
    }
}
