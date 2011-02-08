/**
 * author: Daniel Kuschny (dku2375)
 * created on: 28.01.2011
 * filename: GameView.java
 * project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import at.kubatsch.client.controller.ClientConfigController;
import at.kubatsch.client.controller.ClientGameController;
import at.kubatsch.client.controller.MouseInputController;
import at.kubatsch.client.controller.NetworkControllerClient;
import at.kubatsch.client.controller.ViewController;
import at.kubatsch.client.model.gear.MouseConfig;
import at.kubatsch.model.Ball;
import at.kubatsch.model.GameState;
import at.kubatsch.model.Player;
import at.kubatsch.model.PlayerPosition;
import at.kubatsch.model.SpecialItem;
import at.kubatsch.model.message.ConnectAsPlayerMessage;
import at.kubatsch.model.message.INetworkMessage;
import at.kubatsch.model.message.PaddleMovedMessage;
import at.kubatsch.model.message.SetUniqueIdMessage;
import at.kubatsch.model.message.UpdateGameStateMessage;
import at.kubatsch.model.rules.PaddleReflectRule;
import at.kubatsch.server.controller.NetworkGameClientEventArgs;
import at.kubatsch.server.controller.NetworkMessageEventArgs;
import at.kubatsch.uicontrols.BloodPanel;
import at.kubatsch.uicontrols.KuBatschTheme;
import at.kubatsch.uicontrols.SmallCapsUtility;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

/**
 * @author Daniel Kuschny (dku2375)
 * 
 */
public final class GameView extends BloodPanel implements INotifiableView
{
    
    private static Logger LOGGER = Logger.getLogger(GameView.class);
    
    /**
     * The view-id used by this panel
     */
    public static final String      PANEL_ID         = "game";
    private static final long       serialVersionUID = 4868195256564051158L;

    private NetworkControllerClient _networkController;
    private MouseInputController    _inputController;

    /**
     * @see at.kubatsch.uicontrols.BloodPanel#getBloodOpacity()
     */
    @Override
    public float getBloodOpacity()
    {
        Player p = ClientGameController.getInstance().getCurrentPlayer();
        if (p == null)
            return 1;
        return 1 - p.getHealth();
    }

