/**
 * author: Daniel Kuschny (dku2375)
 * created on: 27.01.2011
 * filename: Client.java
 * project: KuBatsch
 */
package at.kubatsch.samples.simplenetwork;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

/**
 * @author Daniel Kuschny (dku2375)
 *
 */
public class Client
{
    public static void main(String[] args)
    {
        System.out.println("Connecting to server");
        try
        {
            Socket socket = new Socket("localhost", 26000);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            while(true)
            {
                try
                {
                    System.out.println("Waiting for data");
                    Object o = in.readObject();
                    
                    if(o instanceof List<?>)
                    {
                        System.out.println("New Package");
                        List<DataPackage> packages = (List<DataPackage>)o;
                        
                        for (DataPackage dataPackage : packages)
                        {
                            System.out.printf("    %s%n", dataPackage);
                        }
                    }
                    else
                    {
                        System.out.println("Unknown Message of type: " + o.getClass().getName());
                    }
                }
                catch (ClassNotFoundException e)
                {
                    System.out.println("Unknown class received: " + e.toString());
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error connecting: " + e.toString());
        }
        
    }
}
