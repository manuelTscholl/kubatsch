/**
 * author: Martin Balter
 * created on: 07.02.2011
 * filename: gziptest.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.gzip;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import at.kubatsch.client.model.ClientConfig;
import at.kubatsch.model.GameState;
import at.kubatsch.model.message.PaddleMovedMessage;

/**
 * @author Martin Balter
 *
 */
public class gziptest
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        GameState gameState = new GameState();
        PaddleMovedMessage pmm = new PaddleMovedMessage(476778273,456789.647f);
        
        String fileName = "test1";
        String fileNameNormal = "test2";
        
        writeObjectToFileGZIP(gameState, fileName);
        writeObjectToFile(gameState, fileNameNormal);
        
        PaddleMovedMessage blub = (PaddleMovedMessage) readObjectFromFileGZIP(fileName);
        
        System.out.println(blub.toString());

    }

    public static void writeObjectToFileGZIP(Object o, String fileName)
    {
        ObjectOutputStream oos = null;

        try
        {
            oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(
                    fileName)));
            oos.writeObject(o);
            oos.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                oos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public static void writeObjectToFile(Object o, String fileName)
    {
        ObjectOutputStream oos = null;

        try
        {
            oos = new ObjectOutputStream(new FileOutputStream(
                    fileName));
            oos.writeObject(o);
            oos.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                oos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public static Object readObjectFromFileGZIP(String fileName)
    {
        
        ObjectInputStream ois = null;

        try
        {
            ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(
                    fileName)));
            return ois.readObject();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
