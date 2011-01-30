/**
 * author: Martin Balter
 * created on: 18.01.2011
 * filename: IConfig.java
 * project: KuBaTsch
 */
package at.kubatsch.model;

import java.io.Serializable;

/**
 * @author Martin Balter
 * Standard Config for the {@link ConfigManager}. This files are 
 * {@link Serializable} in an Annotationstyle where i use XStream.
 * 
 */
public abstract class Config implements Serializable
{
    private static final long serialVersionUID = 4226846703353197589L;
    
    /**
     * This should gave the the default config back of his own Type.
     * It should be used to get a default config if the Configmanager can't 
     * load the right configuration
     * @return the default config of its own type
     */
    public abstract Config getDefaultConfig();
    
    /**
     * @return the ConfigType as a String
     */
    public abstract String getConfigType();
}
