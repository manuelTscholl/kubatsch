/**
 * This file is part of KuBatsch.
 *   created on: 18.01.2011
 *   filename: BloodTextBox.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * A single line textfield which can renders in bloody KuBaTsch style. 
 * @author Daniel Kuschny (dku2375)
 */
public class BloodTextfield extends JTextField
{
    /**
     * A unique serialization id.
     */
    private static final long serialVersionUID = -5347473108963296326L;

    /**
     * Initializes a new instance of the {@link BloodTextfield} class.
     */
    public BloodTextfield()
    {
        setForeground(KuBatschTheme.TEXTBOX_FOREGROUND[0]);
        setOpaque(false);
        setBorder(new EmptyBorder(3,3,3,3));
        setHorizontalAlignment(JTextField.CENTER);
    }
    
    
    /**
     * Initializes a new instance of the {@link BloodTextfield} class.
     */
    public BloodTextfield(String text)
    {
        this();
        setText(text);
    }
    
    
    
    /**
     * @see javax.swing.JTextField#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize()
    {
        Dimension d = super.getPreferredSize();
        return new Dimension((int) d.getWidth(), KuBatschTheme.TEXTBOX_HEIGHT);
    }

    /**
     * @see javax.swing.JComponent#getMinimumSize()
     */
    @Override
    public Dimension getMinimumSize()
    {
        Dimension d = super.getMinimumSize();
        return new Dimension((int) d.getWidth(), KuBatschTheme.TEXTBOX_HEIGHT);
    }

    /**
     * @see javax.swing.JComponent#getMaximumSize()
     */
    @Override
    public Dimension getMaximumSize()
    {
        Dimension d = super.getMaximumSize();
        return new Dimension((int) d.getWidth(), KuBatschTheme.TEXTBOX_HEIGHT);
    }

    /**
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        paintComponents(g);
        paintComponent(g);
    }
    
    /**
     * @see javax.swing.text.JTextComponent#setEditable(boolean)
     */
    @Override
    public void setEditable(boolean b)
    {
        super.setEditable(b);
        setForeground(b ? KuBatschTheme.TEXTBOX_FOREGROUND[0] : KuBatschTheme.TEXTBOX_FOREGROUND[1]);
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

        Image img = isEditable() ? KuBatschTheme.TEXTBOX_BACKGROUND[0]
                : KuBatschTheme.TEXTBOX_BACKGROUND[1];
        g.drawImage(img, 0, 0, img.getWidth(this), img.getHeight(this), this);
        super.paintComponent(g);
    }
}
