/**
 * author: Martin Balter
 * created on: 08.02.2011
 * filename: Server.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.udp;

import java.io.IOException;

/**
 * @author Martin Balter
 *
 */
public class Server
{

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {
        new QuoteServerThread().start();
    }

}
