/**
 * author: Martin Balter
 * created on: 18.01.2011
 * filename: ConfigManager.java
 * project: KuBaTsch
 */
package at.kubatsch.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import at.kubatsch.client.controller.ClientConfigController;
import at.kubatsch.client.model.ClientConfig;
import at.kubatsch.model.Config;
import at.kubatsch.server.model.ServerConfig;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Load the Configuration from the standard File "config.xml".
 * The Configuation has the Structure as a XML so that the User can edit it manuelly.
 * When the Standard Configuation cant be load the default configuration would be load.
 * There are differnt Types like {@link ClientConfig} or {@link ServerConfig}. 
 * Both implements {@link Config}
 * @author Martin Balter
 */
public class ConfigManager
{
    private static ConfigManager _configManager = null;
    private XStream              _stream        = null;
    private Config               _config        = null;
    // Path of the config, load from out of the Jar
    private String               _configFile    = null;

    /**
     * Singelton Pattern
     * There can only be one ConfigManager in the Program
     * Initializes a new instance of the {@link ConfigManager} class.
     */
    private ConfigManager(String configName)
    {
        // Load default information
        DomDriver dom = new DomDriver("UTF-8"); // Dom with the format of UTF-8
        _stream = new XStream(dom);
        // XStream takes the Annotationinformation of the class to generate the
        // tag name
        _stream.autodetectAnnotations(true);

        this.initialize(configName);
    }

    /**
     * Get the instance of the {@link ConfigManager}. There can only be one 
     * instance of the {@link ConfigManager} in the program.
     * @return the instance of the {@link ConfigManager} of the Program
     */
    public static ConfigManager getInstance(String configName)
    {
        // Initial the ConfigManager if he isn't
        if (_configManager == null)
        {
            _configManager = new ConfigManager(configName);
        }

        return _configManager;
    }

    /**
     * Get the Instance of the {@link ConfigManager} with the default type.
     * The Type depends on the Config
     * @return Instance of the {@link ConfigManager}
     * @see ClientConfigController
     */
    public static ConfigManager getInstance()
    {

        return ConfigManager.getInstance(_configManager._config.getConfigType());
    }

    /**
     * initialize the Configmananger with the ConfigFileName. The Config comes into the directory there the JarFile is.
     * @param configName Filename of the Config (no ".xml" needed and no Path needed)
     */
    private void initialize(String configName)
    {
        _configFile = new StringBuilder().append(KuBaTschUtils.getJarLocation())
                .append("/").append(configName).append(".xml").toString();
    }

    /**
     * Saves the config in a the standard config File.
     * The default file should be in the directory where the project or the jar file is
     * ({@link ConfigManager.CONFIG_PATH})
     * @throws IOException when the {@link FileWriter} has problem to write the file into the file system
     */
    public void saveConfig(Config config) throws IOException
    {
        _stream.toXML(config, new FileWriter(_configFile));
    }

    /**
     * Save the actual instance of the config in the standard config File.
     * @throws IOException
     */
    public void saveConfig() throws IOException
    {
        saveConfig(_config);
    }

    /**
     * Load Config File from the default  Configuration Path ({@link ConfigManager.CONFIG_PATH})
     * @return The Configuration form the configuration File with the exactly right Class
     * @throws FileNotFoundException when the file is not in the right plays near the project or of the jar file
     * @throws ClassCastException when you want to save the loaded configuration information into the wrong ConfigObject
     */
    @SuppressWarnings("unchecked")
    public <T> T loadConfig() throws FileNotFoundException, ClassCastException
    {
        if (this._config == null)
            return (T) _stream.fromXML(new FileReader(_configFile));
        return (T) _config;
    }
}
