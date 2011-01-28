/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: CollidablePanel.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.networktest.client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JPanel;

import at.kubatsch.model.ICollidable;
import at.kubatsch.model.ICollisionRule;
import at.kubatsch.model.IDrawable;
import at.kubatsch.model.IUpdatable;
import at.kubatsch.samples.networktest.CollisionController;
import at.kubatsch.samples.networktest.server.NetworkControllerServer;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

/**
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class CollidablePanel extends JPanel
{
    private CollisionController _controller;
    private NetworkControllerClient _networkController;
    public CollidablePanel()
    {
        try
        {
            _networkController = new NetworkControllerClient("localhost",25000);
            _networkController.addStateUpdatedHandler(new IEventHandler<EventArgs>()
            {                
                /**
                 * @see at.kubatsch.util.IEventHandler#fired(java.lang.Object, at.kubatsch.util.EventArgs)
                 */
                @Override
                public void fired(Object sender, EventArgs e)
                {
                    _controller.setCollidables(_networkController.getCollidables());  
                }
            });
        }
        catch (IOException e1)
        {            
            e1.printStackTrace();
        }
        
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

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(800, 800);
    }

    @Override
    public Dimension getMinimumSize()
    {
        return new Dimension(800, 800);
    }

    @Override
    public Dimension getMaximumSize()
    {
        return new Dimension(800, 800);
    }
    
    /**
     * Draws all collidables which are listed in the controller via the method getCollidables()
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        for (Entry<String, List<ICollidable>>  group : _controller.getCollidablesMapping().entrySet())
        {
            for (ICollidable collidable : group.getValue())
            {
                if(collidable instanceof IDrawable)
                {
                    ((IDrawable)collidable).paint(g, getSize());
                }
            }

        }
    }
    
    

    /**
     * @param collidable
     * @see at.kubatsch.samples.collisionperformance.CollisionController#addCollidable(at.kubatsch.model.ICollidable)
     */
    public void addCollidable(String type,ICollidable collidable)
    {
        _controller.addCollidable(type, collidable);
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
     * 
     * @see at.kubatsch.samples.collisionperformance.CollisionController#start()
     */
    public void start()
    {
        _controller.start();
    }

    /**
     * 
     * @see at.kubatsch.samples.collisionperformance.CollisionController#stop()
     */
    public void stop()
    {
        _controller.stop();
    }
    
    
}
