/**
 * This file is part of KuBatsch.
 *   created on: 27.01.2011
 *   filename: KeySettingsView.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import at.kubatsch.client.controller.ClientConfigController;
import at.kubatsch.client.model.ClientConfig;
import at.kubatsch.client.model.gear.KeyboardConfig;
import at.kubatsch.uicontrols.BloodSlider;
import at.kubatsch.uicontrols.BloodTextfield;
import at.kubatsch.uicontrols.KuBaTschPane;
import at.kubatsch.uicontrols.KuBatschTheme;
import at.kubatsch.uicontrols.KuBatschTheme.TextBoxSize;
import at.kubatsch.uicontrols.MenuButton;
import at.kubatsch.uicontrols.SmallCapsLabel;
import at.kubatsch.uicontrols.layout.CustomGridLayout;
import at.kubatsch.uicontrols.layout.CustomGridLayout.CustomGridPosition;

/**
 * This view displays elements for configuring the keyboard input settings.
 * @author Daniel Kuschny (dku2375)
 */
public class KeySettingsView extends NotGameView
{
    /**
     * A unique serialization id.
     */
    private static final long  serialVersionUID = 3750980551141332245L;
    /**
     * The view-id used by this panel
     */
    public static final String PANEL_ID         = "key-settings";

    /**
     * Initializes a new instance of the {@link KeySettingsView} class.
     * @param container the parent container
     */
    public KeySettingsView()
    {
        ClientConfigController configController = ClientConfigController.getInstance();
        ClientConfig config = configController.getConfig();
        final KeyboardConfig keyConfig = (KeyboardConfig) config.getControlType()[0];

        setViewText("Keyboard-Settings");

        KuBaTschPane controlGrid = new KuBaTschPane();
        add(controlGrid);
        controlGrid.setLayout(new CustomGridLayout(new int[] { 245, 500 }, new int[] {
                50, 60 }, 0, 10));

        // Keys
        {
            SmallCapsLabel sensitivityLbl = KuBatschTheme.getLabel("Keys");
            controlGrid.add(sensitivityLbl, CustomGridPosition.MiddleCenter);

            KuBaTschPane keysPane = new KuBaTschPane();
            keysPane.setLayout(new FlowLayout());
            controlGrid.add(keysPane, CustomGridPosition.MiddleLeft);

            final BloodTextfield leftKey = KuBatschTheme.getTextBox(TextBoxSize.SMALL);
            leftKey.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyReleased(KeyEvent e)
                {
                    // Save key in config instance
                    keyConfig.setLeftKey(e.getKeyCode());
                    // Show the Key in the field
                    leftKey.setText(KeyEvent.getKeyText(e.getKeyCode()));
                }
            });
            leftKey.setText(KeyEvent.getKeyText(keyConfig.getLeftKey()));
            keysPane.add(leftKey);

            final BloodTextfield rightKey = KuBatschTheme.getTextBox(TextBoxSize.SMALL);
            rightKey.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyReleased(KeyEvent e)
                {
                    // Save key in config instance
                    keyConfig.setRightKey(e.getKeyCode());
                    // Show the Key in the field
                    rightKey.setText(KeyEvent.getKeyText(e.getKeyCode()));
                }
            });
            rightKey.setText(KeyEvent.getKeyText(keyConfig.getRightKey()));
            keysPane.add(rightKey);
        }

        // Sensitivity
        {
            SmallCapsLabel sensitivityLbl = KuBatschTheme.getLabel("Sensitivity");
            controlGrid.add(sensitivityLbl, CustomGridPosition.MiddleCenter);

            // it is not allowed that the user uses the hole time with the
            // keyboard
            final BloodSlider sensitivity = new BloodSlider(3, 103,
                    keyConfig.getRepeateRate());
            sensitivity.addChangeListener(new ChangeListener()
            {
                @Override
                public void stateChanged(ChangeEvent e)
                {
                    keyConfig.setRepeateRate(sensitivity.getValue());
                }
            });
            controlGrid.add(sensitivity, CustomGridPosition.MiddleLeft);
        }

        MenuButton backButton = new MenuButton("Back", false);
        backButton.setTheme(KuBatschTheme.BUTTON_THEMES[3]);
        backButton.addMouseListener(new ChangeViewClickListener(SettingsView.PANEL_ID));
        add(backButton);
    }
}
