/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: StartNewServerPanel.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.net.InetAddress;
import java.net.UnknownHostException;

import at.kubatsch.uicontrols.BloodTextfield;
import at.kubatsch.uicontrols.layout.CustomGridLayout;
import at.kubatsch.uicontrols.KuBaTschPane;
import at.kubatsch.uicontrols.KuBatschTheme;
import at.kubatsch.uicontrols.MenuButton;
import at.kubatsch.uicontrols.SmallCapsLabel;
import at.kubatsch.uicontrols.layout.CustomGridLayout.CustomGridPosition;
import at.kubatsch.uicontrols.KuBatschTheme.TextBoxSize;

/**
 * This view displays UI elements for configuring and starting a new server.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class StartNewServerView extends NotGameView
{
    private static final int PORT_MAX = 65535;
    /**
     * A unique serialization id.
     */
    private static final long  serialVersionUID = 4665705154541835024L;

    /**
     * The view-id used by this panel
     */
    public static final String PANEL_ID         = "start-server";

    /**
     * Initializes a new instance of the {@link StartNewServerView} class.
     */
    public StartNewServerView(Container container)
    {
        setViewText("Start New Server");

        KuBaTschPane controlGrid = new KuBaTschPane();
        controlGrid.setLayout(new CustomGridLayout(new int[]{245,445},new int[]{45,45}, 0, 25));

        // Port 
        {
            SmallCapsLabel portLbl = KuBatschTheme.getLabel("Port");
            portLbl.setAutoSize(false);
            controlGrid.add(portLbl, CustomGridPosition.MiddleCenter);

            controlGrid.add( KuBatschTheme.getNumericalTextBox(TextBoxSize.SMALL, 0, PORT_MAX), CustomGridPosition.MiddleLeft);
        }
         
        // IP 
        {
            SmallCapsLabel ipLbl = KuBatschTheme.getLabel("IP");
            ipLbl.setAutoSize(false);
            controlGrid.add(ipLbl, CustomGridPosition.MiddleCenter);
            
            
            String ip = "";
            try
            {
                ip = InetAddress.getLocalHost().getHostAddress();
            }
            catch(UnknownHostException e)
            {
                ip = "unknown";
            }
            BloodTextfield ipBox = KuBatschTheme.getTextBox(TextBoxSize.NORMAL);
            ipBox.setText(ip);
            ipBox.setEditable(false);
            controlGrid.add(ipBox, CustomGridPosition.MiddleLeft);
        }
        add(controlGrid);
        
        // Buttons
        KuBaTschPane buttonPane = new KuBaTschPane();
        buttonPane.setLayout(new FlowLayout());
        
        MenuButton startButton = new MenuButton("Start", true);
        startButton.setGlowEnabled(true);
        startButton.setTheme(KuBatschTheme.BUTTON_THEMES[1]);
        // TODO Start Server Listener
        startButton.addMouseListener(new ChangeViewClickListener(container,
                MenuView.PANEL_ID));
        buttonPane.add(startButton);
        
        MenuButton backButton = new MenuButton("Back", false);
        backButton.setTheme(KuBatschTheme.BUTTON_THEMES[3]);
        backButton.addMouseListener(new ChangeViewClickListener(container,
                MenuView.PANEL_ID));
        buttonPane.add(backButton);
        
        add(buttonPane);
    }
}
