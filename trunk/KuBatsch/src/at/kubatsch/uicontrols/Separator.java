/**
 * This file is part of KuBatsch.
 *   created on: 15.01.2011
 *   filename: Separator.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.Dimension;

import javax.swing.JComponent;

/**
 * A control which can be used for creating spacing. 
 * @author Daniel Kuschny (dku2375)
 *
 */
public class Separator extends JComponent
{
    /**
     * 
     */
    private static final long serialVersionUID = 7513740709018333409L;
    private int _width;
    private int _height;
    
    /**
     * Initializes a new instance of the {@link Separator} class.
     * @param i
     * @param j
     */
    public Separator(int width, int height)
    {
        _width = width;
        _height = height;
    }
    
    /**
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(_width, _height);
    }
    
}
