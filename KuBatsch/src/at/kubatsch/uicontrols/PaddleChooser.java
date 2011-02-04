/**
 * This file is part of KuBatsch.
 *   created on: 26.01.2011
 *   filename: PaddleChooser.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import at.kubatsch.model.Color;
import at.kubatsch.util.Event;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

/**
 * @author Daniel Kuschny (dku2375)
 *
 */
public class PaddleChooser extends JComponent
{
    /**
     * A unique serialization id.
     */
    private static final long serialVersionUID = -6217817080413853695L;
    private static final Font MAIN_FONT        = KuBatschTheme.MAIN_FONT
                                                       .deriveFont(KuBatschTheme.MAIN_FONT
                                                               .getSize() * 0.7f);
    private static final Font SMALL_FONT       = KuBatschTheme.MAIN_FONT
                                                       .deriveFont(KuBatschTheme.SMALL_FONT
                                                               .getSize() * 0.7f);

    private Color             _selectedColor;
    private String            _text;

    /**
     * Gets the selectedColor.
     * @return the selectedColor
     */
    public Color getSelectedColor()
    {
        return _selectedColor;
    }

    /**
     * Sets the selectedColor.
     * @param selectedColor the selectedColor to set
     */
    public void setSelectedColor(Color selectedColor)
    {
        _selectedColor = selectedColor;
        repaint();
    }

    /**
     * Gets the text.
     * @return the text
     */
    public String getText()
    {
        return _text;
    }

    /**
     * Sets the text.
     * @param text the text to set
     */
    public void setText(String text)
    {
        _text = text;
    }

    /**
     * Initializes a new instance of the {@link PaddleChooser} class.
     */
    public PaddleChooser(String text)
    {
        setDoubleBuffered(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter()
        {
            /**
             * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseClicked(MouseEvent e)
            {
                selectNextColor();
            }
        });
        setOpaque(false);
        _text = text;
        _selectedColor = Color.RED;
    }

    private void selectNextColor()
    {
        setSelectedColor(Color.getColor(_selectedColor.getIndex() + 1));
    }

    /**
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(95, 65);
    }

    /**
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        PaddlePainter.paint(g, new Rectangle(-5, 0, getWidth() + 10, 30), _selectedColor,
                1);

        g.setColor(java.awt.Color.white);
        Dimension size = SmallCapsUtility.calculateSize(this, _text, MAIN_FONT,
                SMALL_FONT);
        int x = (getWidth() - size.width) / 2;
        SmallCapsUtility.render(g, this, _text, MAIN_FONT, SMALL_FONT, x, 30);
    }

    private Event<EventArgs> _colorChanged = new Event<EventArgs>(this);

    /**
     * @param handler
     * @see at.kubatsch.util.Event#addHandler(at.kubatsch.util.IEventHandler)
     */
    public void addColorChangedHandler(IEventHandler<EventArgs> handler)
    {
        _colorChanged.addHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#removeHandler(at.kubatsch.util.IEventHandler)
     */
    public void removeColorChangedHandler(IEventHandler<EventArgs> handler)
    {
        _colorChanged.removeHandler(handler);
    }

}
