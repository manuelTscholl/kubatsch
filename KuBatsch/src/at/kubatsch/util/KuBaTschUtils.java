/**
 * author: Daniel Kuschny (dku2375)
 * created on: 28.01.2011
 * filename: KuBaTschUtils.java
 * project: KuBatsch
 */
package at.kubatsch.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Some utlily functions for KuBaTsch.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class KuBaTschUtils
{
    /**
     * The default server port.  
     */
    public static final int DEFAULT_SERVER_PORT = 25000;
    
    /**
     * Max count of players on a server.
     */
    public static final int MAX_PLAYERS = 4;


    public static final String getLocalIp()
    {
        try
        {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e)
        {
            return "localhost";
        }
    }
}
