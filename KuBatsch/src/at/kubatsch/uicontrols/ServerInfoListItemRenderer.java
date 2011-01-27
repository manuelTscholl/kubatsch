/**
 * This file is part of KuBatsch.
 *   created on: 19.01.2011
 *   filename: OnlineServerListItemRenderer.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import at.kubatsch.model.ServerInfo;
import at.kubatsch.uicontrols.layout.CustomGridLayout;
import at.kubatsch.uicontrols.layout.CustomGridLayout.CustomGridPosition;

/**
 * A {@link ListCellRenderer} for displaying {@link ServerInfo} objects. 
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class ServerInfoListItemRenderer extends JPanel implements
        ListCellRenderer
{
    /**
     * A unique serialization id. 
     */
    private static final long serialVersionUID = -767739821966177959L;
    
    private ServerInfo _server;
    private SmallCapsLabel _nameLabel;
    private SmallCapsLabel _playerLabel;
    
    /**
     * Gets the server to display
     * @return the server
     */
    public ServerInfo getServer()
    {
        return _server;
    }

    /**
     * Sets the server to display.
     * @param server the server to set
     */
    public void setServer(ServerInfo server)
    {
        _server = server;
        updateText();
    }

    /**
     * Updates the text. 
     */
    private void updateText()
    {
        _nameLabel.setText(_server.getName());
        _playerLabel.setText(String.format("%d/4", _server.getCurrentPlayers()));
    }

    /**
     * Initializes a new instance of the {@link ServerInfoListItemRenderer}
     * class.
     */
    public ServerInfoListItemRenderer()
    {
        setLayout(new CustomGridLayout(new int[]{555, 75}, new int[]{35}));
        _nameLabel = new SmallCapsLabel();
        _playerLabel = new SmallCapsLabel();
        KuBatschTheme.initializeLabel(_nameLabel);
        KuBatschTheme.initializeLabel(_playerLabel);
        
        add(_nameLabel, CustomGridPosition.MiddleLeft);
        add(_playerLabel, CustomGridPosition.MiddleLeft);
        
        setOpaque(false);
    }

    /**
     * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList,
     *      java.lang.Object, int, boolean, boolean)
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus)
    {

        if (isSelected)
        {
            _nameLabel.setForeground(KuBatschTheme.TEXTBOX_FOREGROUND[0]);
            _playerLabel.setForeground(KuBatschTheme.TEXTBOX_FOREGROUND[0]);
        }
        else
        {
            _nameLabel.setForeground(KuBatschTheme.TEXTBOX_FOREGROUND[1]);
            _playerLabel.setForeground(KuBatschTheme.TEXTBOX_FOREGROUND[1]);
        }

        if (value instanceof ServerInfo)
        {
            setServer((ServerInfo) value);
        }
        else
        {
            setServer(null);
        }

        return this;
    }
}
