/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: MenuButton.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.Timer;

import at.kubatsch.uicontrols.KuBatschTheme.ButtonTheme;

/**
 * Represents a button which uses the {@link KuBatschTheme} to render different
 * button styles.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class MenuButton extends JLabel
{
    /**
     * A unique serialization id.
     */
    private static final long  serialVersionUID = -950923926760782060L;

    private static final float GLOW_INTERVAL    = 0.1f;

    private boolean            _isSelected;
    private float              _glowAlpha;
    private ButtonTheme        _theme;
    private Font               _smallCapsFont;
    private Timer              _glowTimer;
    private boolean            _fadeIn;

    /**
     * Initializes a new instance of the {@link MenuButton} class.
     */
    public MenuButton()
    {
        this("");
    }

    /**
     * Initializes a new instance of the {@link MenuButton} class.
     */
    public MenuButton(String text)
    {
        this(text, false);
    }

    /**
     * Initializes a new instance of the {@link MenuButton} class.
     */
    public MenuButton(String text, boolean isSelected)
    {
        setDoubleBuffered(true);
        _theme = KuBatschTheme.BUTTON_THEMES[0];
        setFont(KuBatschTheme.MAIN_FONT.deriveFont(25f));
        setSmallCapsFont(KuBatschTheme.MAIN_FONT.deriveFont(20f));
        setText(text);
        setSelected(isSelected);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        _fadeIn = true;
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
     * Gets the theme.
     * @return the theme
     */
    public ButtonTheme getTheme()
    {
        return _theme;
    }

    /**
     * Sets the theme.
     * @param theme the theme to set
     */
    public void setTheme(ButtonTheme theme)
    {
        _theme = theme;
    }

    /**
     * Gets a value indicating whether the button should be rendered as active.
     * @return true if the button should be rendered as active, otherwise false.
     */
    public boolean isSelected()
    {
        return _isSelected;
    }

    /**
     * Sets a value indicating whether the button should be rendered as active.
     * @param isSelected true if the button should be rendered as active,
     *            otherwise false.
     */
    public void setSelected(boolean isSelected)
    {
        if (_isSelected == isSelected)
            return;

        _isSelected = isSelected;
        if (_glowTimer != null)
        {
            _glowAlpha = 0;
            if (isSelected)
            {
                _glowTimer.start();
            }
            else
            {
                _glowTimer.stop();
            }
        }
        repaint();
    }

    /**
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize()
    {
        return KuBatschTheme.BUTTON_SIZE;
    }

    /**
     * @see javax.swing.JComponent#getMinimumSize()
     */
    @Override
    public Dimension getMinimumSize()
    {
        return KuBatschTheme.BUTTON_SIZE;
    }

    /**
     * @see javax.swing.JComponent#getMaximumSize()
     */
    @Override
    public Dimension getMaximumSize()
    {
        return KuBatschTheme.BUTTON_SIZE;
    }

    /**
     * Simulates a mouselick onto the button.
     */
    public void doClick()
    {
        processMouseEvent(new MouseEvent(this, MouseEvent.MOUSE_CLICKED,
                System.currentTimeMillis(), 0, 0, 0, 1, false));
    }

    /**
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        // super.paint(g);

        if (_theme == null)
            return;

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // bg
        Image bg = isSelected() ? _theme.getActiveImage() : _theme
                .getNormalImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);

        // text
        Color fg = isSelected() ? _theme.getActiveColor() : _theme
                .getFontColor();
        g.setColor(fg);
        Dimension textSize = SmallCapsUtility.calculateSize(this, getText(),
                getFont(), getSmallCapsFont());

        int x = (getWidth() - textSize.width) / 2;
        int y = (getHeight() - textSize.height) / 2;

        SmallCapsUtility.render(g, this, getText(), getFont(),
                getSmallCapsFont(), x, y);

        // glow
        if (_glowAlpha > 0)
        {
            Graphics2D g2d = (Graphics2D) g;
            AlphaComposite alphaComposite = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, _glowAlpha);
            g2d.setComposite(alphaComposite);
            g2d.drawImage(_theme.getGlowImage(), null, 0, 0);
        }
    }

    /**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        // prevent default label drawing
    }

    private void updateGlow()
    {
        if (_fadeIn)
        {
            _glowAlpha += GLOW_INTERVAL;

            if (_glowAlpha >= 1f)
            {
                _glowAlpha = 1f;
                _fadeIn = false;
            }
        }
        else
        {
            _glowAlpha -= GLOW_INTERVAL;

            if (_glowAlpha <= 0)
            {
                _glowAlpha = 0f;
                _fadeIn = true;
            }
        }

        repaint();
    }

    /**
     * @param enabled
     */
    public void setGlowEnabled(boolean enabled)
    {
        if (enabled && _glowTimer == null)
        {
            _glowTimer = new Timer(50, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    updateGlow();
                }
            });

            if (isSelected())
            {
                _glowTimer.start();
            }
        }
        else if (!enabled && _glowTimer != null)
        {
            _glowTimer.stop();
            _glowTimer = null;
        }
    }
}
