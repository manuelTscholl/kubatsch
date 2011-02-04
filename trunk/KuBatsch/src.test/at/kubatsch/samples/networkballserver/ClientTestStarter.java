package at.kubatsch.samples.networkballserver;

/**
 * author: Manuel Tscholl(mts3970)
 * created on: 19.01.2011
 * filename: TestStarter.java
 * project: KuBaTsch
 */

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * @author Manuel Tscholl (mts3970)
 * 
 */
public class ClientTestStarter
{

    public static void main(String[] args)
    {
        try
        {
            UIManager
                    .setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e)
        {
        }
        final String server = args[0];
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                ClientRunner runner;
                
                runner = new ClientRunner(new ClientGamePanel(server,
                        25000));
                runner.setVisible(true);

            }
        });
    }

}
