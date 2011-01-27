/**
 * This file is part of KuBatsch.
 *   created on: 15.01.2011
 *   filename: ImagePanel.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

/**
 * A control which renders an image.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class ImageBox extends JComponent
{
    /**
     * A unique serialization id.
     */
    private static final long serialVersionUID = 6584851343067704740L;
    private Image             _image;

    /**
     * Gets the image to display.
     * @return the image
     */
    public Image getImage()
    {
        return _image;
    }

    /**
     * Sets the image to display.
     * @param image the image to set
     */
    public void setImage(Image image)
    {
        _image = image;
    }

    /**
     * Initializes a new instance of the {@link ImageBox} class.
     */
    public ImageBox()
    {
        this(null);
    }

    /**
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize()
    {
        if (_image != null)
        {
            return new Dimension(_image.getWidth(this), _image.getHeight(this));
        }
        return super.getPreferredSize();
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
     * @see javax.swing.JComponent#getMaximumSize()
     */
    @Override
    public Dimension getMaximumSize()
    {
        return getPreferredSize();
    }

    /**
     * Initializes a new instance of the {@link ImageBox} class.
     * @param image the image to display.
     */
    public ImageBox(Image image)
    {
        setImage(image);
        // setOpaque(false);
    }

    /**
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        if (_image != null)
        {
            g.drawImage(_image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
