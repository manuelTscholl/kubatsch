/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: StartNewServerPanel.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import at.kubatsch.client.controller.StartNewServerController;
import at.kubatsch.client.controller.StartServerException;
import at.kubatsch.client.controller.ViewController;
import at.kubatsch.uicontrols.BloodTextfield;
import at.kubatsch.uicontrols.layout.CustomGridLayout;
import at.kubatsch.uicontrols.BloodIntTextfield;
import at.kubatsch.uicontrols.KuBaTschPane;
import at.kubatsch.uicontrols.KuBatschTheme;
import at.kubatsch.uicontrols.MenuButton;
import at.kubatsch.uicontrols.SmallCapsLabel;
import at.kubatsch.uicontrols.layout.CustomGridLayout.CustomGridPosition;
import at.kubatsch.uicontrols.KuBatschTheme.TextBoxSize;
import at.kubatsch.util.KuBaTschUtils;

/**
 * This view displays UI elements for configuring and starting a new server.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class StartNewServerView extends NotGameView implements INotifiableView
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

    private SmallCapsLabel _errorLbl;
    
    /**
     * Initializes a new instance of the {@link StartNewServerView} class.
     */
    public StartNewServerView()
    {
        setViewText("Start New Server");

        KuBaTschPane controlGrid = new KuBaTschPane();
        controlGrid.setLayout(new CustomGridLayout(new int[]{245,445},new int[]{45,45}, 0, 25));

        // Port 
        SmallCapsLabel portLbl = KuBatschTheme.getLabel("Port");
        portLbl.setAutoSize(false);
        controlGrid.add(portLbl, CustomGridPosition.MiddleCenter);

        final BloodIntTextfield portBox = KuBatschTheme.getNumericalTextBox(TextBoxSize.SMALL, 0, PORT_MAX);
        portBox.setValue(KuBaTschUtils.DEFAULT_SERVER_PORT);
        controlGrid.add( portBox, CustomGridPosition.MiddleLeft);
         
        // IP 
        SmallCapsLabel ipLbl = KuBatschTheme.getLabel("IP");
        ipLbl.setAutoSize(false);
        controlGrid.add(ipLbl, CustomGridPosition.MiddleCenter);
        
        
        BloodTextfield ipBox = KuBatschTheme.getTextBox(TextBoxSize.NORMAL);
        ipBox.setText(KuBaTschUtils.getLocalIp());
        ipBox.setEditable(false);
        controlGrid.add(ipBox, CustomGridPosition.MiddleLeft);
        add(controlGrid);
        
        // error label
        _errorLbl = KuBatschTheme.getLabel("");
        _errorLbl.setVisible(false);
        add(_errorLbl);
        
        // Buttons
        KuBaTschPane buttonPane = new KuBaTschPane();
        buttonPane.setLayout(new FlowLayout());
        
        final MenuButton backButton = new MenuButton("Back", false);

        MenuButton startButton = new MenuButton("Start", true);
        startButton.setGlowEnabled(true);
        startButton.setTheme(KuBatschTheme.BUTTON_THEMES[1]);
        startButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                try
                {
                    StartNewServerController.getInstance().startServer(portBox.getValue());
                    ViewController.getInstance().switchToView(MenuView.PANEL_ID);
                }
                catch (StartServerException ex)
                {
                    _errorLbl.setText(ex.getMessage());
                    _errorLbl.setVisible(true);
                }
            }
        });
        buttonPane.add(startButton);
        
        backButton.setTheme(KuBatschTheme.BUTTON_THEMES[3]);
        backButton.addMouseListener(new ChangeViewClickListener(MenuView.PANEL_ID));
        buttonPane.add(backButton);
        
        add(buttonPane);
    }

    /**
     * @see at.kubatsch.client.view.INotifiableView#viewDisplaying()
     */
    @Override
    public void viewDisplaying()
    {
        _errorLbl.setVisible(false);
    }

    /**
     * @see at.kubatsch.client.view.INotifiableView#viewHidding()
     */
    @Override
    public void viewHidding()
    {
    }
    
    
}
