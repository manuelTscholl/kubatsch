/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: SettingsPanel.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;

import at.kubatsch.model.Color;
import at.kubatsch.uicontrols.BloodChooser;
import at.kubatsch.uicontrols.BloodSlider;
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
    public SettingsView(Container container)
    {
        setViewText("Settings");

        KuBaTschPane controlGrid = new KuBaTschPane();
        controlGrid.setLayout(new CustomGridLayout(new int[] { 245, 500 },
                new int[] { 50, 55, 61, 60, 60, 60 }, 0, 10));

        // Nickname
        {
            SmallCapsLabel nicknameLbl = KuBatschTheme.getLabel("Nickname");
            controlGrid.add(nicknameLbl, CustomGridPosition.MiddleCenter);

            controlGrid.add(KuBatschTheme.getTextBox(TextBoxSize.NORMAL),
                    CustomGridPosition.MiddleLeft);
        }

        // Controls
        {
            SmallCapsLabel nicknameLbl = KuBatschTheme.getLabel("Controls");
            controlGrid.add(nicknameLbl, CustomGridPosition.MiddleCenter);

            KuBaTschPane controlsPane = new KuBaTschPane();
            controlsPane.setLayout(new FlowLayout());

            final MouseListener[] controlSettingsListener = {
                    new ChangeViewClickListener(container,
                            MouseSettingsView.PANEL_ID),
                    new ChangeViewClickListener(container,
                            KeySettingsView.PANEL_ID) };

            final ImageBox controlSettingsBtn = new ImageBox(
                    KuBatschTheme.CONTROL_SETTINGS);
            controlSettingsBtn.setCursor(Cursor
                    .getPredefinedCursor(Cursor.HAND_CURSOR));

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

            PaddleChooser south = new PaddleChooser("South");
            paddleChooserPane.add(south);

            PaddleChooser north = new PaddleChooser("North");
            north.setSelectedColor(Color.BLUE);
            paddleChooserPane.add(north);

            PaddleChooser east = new PaddleChooser("East");
            east.setSelectedColor(Color.GREEN);
            paddleChooserPane.add(east);

            PaddleChooser west = new PaddleChooser("West");
            west.setSelectedColor(Color.VIOLET);
            paddleChooserPane.add(west);

            controlGrid.add(paddleChooserPane, CustomGridPosition.MiddleLeft);
        }

        // BgMusic
        {
            SmallCapsLabel nicknameLbl = KuBatschTheme.getLabel("Bg-Music");
            controlGrid.add(nicknameLbl, CustomGridPosition.MiddleCenter);

            BloodSlider slider = new BloodSlider();
            controlGrid.add(slider, CustomGridPosition.MiddleLeft);
        }

        // Effects
        {
            SmallCapsLabel nicknameLbl = KuBatschTheme.getLabel("Effects");
            controlGrid.add(nicknameLbl, CustomGridPosition.MiddleCenter);

            BloodSlider slider = new BloodSlider();
            controlGrid.add(slider, CustomGridPosition.MiddleLeft);
        }

        // Hud Alpha
        {
            SmallCapsLabel nicknameLbl = KuBatschTheme.getLabel("HUD-Alpha");
            controlGrid.add(nicknameLbl, CustomGridPosition.MiddleCenter);

            BloodSlider slider = new BloodSlider();
            controlGrid.add(slider, CustomGridPosition.MiddleLeft);
        }

        add(controlGrid);

        // Buttons
        KuBaTschPane buttonPane = new KuBaTschPane();
        buttonPane.setLayout(new FlowLayout());

        MenuButton startButton = new MenuButton("Save", true);
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
