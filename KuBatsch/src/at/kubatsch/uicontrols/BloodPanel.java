/**
 * This file is part of KuBatsch.
 *   created on: 15.01.2011
 *   filename: BloodPanel.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * This is the main applications panel which requires a fixed square size and
 * provides the functionality of fading blood onto the default background.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class BloodPanel extends KuBaTschPane
{
    /**
     * The applications content size.
     */
    private static final Dimension     PANEL_SIZE       = new Dimension(800,
                                                                800);
    /**
     * An image 
     */
    private static final BufferedImage BLOOD_ALPHA;
    
    /**
     * A unique serialization id.
     */
    private static final long          serialVersionUID = 2022857904089938769L;

    /**
     * Initializes the static members of the BloodPanel class.
     */
    static
    {
        BLOOD_ALPHA = new BufferedImage(PANEL_SIZE.width, PANEL_SIZE.height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = BLOOD_ALPHA.createGraphics();
        g.drawImage(KuBatschTheme.BLOOD_BACKGROUND, 0, 0, null);
        g.dispose();
    }


    private float                      _bloodOpacity;
    
    /**
     * Initializes a new instance of the {@link BloodPanel} class.
     */
    public BloodPanel()
    {
        setDoubleBuffered(true);
    }

    /**
     * Gets the alpha value for painting the blood overlay. .
     * @return a value from 0 to 1 indicating the alpha value. 0 is not visible,
     *         1 is full visible
     */
    public float getBloodOpacity()
    {
        return _bloodOpacity;
    }

    /**
     * Sets the alpha value for painting the blood overlay.
     * @param bloodOpacity a value from 0 to 1 indicating the alpha value. 0 is
     *            not visible, 1 is full visible
     */
    public void setBloodOpacity(float bloodOpacity)
    {
        _bloodOpacity = Math.min(Math.max(0, bloodOpacity), 1);
        repaint();
    }

    /**
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize()
    {
        return PANEL_SIZE;
    }

    /**
     * @see java.awt.Component#resize(int, int)
     */
    @SuppressWarnings("deprecation")
    @Override
    public void resize(int width, int height)
    {
        // use deprecated method as java uses this method
        // as last call for applying the size.
        // this enforces the specified size.
        super.resize(PANEL_SIZE.width, PANEL_SIZE.width);
    }
    
    /**
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        // draw default background.
        Image img = KuBatschTheme.MAIN_BACKGROUND;
        g.drawImage(img, 0, 0, PANEL_SIZE.width,
                PANEL_SIZE.height, this);

        // draw blood overlay
        if (_bloodOpacity > 0)
        {
            Graphics2D g2d = (Graphics2D) g;
            AlphaComposite alphaComposite = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, _bloodOpacity);
            g2d.setComposite(alphaComposite);
            g2d.drawImage(BLOOD_ALPHA, null, 0, 0);
        }
        
        paintComponents(g);
    }
}
