/**
 * This file is part of KuBatsch.
 *   created on: 27.01.2011
 *   filename: MouseSettingsView.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import at.kubatsch.client.controller.ClientConfigController;
import at.kubatsch.client.model.ClientConfig;
import at.kubatsch.client.model.gear.MouseConfig;
import at.kubatsch.uicontrols.BloodSlider;
import at.kubatsch.uicontrols.KuBaTschPane;
import at.kubatsch.uicontrols.KuBatschTheme;
import at.kubatsch.uicontrols.MenuButton;
import at.kubatsch.uicontrols.SmallCapsLabel;
import at.kubatsch.uicontrols.layout.CustomGridLayout;
import at.kubatsch.uicontrols.layout.CustomGridLayout.CustomGridPosition;

/**
 * This view allows the user to configure the mouse
 * input settings. 
 * @author Daniel Kuschny (dku2375)
 */
public class MouseSettingsView extends NotGameView
{
    /**
     * A unique serialization id.
     */
    private static final long  serialVersionUID = 8927458567868300449L;
    /**
     * The view-id used by this panel
     */
    public static final String PANEL_ID         = "menu-settings";

    /**
     * Initializes a new instance of the {@link MouseSettingsView} class.
     * @param container the parent container
     */
    public MouseSettingsView()
    {
        ClientConfigController configController = ClientConfigController.getInstance();
        ClientConfig config = configController.getConfig();
        final MouseConfig mouseConfig = (MouseConfig) config.getControlType()[1];

        setViewText("Mouse-Settings");

        KuBaTschPane controlGrid = new KuBaTschPane();
        add(controlGrid);
        controlGrid.setLayout(new CustomGridLayout(new int[] { 245, 500 },
                new int[] { 60 }, 0, 10));

        // Sensitivity
        {
            SmallCapsLabel sensitivityLbl = KuBatschTheme.getLabel("Sensitivity");
            controlGrid.add(sensitivityLbl, CustomGridPosition.MiddleCenter);

            final BloodSlider sensitivity = new BloodSlider(0, 100,
                    (int) mouseConfig.getSensitivity() * 100);
            sensitivity.addChangeListener(new ChangeListener()
            {
                @Override
                public void stateChanged(ChangeEvent e)
                {
                    mouseConfig.setSensitivity(sensitivity.getValue() / 100f);
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
