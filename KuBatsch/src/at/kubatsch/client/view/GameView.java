/**
 * author: Daniel Kuschny (dku2375)
 * created on: 28.01.2011
 * filename: GameView.java
 * project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import at.kubatsch.client.controller.ViewController;
import at.kubatsch.model.ICollidable;
import at.kubatsch.model.IDrawable;
import at.kubatsch.samples.networktest.CollisionController;
import at.kubatsch.samples.networktest.client.NetworkControllerClient;
import at.kubatsch.uicontrols.BloodPanel;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

/**
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class GameView extends BloodPanel implements INotifiableView
{
    /**
     * The view-id used by this panel
     */
    public static final String      PANEL_ID         = "game";

    /**
     * 
     */
    private static final long       serialVersionUID = 4868195256564051158L;

    private CollisionController     _controller;
    private NetworkControllerClient _networkController;

    /**
     * Initializes a new instance of the {@link GameView} class.
     */
    public GameView()
    {
        setBloodOpacity(1);
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
        setFocusable(true);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "disconnect");
        getActionMap().put("disconnect", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                disconnect();
            }
        });
    }

    public void connect(String server, int port) throws IOException
    {
        if (_networkController != null)
            disconnect();
        System.out.println("Connect to Server");
        _networkController = new NetworkControllerClient(server, port);
        _networkController
                .addStateUpdatedHandler(new IEventHandler<EventArgs>()
                {
                    @Override
                    public void fired(Object sender, EventArgs e)
                    {
                        _controller.setCollidables(_networkController
                                .getCollidables());
                    }
                });
        _networkController.addDisconnectedListener(new IEventHandler<EventArgs>()
        {
            @Override
            public void fired(Object sender, EventArgs e)
            {
                _controller.stop();
                ViewController.getInstance().switchToView(MenuView.PANEL_ID);
            }
        });
    }

    /**
     * Draws all collidables which are listed in the controller via the method
     * getCollidables()
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        for (Entry<String, List<ICollidable>> group : _controller
                .getCollidablesMapping().entrySet())
        {
            for (ICollidable collidable : group.getValue())
            {
                if (collidable instanceof IDrawable)
                {
                    ((IDrawable) collidable).paint(g, getSize());
                }
            }

        }
    }

    /**
     * 
     */
    private void disconnect()
    {
        if (_networkController != null)
        {
            NetworkControllerClient nw = _networkController;
            _networkController = null;
            nw.disconnect();
        }
        _controller.reset();
        _controller.stop();
    }

    /**
     * @see at.kubatsch.client.view.INotifiableView#viewDisplaying()
     */
    @Override
    public void viewDisplaying()
    {
        _controller.start();
        requestFocusInWindow();
    }

    /**
     * @see at.kubatsch.client.view.INotifiableView#viewHidding()
     */
    @Override
    public void viewHidding()
    {
        disconnect();
    }
}
