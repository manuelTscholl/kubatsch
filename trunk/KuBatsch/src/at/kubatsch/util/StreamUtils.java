/**
 * This file is part of KuBatsch.
 *   created on: 15.01.2011
 *   filename: StreamHelpers.java
 *   project: KuBatsch
 */
package at.kubatsch.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * A utility class for streams. 
 * @author Daniel Kuschny (dku2375)
 *
 */
public class StreamUtils
{
    /**
     * Closes any {@link Closeable} without throwing an exception and returning a boolean flag instead.
     * @param closeable The closeable to close.
     * @return true if the closeable was closed successfully, otherwise false.
     */
    public static boolean close(Closeable closeable)
    {
        try
        {
            closeable.close();
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }


    /**
     * Loads a byte array from a {@link InputStream}. 
     * @param data The stream to read from.
     * @return The byte array read from the stream.
     */
    public static byte[] getBytes(InputStream data)
    {
        if(data == null) return new byte[0];
        
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        
        byte[] buffer = new byte[2048];
        int count;
        try
        {
            while((count = data.read(buffer)) > 0)
            {
                bytes.write(buffer, 0, count);
            }
        }
        catch(IOException e)
        {
            
        }
        
        close(data);
        

        return bytes.toByteArray();
    }
}
