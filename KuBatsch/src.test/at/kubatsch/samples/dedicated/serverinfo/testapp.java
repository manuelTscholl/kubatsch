/**
 * author: Martin Balter
 * created on: 05.02.2011
 * filename: testapp.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.dedicated.serverinfo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import at.kubatsch.client.controller.DedicatedServerInfoController;
import at.kubatsch.model.ServerInfo;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Martin Balter
 *
 */
public class testapp
{

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {
//        writeTestFile();
        
        readTestFileFromHTTP();
    }

    public static void writeTestFile() throws IOException
    {
        ArrayList<ServerInfo> serverInfos = new ArrayList<ServerInfo>();

        serverInfos.add(new ServerInfo("Deadly Death", "localhost", 25000));
        serverInfos.add(new ServerInfo("Blub", "localhost", 25000));
        serverInfos.add(new ServerInfo("Lets Try", "localhost", 25000));

        DomDriver dom = new DomDriver();
        XStream stream = new XStream(dom);

        stream.autodetectAnnotations(true);
        
        stream.toXML(serverInfos, new FileOutputStream("test.xml"));
    }
    
    public static void readTestFileFromHTTP() throws IOException
    {
        DedicatedServerInfoController controller = DedicatedServerInfoController.getInstance(new URL("http://localhost/servers.xml"));
        ArrayList<ServerInfo> test = controller.getDedicatedServerInfo();
        
        for (ServerInfo serverInfo : test)
        {
            System.out.println(serverInfo.getName());
        }
    }

}
