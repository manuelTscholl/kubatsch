/**
 * author: Daniel Kuschny (dku2375)
 * created on: 28.01.2011
 * filename: KuBaTschUtils.java
 * project: KuBatsch
 */
package at.kubatsch.util;

import java.io.File;
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
	
	/**
     * Get the Path of the directory where the Class lies
     * @return the Path of the directory where the class lies
     */
    public static final String getJarLocation(Class<?> test)
    {
        return new File(test.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
    }
    
    /**
     * Get the Path of the directory where this Class lies
     * @return the Path of the directory where the actual JarFinder Class 
     */
    public static final String getJarLocation()
    {
        return getJarLocation(KuBaTschUtils.class);
    }
	
	/**
     * Checks if the value is in the Range. When the value is not in the Range it will return the under or the upper range based on the value
     * @param value which you want to check
     * @param underRange under range 
     * @param UpperRange upper range
     * @param <T> The type of all inputs and the return value must be the same
     * @return the value when its in the range, if net the it will return the egde where it passed
     */
    public static final <T extends Number> T getValueBetweenRange(T value, T underRange,
            T UpperRange)
    {   
        if (value.doubleValue() < underRange.doubleValue()) // if value under the range
        {
            return underRange;
        }
        else if (value.doubleValue() > UpperRange.doubleValue()) // if value is over the range
        {
            return UpperRange;
        }

        return value; // nothing of both
    }

    /**
     * checks if the value is in the Range.
     * @param value value which you want to check
     * @param underRange under range
     * @param UpperRange upper range
     * @param <T> The type of all inputs and the return value must be the same
     * @return if the value is in the range of under range and upper range
     */
    public static final <T extends Number> boolean isValueBetweenRange(T value, T underRange,
            T UpperRange)
    {
        if (value.doubleValue() < underRange.doubleValue()
                || value.doubleValue() > UpperRange.doubleValue())
            return false;

        return true;
    }
}
