/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: KuBaTschClient.java
 *   project: KuBatsch
 */
package at.kubatsch.client;

import org.apache.log4j.xml.DOMConfigurator;

import de.quippy.javamod.system.Helpers;

import at.kubatsch.client.controller.AudioController;
import at.kubatsch.client.controller.ClientConfigController;
import at.kubatsch.client.view.MainForm;
import at.kubatsch.util.ConfigManager;

/**
 * This is the main executable class for starting a new KuBaTsch client.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class KuBaTschClient
{
    /**
     * The client applications main entry point.
     * @param args The command line arguments, currently not used.
     */
    public static void main(String args[])
    {
        //initialize Client Config Controller
        ClientConfigController.getInstance();
        // initialize log4j
        DOMConfigurator.configureAndWatch("kubatsch-log.xml", 60000L);
        // create UI
        MainForm form = new MainForm();
        // tell javaMod to prepare for usage in a UI
        Helpers.setCoding(true);
        // display UI
        form.setVisible(true);
        // start audio player
        AudioController.getInstance().setAudioEnabled(true);
        AudioController.getInstance().start();
        
    }
}
