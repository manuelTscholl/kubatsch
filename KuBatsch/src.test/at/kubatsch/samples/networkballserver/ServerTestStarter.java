/**
 * author: Manuel Tscholl(mts3970)
 * created on: 19.01.2011
 * filename: TestStarter.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.networkballserver;

import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * @author Manuel Tscholl (mts3970)
 *
 */
public class ServerTestStarter
{

    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e)
        {
        }
        
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                ServerRunner runner;
                try
                {
                    ServerGamePanel server = new ServerGamePanel(25000);
                    runner = new ServerRunner(server);
                    runner.setVisible(true);
                    server.startServer();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                
            }
        });        
    }


}
