/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: JoinServerPanel.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import at.kubatsch.client.controller.JoinServerController;
import at.kubatsch.client.controller.JoinServerException;
import at.kubatsch.uicontrols.BloodTextfield;
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
public class JoinServerView extends NotGameView implements INotifiableView
{
    /**
     * A unique serialization ID
     */
    private static final long serialVersionUID = -9178940078212275715L;
    
    /**
     * The view-id used by this panel 
     */
    public static final String PANEL_ID = "join-server";
    
    private SmallCapsLabel _errorLbl;
    
    /**
     * Initializes a new instance of the {@link JoinServerView} class.
     */
    public JoinServerView()
    {
        setViewText("Join Server");
        
        KuBaTschPane controlGrid = new KuBaTschPane();
        controlGrid.setLayout(new CustomGridLayout(new int[]{245,450},new int[]{44}, 0, 25));

        // Connection String 
        SmallCapsLabel portLbl = KuBatschTheme.getLabel("Host:Port");
        portLbl.setAutoSize(false);
        controlGrid.add(portLbl, CustomGridPosition.MiddleCenter);

        final BloodTextfield serverAdressBox = KuBatschTheme.getTextBox(TextBoxSize.HUGE);
        controlGrid.add(serverAdressBox, CustomGridPosition.MiddleLeft);
        add(controlGrid);
        
        // Buttons
        KuBaTschPane buttonPane = new KuBaTschPane();
        buttonPane.setLayout(new FlowLayout());
        
        MenuButton startButton = new MenuButton("Join", true);
        startButton.setGlowEnabled(true);
        startButton.setTheme(KuBatschTheme.BUTTON_THEMES[3]);
        startButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                try
                {
                    JoinServerController.getInstance().joinServer(serverAdressBox.getText());
                }
                catch (JoinServerException ex)
                { 
                    _errorLbl.setText(ex.getMessage());
                    _errorLbl.setVisible(true);
                }
            }
        });
        buttonPane.add(startButton);
        
        MenuButton backButton = new MenuButton("Back", false);
        backButton.setTheme(KuBatschTheme.BUTTON_THEMES[4]);
        backButton.addMouseListener(new ChangeViewClickListener(MenuView.PANEL_ID));
        buttonPane.add(backButton);
        
        add(new Separator(1, 15));
        
        _errorLbl = KuBatschTheme.getLabel("");
        _errorLbl.setVisible(false);
        add(_errorLbl);
        
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
