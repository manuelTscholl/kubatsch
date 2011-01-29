/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: PlayOnlinePanel.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;

import at.kubatsch.client.controller.JoinServerController;
import at.kubatsch.client.controller.JoinServerException;
import at.kubatsch.client.controller.PlayOnlineController;
import at.kubatsch.model.ServerInfo;
import at.kubatsch.uicontrols.BloodScrollPane;
import at.kubatsch.uicontrols.KuBaTschPane;
import at.kubatsch.uicontrols.KuBatschTheme;
import at.kubatsch.uicontrols.MenuButton;
import at.kubatsch.uicontrols.ServerInfoListItemRenderer;
import at.kubatsch.uicontrols.SmallCapsLabel;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

/**
 * This view lists the online servers.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class PlayOnlineView extends NotGameView implements INotifiableView
{
    /**
     * A unique serialization ID
     */
    private static final long  serialVersionUID = 9032562409193287921L;

    /**
     * The view-id used by this panel
     */
    public static final String PANEL_ID         = "play-online";
    
    private SmallCapsLabel _errorLbl;
    private JList _serverList;

    /**
     * Initializes a new instance of the {@link PlayOnlineView} class.
     */
    public PlayOnlineView()
    {
        setViewText("Play Online");

        _serverList = new JList();
        _serverList.setOpaque(false);
        _serverList.setCellRenderer(new ServerInfoListItemRenderer());
        _serverList.setBorder(new EmptyBorder(10, 20, 10, 20));
        _serverList.setSelectedIndex(0);

        BloodScrollPane listContainer = new BloodScrollPane(_serverList);
        add(listContainer);
        
        _errorLbl = KuBatschTheme.getLabel("");
        _errorLbl.setVisible(false);
        add(_errorLbl);
        
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
                    ServerInfo info = (ServerInfo) _serverList.getSelectedValue();
                    JoinServerController.getInstance().joinServer(info);
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
        
        add(buttonPane);

        MenuButton refreshButton = new MenuButton("Refesh", false);
        refreshButton.addMouseListener(new MouseAdapter()
        {
            /**
             * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseClicked(MouseEvent e)
            {
                PlayOnlineController.getInstance().refreshServers();
            }
        });
        refreshButton.setTheme(KuBatschTheme.BUTTON_THEMES[0]);
        add(refreshButton);
        
        PlayOnlineController.getInstance().addUpdatedListeners(new IEventHandler<EventArgs>()
        {
            @Override
            public void fired(Object sender, EventArgs e)
            {
                final List<ServerInfo> data = PlayOnlineController.getInstance().getServers();
                _serverList.setModel(new AbstractListModel() {
                    public int getSize() { return data.size(); }
                    public Object getElementAt(int i) { return data.get(i); }
                });
                
                if(_serverList.getModel().getSize() > 0)
                {
                    _serverList.setSelectedIndex(0);
                }
            }
        });
    }
    
    
    /**
     * @see at.kubatsch.client.view.INotifiableView#viewDisplaying()
     */
    @Override
    public void viewDisplaying()
    {
        PlayOnlineController.getInstance().refreshServers();
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