    /**
     * Initializes a new instance of the {@link GameView} class.
     */
    public GameView()
    {
        MouseConfig c = (MouseConfig) ClientConfigController.getInstance().getConfig()
                .getControlType()[1];
        try
        {
            _inputController = new MouseInputController((float) c.getSensitivity(), this);
            _inputController.addPositionChangedListener(new IEventHandler<EventArgs>()
            {

                        @Override
                        public void fired(Object sender, EventArgs e)
                        {
                            if(_networkController!=null)
                            _networkController
                                    .addToMessageStack(new PaddleMovedMessage(
                                            _inputController
                                                    .getCurrentPosition()));
                        }
                    });
        }
        catch (AWTException e1)
        {
            // error registering: robot
        }
        setDoubleBuffered(true);
        setFocusable(true);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "disconnect");
        getActionMap().put("disconnect", new AbstractAction()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e)
            {
                disconnect();
            }
        });

        ClientGameController gameController = ClientGameController.getInstance();
        gameController.addStateUpdatedListener(new IEventHandler<EventArgs>()
        {
            @Override
            public void fired(Object sender, EventArgs e)
            {
                repaint();
            }
        });
        gameController.start();
        gameController.suspendUpdating();
    }

    public void connect(String server, int port) throws IOException
    {
        if (_networkController != null)
            disconnect();
//        System.out.println("Connect to Server");
        LOGGER.info("Connect to Server");
        _networkController = new NetworkControllerClient(server, port);

        _networkController
                .addMessageReceivedListener(new IEventHandler<NetworkMessageEventArgs>()
                {
                    @Override
                    public void fired(Object sender, NetworkMessageEventArgs e)
                    {
                        handleMessage(e.getMessage());
                    }
                });

        _networkController
                .addConnectionLostListener(new IEventHandler<NetworkGameClientEventArgs>()
                {
                    @Override
                    public void fired(Object sender, NetworkGameClientEventArgs e)
                    {
                        ClientGameController.getInstance().suspendUpdating();
                        ViewController.getInstance().switchToView(MenuView.PANEL_ID);
                    }
                });
        // will be sent after connect message
        // will be sent first
        _networkController.addToMessageStack(new ConnectAsPlayerMessage(
                ClientConfigController.getInstance().getConfig().getName()));
        _networkController.startWork();
    }

    /**
     * @param message
     */
    /**
     * @param message
     */
    protected void handleMessage(INetworkMessage message)
    {
        if (message.getMessageId().equalsIgnoreCase(SetUniqueIdMessage.MESSAGE_ID))
        {
            SetUniqueIdMessage uniqueIdMessage = (SetUniqueIdMessage) message;
            _networkController.setClientUid(uniqueIdMessage.getUniqueId());
            ClientGameController.getInstance()
                    .setClientUid(uniqueIdMessage.getUniqueId());
        }
        else if (message.getMessageId().equalsIgnoreCase(
                UpdateGameStateMessage.MESSAGE_ID))
        {
            UpdateGameStateMessage updateMessage = (UpdateGameStateMessage) message;

            GameState s = updateMessage.getGameState();
            ClientGameController.getInstance().setCurrentGameState(s);
        }
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
        if (ClientGameController.getInstance().getCurrentGameState() == null)
            return;
        if (_networkController==null || _networkController.getClientUid() == 0)
            return; // do not know who am I
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        GameState s = ClientGameController.getInstance().getCurrentGameState();

        // rotate the gameboard so we see us on bottom
        int index = s.getPlayerIndex(ClientGameController.getInstance().getClientUid());
        if(index < 0) return;
        PlayerPosition position = PlayerPosition.getPositionForIndex(index);

        AffineTransform t = ((Graphics2D) g).getTransform();
        int rotation = PlayerPosition.getRotationForPosition(position);
        if (rotation > 0)
        {
            ((Graphics2D) g).transform(AffineTransform.getRotateInstance(
                    (double) rotation * PaddleReflectRule.GRAD_RAD_FACTOR,
                    getSize().width / 2, getSize().height / 2));
        }

        
        // draw players
        for (int i = 0; i < s.getPlayer().length; i++) 
        {
            AffineTransform t2 = ((Graphics2D) g).getTransform();
            Player player = s.getPlayer()[i];
            
            
            rotation = 0;
            switch (player.getPosition())
            {
                case NORTH:
                    rotation = 180;
                    break;
                case WEST:
                    rotation = 90;
                    break;
                case EAST:
                    rotation = 270;
                    break;
            }
            if (rotation > 0)
            {
                ((Graphics2D) g).transform(AffineTransform.getRotateInstance(
                        (rotation * PaddleReflectRule.GRAD_RAD_FACTOR),
                        getSize().width / 2, getSize().height / 2));
            }

            if (player.getUid() == -1 || !player.isAlive()) // no player,
                                                            // or dead
            {
                player.getWall().paint(g, getSize());
            }
            else
            // player alive,
            {
                if (player.getPosition() == position)
                {
                    player.setPaddlePosition(_inputController
                            .getCurrentPosition());
                }
                player.getPaddle().paint(g, getSize());
            }

            

            Dimension nameSize = SmallCapsUtility.calculateSize(this,
                    player.getName(), KuBatschTheme.MAIN_FONT,
                    KuBatschTheme.SMALL_FONT);
            String points = String.format("%d",player.getWins());
            Dimension pointsSize = SmallCapsUtility.calculateSize(this,
                    points, KuBatschTheme.MAIN_FONT,
                    KuBatschTheme.SMALL_FONT);
            
            Image deadPlayerImage = KuBatschTheme.DEAD_PLAYER;

            int nameX = (getSize().width - nameSize.width) / 2;
            int nameY = 0;
            int pointsX = (getSize().width - pointsSize.width) / 2;
            int pointsY = 0;
            int bloodX = (getSize().width - deadPlayerImage.getWidth(this)) / 2;
            int bloodY = 0;
            
            
            switch (ClientGameController.getInstance().getPositionMappings()[i])
            {
                case EAST:
                case WEST:
                case NORTH:
                    nameY = 270;
                    pointsY = 325;
                    bloodY = 235;
                    // rotate 180° to draw hud
                    ((Graphics2D) g).transform(AffineTransform.getRotateInstance(
                            (180 * PaddleReflectRule.GRAD_RAD_FACTOR),
                            getSize().width / 2, getSize().height / 2));
                    break;
                case SOUTH:
                    nameY = 495;
                    pointsY = 440;
                    bloodY = 455;
                    break;
            }

            Graphics2D g2d = (Graphics2D) g;
            Composite c = g2d.getComposite();
            AlphaComposite alphaComposite = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, (float)ClientConfigController.getInstance().getConfig().getHudAlpha());
            g2d.setComposite(alphaComposite);
            
            if(!player.isAlive())
            {
                g.drawImage(deadPlayerImage, bloodX, bloodY, null);
            }
            
            g.setColor(ClientGameController.getInstance().getColorForIndex(i).getAwtColor());
            SmallCapsUtility.render(g, this, player.getName(),
                    KuBatschTheme.MAIN_FONT, KuBatschTheme.SMALL_FONT, nameX, nameY);
            SmallCapsUtility.render(g, this, points,
                    KuBatschTheme.MAIN_FONT, KuBatschTheme.SMALL_FONT, pointsX, pointsY);

            g2d.setComposite(c);
            
            

            ((Graphics2D) g).setTransform(t2);
        }

        // draw balls
        for (Ball ball : s.getBalls())
        {
            ball.paint(g, getSize());
        }

        // special items
        for (SpecialItem item : s.getSpecialItems())
        {
            item.paint(g, getSize());
        }

        ((Graphics2D) g).setTransform(t);
        
        // draw hud image 
        Graphics2D g2d = (Graphics2D) g;
        Composite c = g2d.getComposite();
        AlphaComposite alphaComposite = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, (float)ClientConfigController.getInstance().getConfig().getHudAlpha());
        g2d.setComposite(alphaComposite);
        
        Image hudImage = KuBatschTheme.HUD_IMAGE;
        int hudW = hudImage.getWidth(this);
        int hudH = hudImage.getHeight(this);
        int hudX = (getSize().width - hudW)/2;
        int hudY = (getSize().height - hudH)/2;
       
        g.drawImage(hudImage, hudX, hudY, null);
        
        g2d.setComposite(c);

        // draw status label
        s.getStatusLbl().paint(g, getSize());
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
        ClientGameController.getInstance().suspendUpdating();
    }

    /**
     * @see at.kubatsch.client.view.INotifiableView#viewDisplaying()
     */
    @Override
    public void viewDisplaying()
    {
        ClientGameController.getInstance().startUpdating();
        requestFocusInWindow();
        _inputController.enable();
    }

    /**
     * @see at.kubatsch.client.view.INotifiableView#viewHidding()
     */
    @Override
    public void viewHidding()
    {
        disconnect();
        _inputController.disable();
    }

    /**
     * 
     */
    public int getClientId()
    {
        return _networkController == null ? -1 : _networkController.getClientUid();
    }
}
