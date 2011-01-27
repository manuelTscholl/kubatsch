/**
 * This file is part of KuBatsch.
 *   created on: 19.01.2011
 *   filename: BloodList.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * This ScrollPane is meant to be used in combination with a {@link JList} It
 * renders a bloody background and enforces a fixed size of
 * {@link KuBatschTheme#LIST_SIZE}
 * @author Daniel Kuschny (dku2375)
 */
public class BloodScrollPane extends JScrollPane
{
    /**
     * A unique serialization id. 
     */
    private static final long serialVersionUID = 3066251009011958492L;

    /**
     * Initializes a new instance of the {@link BloodScrollPane} class.
     * @param view
     * @param vsbPolicy
     * @param hsbPolicy
     */
    public BloodScrollPane(Component view, int vsbPolicy, int hsbPolicy)
    {
        super(view, vsbPolicy, hsbPolicy);
        initialize();
    }

    /**
     * Initializes a new instance of the {@link BloodScrollPane} class.
     * @param view
     */
    public BloodScrollPane(Component view)
    {
        super(view);
        initialize();
    }

    /**
     * Initializes a new instance of the {@link BloodScrollPane} class.
     * @param vsbPolicy
     * @param hsbPolicy
     */
    public BloodScrollPane(int vsbPolicy, int hsbPolicy)
    {
        super(vsbPolicy, hsbPolicy);
        initialize();
    }

    /**
     * Initializes the scrollpanel to be transparent
     */
    private void initialize()
    {
        setOpaque(false);
        getViewport().setOpaque(false);
        setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    /**
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize()
    {
        return KuBatschTheme.LIST_SIZE;
    }

    /**
     * @see javax.swing.JComponent#getMinimumSize()
     */
    @Override
    public Dimension getMinimumSize()
    {
        return KuBatschTheme.LIST_SIZE;
    }

    /**
     * @see javax.swing.JComponent#getMaximumSize()
     */
    @Override
    public Dimension getMaximumSize()
    {
        return KuBatschTheme.LIST_SIZE;
    }

    /**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D gd = (Graphics2D) g;
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(KuBatschTheme.LIST_BACKGROUND, 0, 0,
                KuBatschTheme.LIST_BACKGROUND.getWidth(this),
                KuBatschTheme.LIST_BACKGROUND.getHeight(this), this);
        super.paintComponent(g);
    }
}
