/**
 * author: Martin Balter
 * created on: 05.02.2011
 * filename: DedicatedServerInfoController.java
 * project: KuBaTsch
 */
package at.kubatsch.client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import at.kubatsch.model.ServerInfo;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Controller to the the Information for the dedicated Server. This loads a XML 
 * File from the URL which you want and parse it into a ServerInfo ArrayList.
 * @author Martin Balter
 */
public class DedicatedServerInfoController
{
    private static DedicatedServerInfoController _controller = null;
    private URL                                  _url;
    private XStream                              _stream;

    /**
     * Initializes a new instance of the {@link DedicatedServerInfoController} class.
     * @param url {@link URL} there the XML file is
     */
    private DedicatedServerInfoController(URL url)
    {
        _url = url;
        DomDriver dom = new DomDriver("UTF-8");
        _stream = new XStream(dom);
        _stream.autodetectAnnotations(true);
    }

    /**
     * Get the instance of the {@link DedicatedServerInfoController}
     * @param url which you want to connect (where the XML file is)
     * @return instance of the {@link DedicatedServerInfoController}
     */
    public static DedicatedServerInfoController getInstance(URL url)
    {
        if (_controller == null)
        {
            _controller = new DedicatedServerInfoController(url);
        }
        return _controller;
    }

    /**
     * Loads the XML List from the Internet 
     * @return  a {@link ServerInfo} {@link ArrayList}
     * @throws IOException if the connection to the {@link URL} is not possible
     */
    @SuppressWarnings("unchecked")
    public ArrayList<ServerInfo> getDedicatedServerInfo() throws IOException
    {
        URLConnection connection = _url.openConnection();
        connection.setConnectTimeout(3000); // 3 seconds timeout
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        ArrayList<ServerInfo> serverInfos = null;

        if (reader != null)
        {
            StringBuilder sb = new StringBuilder();
            serverInfos = new ArrayList<ServerInfo>();
            String line;

            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
            serverInfos = (ArrayList<ServerInfo>) _stream.fromXML(sb.toString());
        }

        return serverInfos;
    }
}