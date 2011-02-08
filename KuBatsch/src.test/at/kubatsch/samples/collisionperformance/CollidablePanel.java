/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: CollidablePanel.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.collisionperformance;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JPanel;

import at.kubatsch.client.controller.ClientConfigController;
import at.kubatsch.client.controller.KeyboardInputController;
import at.kubatsch.client.controller.MouseInputController;
import at.kubatsch.client.model.gear.KeyboardConfig;
import at.kubatsch.model.Ball;
import at.kubatsch.model.ICollidable;
import at.kubatsch.model.IDrawable;
import at.kubatsch.model.Paddle;
import at.kubatsch.model.PlayerHitArea;
import at.kubatsch.model.PlayerPosition;
import at.kubatsch.model.rules.PaddleReflectRule;
import at.kubatsch.samples.networktest.CollisionController;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

/**
 * A panel using a {@link CollisionController} to render a list of
 * {@link IDrawable}s.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class CollidablePanel extends JPanel
{
    /**
     * 
     */
    private static final long   serialVersionUID = -513853675084245981L;
    private CollisionController _controller;
    private Paddle              _paddle;
    private Paddle              _paddle2;
    private Paddle              _paddle3;
    private Paddle              _paddle4;

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

        // add paddles
        final PaddleReflectRule paddleReflectRule = new PaddleReflectRule();
        _paddle = new Paddle();
        _paddle.setPaddlePosition(PlayerPosition.NORTH);
        _paddle.addCollisionRule(paddleReflectRule);
        _paddle2 = new Paddle();
        _paddle2.setPaddlePosition(PlayerPosition.SOUTH);
        _paddle2.addCollisionRule(paddleReflectRule);
        _paddle3 = new Paddle();
        _paddle3.setPaddlePosition(PlayerPosition.EAST);
        _paddle3.addCollisionRule(paddleReflectRule);
        _paddle4 = new Paddle();
        _paddle4.setPaddlePosition(PlayerPosition.WEST);
        _paddle4.addCollisionRule(paddleReflectRule);
        _controller.addCollidable("PADDLE", _paddle);
        _controller.addCollidable("PADDLE", _paddle2);
        _controller.addCollidable("PADDLE", _paddle3);
        _controller.addCollidable("PADDLE", _paddle4);
        try
        {
            // enable mouse input
            final MouseInputController mouseInput = new MouseInputController(1f, this);
            mouseInput.addPositionChangedListener(new IEventHandler<EventArgs>()
            {
                @Override
                public void fired(Object sender, EventArgs e)
                {
                    _paddle.setRelativePosition(mouseInput.getCurrentPosition());
                    _paddle2.setRelativePosition(mouseInput.getCurrentPosition());
                    _paddle3.setRelativePosition(mouseInput.getCurrentPosition());
                    _paddle4.setRelativePosition(mouseInput.getCurrentPosition());
                    repaint();
                }
            });
            mouseInput.enable();
            
            // enable keyboard input
            final KeyboardInputController keyInput = new KeyboardInputController(
                    (KeyboardConfig) ClientConfigController.getInstance()
                            .getConfig().getControlType()[0]);
            keyInput.addPositionChangedListener(new IEventHandler<EventArgs>()
                    {
                @Override
                public void fired(Object sender, EventArgs e)
                {
                    _paddle.setRelativePosition(keyInput.getCurrentPosition());
                    _paddle2.setRelativePosition(keyInput.getCurrentPosition());
                    _paddle3.setRelativePosition(keyInput.getCurrentPosition());
                    _paddle4.setRelativePosition(keyInput.getCurrentPosition());
                    repaint();
                }
                    });
            keyInput.enable();
        }
        catch (AWTException e1)
        {
        }

        // add hitareas
        final HitPanelReflectRule hitAreaRule = new HitPanelReflectRule();
        PlayerHitArea area;

        area = new PlayerHitArea(PlayerPosition.SOUTH);
        area.addCollisionRule(hitAreaRule);
        _controller.addCollidable("HITAREA", area);

        area = new PlayerHitArea(PlayerPosition.NORTH);
        area.addCollisionRule(hitAreaRule);
        _controller.addCollidable("HITAREA", area);

        area = new PlayerHitArea(PlayerPosition.EAST);
        area.addCollisionRule(hitAreaRule);
        _controller.addCollidable("HITAREA", area);

        area = new PlayerHitArea(PlayerPosition.WEST);
        area.addCollisionRule(hitAreaRule);
        _controller.addCollidable("HITAREA", area);

        // create ball
        addCollidable(Ball.createRandom());

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
        for (Entry<String, List<ICollidable>> set : _controller
                .getCollidablesMapping().entrySet())
        {
            for (ICollidable collidable : set.getValue())
            {
                if (collidable instanceof IDrawable)
                {
                    ((IDrawable) collidable).paint(g, getSize());
                }
            }
        }
    }

    /**
     * @param collidable
     * @see at.kubatsch.samples.collisionperformance.CollisionController#addCollidable(at.kubatsch.model.ICollidable)
     */
    public void addCollidable(ICollidable collidable)
    {
        _controller.addCollidable("BALL", collidable);
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
