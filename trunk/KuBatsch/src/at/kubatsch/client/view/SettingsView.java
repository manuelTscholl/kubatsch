/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: SettingsPanel.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import at.kubatsch.client.controller.AudioController;
import at.kubatsch.client.controller.ClientConfigController;
import at.kubatsch.client.model.ClientConfig;
import at.kubatsch.uicontrols.BloodChooser;
import at.kubatsch.uicontrols.BloodSlider;
import at.kubatsch.uicontrols.BloodTextfield;
import at.kubatsch.uicontrols.ImageBox;
import at.kubatsch.uicontrols.KuBaTschPane;
import at.kubatsch.uicontrols.KuBatschTheme;
import at.kubatsch.uicontrols.KuBatschTheme.TextBoxSize;
import at.kubatsch.uicontrols.MenuButton;
import at.kubatsch.uicontrols.PaddleChooser;
import at.kubatsch.uicontrols.SmallCapsLabel;
import at.kubatsch.uicontrols.layout.CustomGridLayout;
import at.kubatsch.uicontrols.layout.CustomGridLayout.CustomGridPosition;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

/**
 * This panel shows the user settings.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class SettingsView extends NotGameView
{
    /**
     * A unique serialization id.
     */
    private static final long  serialVersionUID = 1811617151694922276L;

    /**
     * The view-id used by this panel
     */
    public static final String PANEL_ID         = "settings";

    /**
     * Initializes a new instance of the {@link SettingsView} class.
     */
    public SettingsView()
    {
        // Config
        final ClientConfigController configController = ClientConfigController.getInstance();
        final ClientConfig config = configController.getConfig();

        setViewText("Settings");

        KuBaTschPane controlGrid = new KuBaTschPane();
        controlGrid.setLayout(new CustomGridLayout(new int[] { 245, 500 }, new int[] {
                50, 55, 61, 60, 60, 60 }, 0, 10));

        // Nickname
        {
            SmallCapsLabel nicknameLbl = KuBatschTheme.getLabel("Nickname");
            controlGrid.add(nicknameLbl, CustomGridPosition.MiddleCenter);

            final BloodTextfield tNickname = KuBatschTheme.getTextBox(TextBoxSize.NORMAL);
            tNickname.setText(config.getName());
            tNickname.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyPressed(KeyEvent e)
                {
                    config.setName(tNickname.getText());
                }
                
                @Override
                public void keyReleased(KeyEvent e)
                {
                    keyPressed(e);
                }
            });
            controlGrid.add(tNickname, CustomGridPosition.MiddleLeft);
        }

        // Controls
        {
            SmallCapsLabel nicknameLbl = KuBatschTheme.getLabel("Controls");
            controlGrid.add(nicknameLbl, CustomGridPosition.MiddleCenter);

            KuBaTschPane controlsPane = new KuBaTschPane();
            controlsPane.setLayout(new FlowLayout());

            final MouseListener[] controlSettingsListener = {
                    new ChangeViewClickListener(MouseSettingsView.PANEL_ID),
                    new ChangeViewClickListener(KeySettingsView.PANEL_ID) };

            final ImageBox controlSettingsBtn = new ImageBox(
                    KuBatschTheme.CONTROL_SETTINGS);
            controlSettingsBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            final BloodChooser chooser = new BloodChooser();
            chooser.addItem("Mouse");
            chooser.addItem("Keyboard");
            chooser.addItemChangedHandler(new IEventHandler<EventArgs>()
            {
                @Override
                public void fired(Object sender, EventArgs e)
                {
                    for (int i = 0; i < controlSettingsListener.length; i++)
                    {
                        if (chooser.getSelectedIndex() == i)
                        {
                            controlSettingsBtn
                                    .addMouseListener(controlSettingsListener[i]);
                        }
                        else
                        {
                            controlSettingsBtn
                                    .removeMouseListener(controlSettingsListener[i]);
                        }
                    }
                }
            });
            chooser.setSelectedIndex(0);
            controlsPane.add(chooser);

            controlsPane.add(controlSettingsBtn);

            controlGrid.add(controlsPane, CustomGridPosition.MiddleLeft);
        }

        // Controls
        {
            SmallCapsLabel paddlesLbl = KuBatschTheme.getLabel("Paddles");
            controlGrid.add(paddlesLbl, CustomGridPosition.MiddleCenter);

            KuBaTschPane paddleChooserPane = new KuBaTschPane();
            paddleChooserPane.setLayout(new FlowLayout());

            final PaddleChooser south = new PaddleChooser("South");
            south.setSelectedColor(config.getSouthColor());
            south.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    config.setSouthColor(south.getSelectedColor());
                }
            });
            paddleChooserPane.add(south);

            final PaddleChooser north = new PaddleChooser("North");
            north.setSelectedColor(config.getNorthColor());
            north.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    config.setNorthColor(north.getSelectedColor());
                }
            });
                    
            paddleChooserPane.add(north);

            final PaddleChooser east = new PaddleChooser("East");
            east.setSelectedColor(config.getEastColor());
            east.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    config.setEastColor(east.getSelectedColor());
                }
            });
            paddleChooserPane.add(east);

            final PaddleChooser west = new PaddleChooser("West");
            west.setSelectedColor(config.getWestColor());
            west.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    config.setWestColor(west.getSelectedColor());
                }
            });
            paddleChooserPane.add(west);

            controlGrid.add(paddleChooserPane, CustomGridPosition.MiddleLeft);
        }

        // BgMusic
        {
            SmallCapsLabel bgMusicLbl = KuBatschTheme.getLabel("Bg-Music");
            controlGrid.add(bgMusicLbl, CustomGridPosition.MiddleCenter);

            final BloodSlider bgMusicSlider = new BloodSlider(0, 100,
                    (int) (config.getMusic() * 100));
            bgMusicSlider.addChangeListener(new ChangeListener()
            {
                @Override
                public void stateChanged(ChangeEvent e)
                {
                    float volume = bgMusicSlider.getValue() / 100f;

                    config.setMusic(volume);

                    AudioController.getInstance().setBackgroundVolume(volume);
                }
            });
            controlGrid.add(bgMusicSlider, CustomGridPosition.MiddleLeft);
        }

        // Effects
        {
            SmallCapsLabel effectsLbl = KuBatschTheme.getLabel("Effects");
            controlGrid.add(effectsLbl, CustomGridPosition.MiddleCenter);

            final BloodSlider effectsSlider = new BloodSlider(0, 100,
                    (int) (config.getEffects() * 100));
            effectsSlider.addChangeListener(new ChangeListener()
            {
                @Override
                public void stateChanged(ChangeEvent e)
                {
                    float volume = effectsSlider.getValue() / 100f;

                    config.setEffects(volume);

                    AudioController.getInstance().setEffectsVolume(volume);
                }
            });
            controlGrid.add(effectsSlider, CustomGridPosition.MiddleLeft);
        }

        // Hud Alpha
        {
            SmallCapsLabel alphaLbl = KuBatschTheme.getLabel("HUD-Alpha");
            controlGrid.add(alphaLbl, CustomGridPosition.MiddleCenter);

            final BloodSlider alphaSlider = new BloodSlider(0, 100,
                    (int) (config.getHudAlpha() * 100));
            alphaSlider.addChangeListener(new ChangeListener()
            {
                @Override
                public void stateChanged(ChangeEvent e)
                {
                    System.out.println(alphaSlider.getValue());
                    config.setHudAlpha(alphaSlider.getValue() / 100f);
                }
            });
            controlGrid.add(alphaSlider, CustomGridPosition.MiddleLeft);
        }

        add(controlGrid);

        // Buttons
        KuBaTschPane buttonPane = new KuBaTschPane();
        buttonPane.setLayout(new FlowLayout());

        final MenuButton startButton = new MenuButton("Save", true);
        startButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON1)
                {
                    try
                    {
                        //Save the changes in the configfile
                        configController.writeConfig();
                    }
                    catch (IOException e1)
                    {
                        //TODO Loggen
                    }
                }
            }
        });
        startButton.setGlowEnabled(true);
        startButton.setTheme(KuBatschTheme.BUTTON_THEMES[1]);

        startButton.addMouseListener(new ChangeViewClickListener(MenuView.PANEL_ID));
        buttonPane.add(startButton);

        final MenuButton backButton = new MenuButton("Back", false);
        backButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON1)
                {
                    try
                    {
                        configController.loadConfig();
                    }
                    catch (Exception e1)
                    {
                        //TODO Loggen oder standartconfig Laden
                    }
                }
            }
        });
        backButton.setTheme(KuBatschTheme.BUTTON_THEMES[3]);
        backButton.addMouseListener(new ChangeViewClickListener(MenuView.PANEL_ID));
        buttonPane.add(backButton);

        add(buttonPane);
    }
}
