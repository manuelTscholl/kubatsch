/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: PlayOnlinePanel.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JList;
import javax.swing.border.EmptyBorder;

import at.kubatsch.model.ServerInfo;
import at.kubatsch.uicontrols.BloodScrollPane;
import at.kubatsch.uicontrols.KuBaTschPane;
import at.kubatsch.uicontrols.KuBatschTheme;
import at.kubatsch.uicontrols.MenuButton;
import at.kubatsch.uicontrols.ServerInfoListItemRenderer;

/**
 * This view lists the online servers.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class PlayOnlineView extends NotGameView
{
    /**
     * A unique serialization ID
     */
    private static final long  serialVersionUID = 9032562409193287921L;

    /**
     * The view-id used by this panel
     */
    public static final String PANEL_ID         = "play-online";

    /**
     * Initializes a new instance of the {@link PlayOnlineView} class.
     */
    public PlayOnlineView(Container container)
    {
        setViewText("Play Online");

        JList serverList = new JList(new Object[] {
                new ServerInfo("KuBaTsch Forever", "194.208.17.83", 3),
                new ServerInfo("Try To Kill Us", "194.208.17.82", 2),
                new ServerInfo("Nobody Survives", "194.208.17.81", 1)});
        serverList.setOpaque(false);
        serverList.setCellRenderer(new ServerInfoListItemRenderer());
        serverList.setBorder(new EmptyBorder(10, 20, 10, 20));
        serverList.setSelectedIndex(0);

        BloodScrollPane listContainer = new BloodScrollPane(serverList);
        add(listContainer);
        
        // Buttons
        KuBaTschPane buttonPane = new KuBaTschPane();
        buttonPane.setLayout(new FlowLayout());
        
        MenuButton startButton = new MenuButton("Join", true);
        startButton.setGlowEnabled(true);
        startButton.setTheme(KuBatschTheme.BUTTON_THEMES[3]);
        // TODO Start Game Listener
        startButton.addMouseListener(new ChangeViewClickListener(container,
                MenuView.PANEL_ID));
        buttonPane.add(startButton);
        
        MenuButton backButton = new MenuButton("Back", false);
        backButton.setTheme(KuBatschTheme.BUTTON_THEMES[4]);
        backButton.addMouseListener(new ChangeViewClickListener(container,
                MenuView.PANEL_ID));
        buttonPane.add(backButton);
        
        add(buttonPane);

        MenuButton refreshButton = new MenuButton("Refesh", false);
        refreshButton.setTheme(KuBatschTheme.BUTTON_THEMES[0]);
        add(refreshButton);
    }
}
