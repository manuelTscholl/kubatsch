/**
 * This file is part of KuBatsch.
 *   created on: 26.01.2011
 *   filename: BloodSlider.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 * This slider is rendered in the bloody KuBaTsch style.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class BloodSlider extends JSlider
{
    /**
     * A unique serialization id.
     */
    private static final long serialVersionUID = 6873742575147772430L;

    /**
     * Initializes a new instance of the {@link BloodSlider} class.
     */
    public BloodSlider()
    {
        setDoubleBuffered(true);
        setOpaque(false);
        setUI(new BloodSliderUI(this));
    }

    /**
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize()
    {
        return KuBatschTheme.SLIDER_SIZE;
    }

    /**
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        paintComponent(g);
    }

    /**
     * A bloody UI slider implementation.
     * @author Daniel Kuschny (dku2375)
     * 
     */
    private static class BloodSliderUI extends BasicSliderUI
    {
        private JSlider _slider;

        /**
         * Initializes a new instance of the {@link BloodSliderUI} class.
         * @param b the slider
         */
        public BloodSliderUI(JSlider b)
        {
            super(b);
            _slider = b;
        }

        /**
         * @see javax.swing.plaf.basic.BasicSliderUI#getThumbSize()
         */
        @Override
        protected Dimension getThumbSize()
        {
            return new Dimension(KuBatschTheme.SLIDER_THUMB_SIZE.width,
                    _slider.getHeight());
        }

        /**
         * @see javax.swing.plaf.basic.BasicSliderUI#paintTrack(java.awt.Graphics)
         */
        @Override
        public void paintTrack(Graphics g)
        {
            int h = KuBatschTheme.SLIDER_BACKGROUND.getHeight(_slider);
            g.drawImage(KuBatschTheme.SLIDER_BACKGROUND, 0,
                    (_slider.getHeight() - h - 5), null);
        }

        /**
         * @see javax.swing.plaf.basic.BasicSliderUI#paintFocus(java.awt.Graphics)
         */
        @Override
        public void paintFocus(Graphics g)
        {}

        /**
         * @see javax.swing.plaf.basic.BasicSliderUI#paintThumb(java.awt.Graphics)
         */
        @Override
        public void paintThumb(Graphics g)
        {
            Rectangle knobBounds = thumbRect;
            int y = knobBounds.y + 5;

            g.translate(knobBounds.x, y);
            g.drawImage(KuBatschTheme.SLIDER_THUMB, 0, 0, null);
            g.translate(-knobBounds.x, -y);
        }
    }
}
