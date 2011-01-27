/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: JoinServerPanel.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.Container;
import java.awt.FlowLayout;

import at.kubatsch.uicontrols.KuBaTschPane;
import at.kubatsch.uicontrols.KuBatschTheme;
import at.kubatsch.uicontrols.MenuButton;
import at.kubatsch.uicontrols.Separator;
import at.kubatsch.uicontrols.SmallCapsLabel;
import at.kubatsch.uicontrols.KuBatschTheme.TextBoxSize;
import at.kubatsch.uicontrols.layout.CustomGridLayout;
import at.kubatsch.uicontrols.layout.CustomGridLayout.CustomGridPosition;

/**
 * This view displays UI elements for joining a server
 * using a IP-address. 
 * @author Daniel Kuschny (dku2375)
 *
 */
public class JoinServerView extends NotGameView
{
    /**
     * A unique serialization ID
     */
    private static final long serialVersionUID = -9178940078212275715L;
    
    /**
     * The view-id used by this panel 
     */
    public static final String PANEL_ID = "join-server";
    
    /**
     * Initializes a new instance of the {@link JoinServerView} class.
     */
    public JoinServerView(Container container)
    {
        setViewText("Join Server");
        
        KuBaTschPane controlGrid = new KuBaTschPane();
        controlGrid.setLayout(new CustomGridLayout(new int[]{245,450},new int[]{44}, 0, 25));

        // IP 
        {
            SmallCapsLabel portLbl = KuBatschTheme.getLabel("Host:Port");
            portLbl.setAutoSize(false);
            controlGrid.add(portLbl, CustomGridPosition.MiddleCenter);

            controlGrid.add( KuBatschTheme.getTextBox(TextBoxSize.HUGE), CustomGridPosition.MiddleLeft);
        }
         
        add(controlGrid);
        
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
        
        add(new Separator(1, 15));
        
        add(KuBatschTheme.getLabel("Connection Failed!"));
        
        add(buttonPane);
    }
}
