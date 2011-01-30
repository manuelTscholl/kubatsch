/**
 * author: Martin Balter
 * created on: 23.01.2011
 * filename: ServerConfig.java
 * project: KuBaTsch
 */
package at.kubatsch.server.model;

import java.util.Set;

import at.kubatsch.model.Config;
import at.kubatsch.util.KuBaTschUtils;

/**
 * @author Martin Balter
 * Configuration of the server
 */
public class ServerConfig extends Config
{
    private static final long serialVersionUID = 5692189898306877962L;

    private int               _port;
    private Set<String>       _specialItems;

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

    @Override
    public ServerConfig getDefaultConfig()
    {
        ServerConfig sc = new ServerConfig();
        sc.setPort(45018);
        sc.getSpecialItems().add("StandardBall.class");
        
        return sc;
    }
    
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

    @Override
    public String getConfigType()
    {
        return "SERVER_CONFIG";
    }

}
