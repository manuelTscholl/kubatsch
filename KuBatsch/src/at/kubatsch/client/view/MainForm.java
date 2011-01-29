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

import at.kubatsch.client.controller.AudioController;
import at.kubatsch.client.controller.ViewController;

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
        ViewController.createGlobalInstance(getContentPane());

        MenuView menu = new MenuView();
        ViewController.getInstance().registerView(MenuView.PANEL_ID, menu);
        
        StartNewServerView startNewServer = new StartNewServerView();
        ViewController.getInstance().registerView(StartNewServerView.PANEL_ID, startNewServer);
        
        JoinServerView joinServer = new JoinServerView();
        ViewController.getInstance().registerView(JoinServerView.PANEL_ID, joinServer);
     
        PlayOnlineView playOnline = new PlayOnlineView();
        ViewController.getInstance().registerView(PlayOnlineView.PANEL_ID, playOnline);
       
        SettingsView settings = new SettingsView();
        ViewController.getInstance().registerView(SettingsView.PANEL_ID, settings);
 
        MouseSettingsView mouseSettings = new MouseSettingsView();
        ViewController.getInstance().registerView(MouseSettingsView.PANEL_ID, mouseSettings);
        
        KeySettingsView keySettings = new KeySettingsView();
        ViewController.getInstance().registerView(KeySettingsView.PANEL_ID, keySettings);

        GameView game = new GameView();
        ViewController.getInstance().registerView(GameView.PANEL_ID, game);

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
}
