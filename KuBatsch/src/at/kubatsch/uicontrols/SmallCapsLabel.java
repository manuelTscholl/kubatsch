/**
 * This file is part of KuBatsch.
 *   created on: 15.01.2011
 *   filename: AntiAliasedLabel.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * A label rendering fonts using small caps and enabled antialiasing
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class SmallCapsLabel extends JLabel
{
    /**
     * A unique serialization id.
     */
    private static final long serialVersionUID = 2351191002825496523L;

    private Font              _smallCapsFont;
    private boolean           _autoSize = true;

    /**
     * Gets the autoSize.
     * @return the autoSize
     */
    public boolean isAutoSize()
    {
        return _autoSize;
    }

    /**
     * Sets the autoSize.
     * @param autoSize the autoSize to set
     */
    public void setAutoSize(boolean autoSize)
    {
        _autoSize = autoSize;
        invalidate();
        repaint();
    }

    /**
     * Gets the font used for rendering small caps.
     * @return the font used for rendering small caps.
     */
    public Font getSmallCapsFont()
    {
        return _smallCapsFont != null ? _smallCapsFont : getFont();
    }

    /**
     * Sets the font used for rendering small caps..
     * @param smallCapsFont the font used for rendering small caps.
     */
    public void setSmallCapsFont(Font smallCapsFont)
    {
        _smallCapsFont = smallCapsFont;
        invalidate();
        repaint();
    }

    /**
     * Initializes a new instance of the {@link SmallCapsLabel} class.
     */
    public SmallCapsLabel()
    {
        super();
    }

    /**
     * Initializes a new instance of the {@link SmallCapsLabel} class.
     * @param image
     * @param horizontalAlignment
     */
    public SmallCapsLabel(Icon image, int horizontalAlignment)
    {
        super(image, horizontalAlignment);
    }

    /**
     * Initializes a new instance of the {@link SmallCapsLabel} class.
     * @param image
     */
    public SmallCapsLabel(Icon image)
    {
        super(image);
    }

    /**
     * Initializes a new instance of the {@link SmallCapsLabel} class.
     * @param text
     * @param icon
     * @param horizontalAlignment
     */
    public SmallCapsLabel(String text, Icon icon, int horizontalAlignment)
    {
        super(text, icon, horizontalAlignment);
    }

    /**
     * Initializes a new instance of the {@link SmallCapsLabel} class.
     * @param text
     * @param horizontalAlignment
     */
    public SmallCapsLabel(String text, int horizontalAlignment)
    {
        super(text, horizontalAlignment);
    }

    /**
     * Initializes a new instance of the {@link SmallCapsLabel} class.
     * @param text
     */
    public SmallCapsLabel(String text)
    {
        super(text);
    }

    /**
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize()
    {
        return SmallCapsUtility.calculateSize(this, getText(), getFont(),
                getSmallCapsFont());
    }

    /**
     * @see javax.swing.JComponent#getMaximumSize()
     */
    @Override
    public Dimension getMaximumSize()
    {
        return _autoSize ? getPreferredSize() : super.getMaximumSize();
    }

    /**
     * @see javax.swing.JComponent#getMinimumSize()
     */
    @Override
    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }

    /**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    public void paintComponent(Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(getForeground());

        int x = 0;
        int y = 0;
        if (_autoSize)
        {
            Dimension textSize = SmallCapsUtility.calculateSize(this,
                    getText(), getFont(), getSmallCapsFont());

            x = (getWidth() - textSize.width) / 2;
            y = (getHeight() - textSize.height) / 2;
        }

        SmallCapsUtility.render(g, this, getText(), getFont(),
                getSmallCapsFont(), x, y);
    }
}
