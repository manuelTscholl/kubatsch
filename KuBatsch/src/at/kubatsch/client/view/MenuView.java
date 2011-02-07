/**
 * This file is part of KuBatsch.
 *   created on: 15.01.2011
 *   filename: MenuPanel.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import at.kubatsch.uicontrols.KuBatschTheme;
import at.kubatsch.uicontrols.MenuButton;

/**
 * This panel displays the menu.
 * @author Daniel Kuschny (dku2375)
 */
public class MenuView extends NotGameView
{
    /**
     * The view-id used by this panel 
     */
    public static final String PANEL_ID = "menu";
    
    /**
     * A unique serialization id.
     */
    private static final long serialVersionUID = -2247406508857174528L;

    /**
     * Initializes a new instance of the {@link MenuView} class.
     * @param container 
     */
    public MenuView()
    {
        // Text
        setViewText("Will You Survive?");
        
        // Buttons
        MenuButton newServerBtn = new MenuButton("Start New Server", true);
        newServerBtn.setTheme(KuBatschTheme.BUTTON_THEMES[0]);
        newServerBtn.addMouseListener(new ChangeViewClickListener(StartNewServerView.PANEL_ID));
        
        MenuButton joinServerBtn = new MenuButton("Join Server", false);
        joinServerBtn.setTheme(KuBatschTheme.BUTTON_THEMES[1]);
        joinServerBtn.addMouseListener(new ChangeViewClickListener(JoinServerView.PANEL_ID));
        
        MenuButton playOnlineBtn = new MenuButton("Play Online", false);
        playOnlineBtn.setTheme(KuBatschTheme.BUTTON_THEMES[2]);
        playOnlineBtn.addMouseListener(new ChangeViewClickListener(PlayOnlineView.PANEL_ID));
        
        MenuButton settingsBtn = new MenuButton("Settings", false);
        settingsBtn.setTheme(KuBatschTheme.BUTTON_THEMES[3]);
        settingsBtn.addMouseListener(new ChangeViewClickListener(SettingsView.PANEL_ID));
        
        MenuButton quitBtn = new MenuButton("Quit", false);
        quitBtn.setTheme(KuBatschTheme.BUTTON_THEMES[4]);
        quitBtn.addMouseListener(new QuitApplicationMouseListener(this));
        
        MenuButton[] menuButtons = {newServerBtn, joinServerBtn, playOnlineBtn, settingsBtn, quitBtn};
        MenuButtonSelectionMouseAdapter mouseHandler = new MenuButtonSelectionMouseAdapter(menuButtons);
        for (MenuButton menuButton : menuButtons)
        {
            menuButton.setGlowEnabled(true);
            menuButton.addMouseListener(mouseHandler);
            add(menuButton);
        }
    }
}
