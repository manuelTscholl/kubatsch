/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: NotGamePanel.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import at.kubatsch.uicontrols.BloodPanel;
import at.kubatsch.uicontrols.ImageBox;
import at.kubatsch.uicontrols.KuBatschTheme;
import at.kubatsch.uicontrols.Separator;
import at.kubatsch.uicontrols.SmallCapsLabel;
import at.kubatsch.uicontrols.layout.VerticalLayout;

/**
 * This is the base panel for KuBaTsch Views which are not the game view.
 * It adds the KuBaTsch logo and a label showing the current view. 
 * @author Daniel Kuschny (dku2375)
 */
public abstract class NotGameView extends BloodPanel
{
    /**
     * A unique serialization id. 
     */
    private static final long serialVersionUID = -469718004597041533L;

    private SmallCapsLabel    _viewLabel;

    /**
     * Initializes a new instance of the {@link NotGameView} class.
     */
    public NotGameView()
    {
        setBloodOpacity(1);
        setLayout(new VerticalLayout());

        add(new Separator(1, 88));

        // KuBaTsch logo
        ImageBox kubatschLogo = new ImageBox(KuBatschTheme.KUBATSCH_LOGO);
        add(kubatschLogo);

        add(new Separator(1, 23));

        // Text
        _viewLabel = KuBatschTheme.getLabel("Will You Survive?");
        add(_viewLabel);

        add(new Separator(1, 40));
    }

    /**
     * Sets the text displayed on the top of the view. 
     * @param text the text.
     */
    protected void setViewText(String text)
    {
        _viewLabel.setText(text);
    }
}
