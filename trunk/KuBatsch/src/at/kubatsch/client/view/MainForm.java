/**
 * This file is part of KuBatsch.
 *   created on: 15.01.2011
 *   filename: MainForm.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import at.kubatsch.client.audio.ModPlayer;

/**
 * This is the applications main form providing all views.
 * @author Daniel Kuschny (dku2375)
 */
public class MainForm extends JFrame
{
    /**
     * A unique serialization id.
     */
    private static final long serialVersionUID = 5927407558737726090L;
    
    private ModPlayer _player;
    
    /**
     * Initializes a new instance of the {@link MainForm} class.
     */
    public MainForm()
    {        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("KuBaTsch");
        
        CardLayout layout = new CardLayout();
        getContentPane().setLayout(layout);

        MenuView menu = new MenuView(getContentPane());
        add(menu, MenuView.PANEL_ID);
        
        StartNewServerView startNewServer = new StartNewServerView(getContentPane());
        add(startNewServer, StartNewServerView.PANEL_ID);
        
        JoinServerView joinServer = new JoinServerView(getContentPane());
        add(joinServer, JoinServerView.PANEL_ID);
     
        PlayOnlineView playOnline = new PlayOnlineView(getContentPane());
        add(playOnline, PlayOnlineView.PANEL_ID);
       
        SettingsView settings = new SettingsView(getContentPane());
        add(settings, SettingsView.PANEL_ID);
 
        MouseSettingsView mouseSettings = new MouseSettingsView(getContentPane());
        add(mouseSettings, MouseSettingsView.PANEL_ID);
        
        KeySettingsView keySettings = new KeySettingsView(getContentPane());
        add(keySettings, KeySettingsView.PANEL_ID);
        
        _player = new ModPlayer();
        
        pack();
        centerFrame();
    }

    /**
     * Centers the frame within the current screen.
     */
    private void centerFrame()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
    }

    /**
     * Starts the player.
     */
    public void startPlayer()
    {
        _player.updateContainer(true);
    }
    
}
