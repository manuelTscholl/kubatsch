/**
 * This file is part of KuBatsch.
 *   created on: 26.01.2011
 *   filename: PaddlePaintPanel.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.paddledrawing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

import at.kubatsch.uicontrols.PaddlePainter;

/**
 * This paddle enables drawing of a paddle.
 * @author Daniel Kuschny (dku2375)
 */
public class PaddlePaintPanel extends JComponent
{
    /**
     * A unique serialization id. 
     */
    private static final long serialVersionUID = -147618577928170853L;
    private int                     _paddleWidth;
    private float                   _health;
    private at.kubatsch.model.Color _paddleColor;

    /**
     * Gets the health of the paddle.
     * @return the health from 0-1 describing the health as percentage.
     */
    public float getHealth()
    {
        return _health;
    }

    /**
     * Sets the health as percentage.
     * @param health the health value to set
     */
    public void setHealth(float health)
    {
        _health = Math.min(1, Math.max(0, health));
        repaint();
    }

    /**
     * Gets the current color of the paddle.
     * @return the current color of the paddle
     */
    public at.kubatsch.model.Color getPaddleColor()
    {
        return _paddleColor;
    }

    /**
     * Sets the current color of the paddle.
     * @param paddleColor the current color of the paddle
     */
    public void setPaddleColor(at.kubatsch.model.Color paddleColor)
    {
        _paddleColor = paddleColor;
        repaint();
    }

    /**
     * Gets the width of the paddle.
     * @return the width of the paddle.
     */
    public int getPaddleWidth()
    {
        return _paddleWidth;
    }

    /**
     * Sets the width of the paddle.
     * @param paddleWidth the width of the paddle.
     */
    public void setPaddleWidth(int paddleWidth)
    {
        _paddleWidth = paddleWidth;
        repaint();
    }

    /**
     * Initializes a new instance of the {@link PaddlePaintPanel} class.
     */
    public PaddlePaintPanel()
    {
        _paddleWidth = 150;
        _health = 0.5f;
        _paddleColor = at.kubatsch.model.Color.RED;
        setDoubleBuffered(true);
    }

    /**
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(800, 800);
    }

    /**
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        if (_paddleWidth < 30)
            return;
        
        // center on x, and y
        int paddleX = ((getWidth() - _paddleWidth) / 2);
        int paddleY = ((getHeight() - 30) / 2);

        // paint paddle
        PaddlePainter.paint(g,
                new Rectangle(paddleX, paddleY, _paddleWidth, 30),
                _paddleColor, _health);
    }
}
