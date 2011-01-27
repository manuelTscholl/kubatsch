/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: CollidablePanel.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.collisionperformance;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import at.kubatsch.model.ICollidable;
import at.kubatsch.model.IDrawable;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

/**
 * A panel using a {@link CollisionController} to render a list of {@link IDrawable}s. 
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class CollidablePanel extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = -513853675084245981L;
    private CollisionController _controller;
    
    /**
     * Initializes a new instance of the {@link CollidablePanel} class.
     */
    public CollidablePanel()
    {
        _controller = new CollisionController();
        _controller.addStateUpdatedHandler(new IEventHandler<EventArgs>()
        {
            @Override
            public void fired(Object sender, EventArgs e)
            {
                repaint();
            }
        });
        
        
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
     * @see javax.swing.JComponent#getMinimumSize()
     */
    @Override
    public Dimension getMinimumSize()
    {
        return new Dimension(800, 800);
    }

    /**
     * @see javax.swing.JComponent#getMaximumSize()
     */
    @Override
    public Dimension getMaximumSize()
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
        for (ICollidable collidable : _controller.getCollidables())
        {
            if(collidable instanceof IDrawable)
            {
                ((IDrawable)collidable).paint(g, getSize());
            }
        }
    }
    
    /**
     * @param collidable
     * @see at.kubatsch.samples.collisionperformance.CollisionController#addCollidable(at.kubatsch.model.ICollidable)
     */
    public void addCollidable(ICollidable collidable)
    {
        _controller.addCollidable(collidable);
    }

    /**
     * @param collidable
     * @see at.kubatsch.samples.collisionperformance.CollisionController#removeCollidable(at.kubatsch.model.ICollidable)
     */
    public void removeCollidable(ICollidable collidable)
    {
        _controller.removeCollidable(collidable);
    }

    /**
     * @see at.kubatsch.samples.collisionperformance.CollisionController#start()
     */
    public void start()
    {
        _controller.start();
    }

    /**
     * @see at.kubatsch.samples.collisionperformance.CollisionController#stop()
     */
    public void stop()
    {
        _controller.stop();
    }
    
}
