/**
 * author: Martin Balter
 * created on: 23.01.2011
 * filename: ServerConfig.java
 * project: KuBaTsch
 */
package at.kubatsch.server.model;

import java.util.HashSet;
import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import at.kubatsch.model.Config;
import at.kubatsch.util.KuBaTschUtils;

/**
 * Configuration of the server
 * @author Martin Balter
 */
public class ServerConfig extends Config
{
    private static final long serialVersionUID = 5692189898306877962L;
    public static final int STANDARD_PORT = 25000;
    public static final String CONFIG_ID = "serverconfig";
    

    @XStreamAlias(value = "port")
    private int               _port;
    @XStreamAlias(value = "spezialItems")
    private Set<String>       _specialItems;
    
    

    /**
     * Initializes a new instance of the {@link ServerConfig} class.
     */
    public ServerConfig()
    {
        _specialItems = new HashSet<String>();
        setPort(STANDARD_PORT);
        getSpecialItems().add("StandardBall.class");
    }
    

    /**
     * Gets the port.
     * @return the port
     */
    public int getPort()
    {
        return _port;
    }

    /**
     * Sets the port.
     * @param port the port to set
     */
    public void setPort(int port)
    {
        _port = KuBaTschUtils.getValueBetweenRange(port, 0, 65535);
    }

    /**
     * Gets the specialItems.
     * @return the specialItems
     */
    public Set<String> getSpecialItems()
    {
        return _specialItems;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("ServerConfig");
        sb.append(System.getProperty("line.separator"));
        
        sb.append("Port: ");
        sb.append(this.getPort());
        sb.append(System.getProperty("line.separator"));
        
        sb.append("Specialitems");
        sb.append(System.getProperty("line.separator"));
        
        for (String specialItem : this.getSpecialItems())
        {
            sb.append("  ");
            sb.append(specialItem);
        }
        
        return sb.toString();
    }

    /**
     * @see at.kubatsch.model.Config#getConfigType()
     */
    @Override
    public String getConfigType()
    {
        return CONFIG_ID;
    }

}
