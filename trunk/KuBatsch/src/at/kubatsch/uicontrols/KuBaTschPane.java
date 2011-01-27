/**
 * This file is part of KuBatsch.
 *   created on: 18.01.2011
 *   filename: KuBaTschPane.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * A panel which only renders the client components without any background.
 * @author Daniel Kuschny (dku2375)
 *
 */
public class KuBaTschPane extends JPanel
{
    /**
     * A unique serialization id. 
     */
    private static final long serialVersionUID = -6772698860830183163L;
    
    /**
     * Initializes a new instance of the {@link KuBaTschPane} class.
     */
    public KuBaTschPane()
    {
        setOpaque(false);
    }

    /**
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        paintComponents(g);
    }
    
    /**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g)
    {
    }
}
