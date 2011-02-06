/**
 * author: Daniel Kuschny (dku2375)
 * created on: 28.01.2011
 * filename: GameView.java
 * project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

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
import at.kubatsch.server.controller.NetworkGameClientEventArgs;
import at.kubatsch.server.controller.NetworkMessageEventArgs;
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

    private NetworkControllerClient _networkController;
    private MouseInputController    _inputController;

    /**
     * Initializes a new instance of the {@link GameView} class.
     */
    public GameView()
    {
        setBloodOpacity(1);

        MouseConfig c = (MouseConfig) ClientConfigController.getInstance()
                .getConfig().getControlType()[1];
        try
        {
            _inputController = new MouseInputController(
                    (float) c.getSensitivity(), this);
            _inputController
                    .addPositionChangedListener(new IEventHandler<EventArgs>()
                    {

                        @Override
                        public void fired(Object sender, EventArgs e)
                        {
                            System.out.println("adding paddle moved message");
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
        System.out.println("Connect to Server");
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
                    public void fired(Object sender,
                            NetworkGameClientEventArgs e)
                    {
                        ClientGameController.getInstance().suspendUpdating();
                        ViewController.getInstance().switchToView(
                                MenuView.PANEL_ID);
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
        if (message.getMessageId().equalsIgnoreCase(
                SetUniqueIdMessage.MESSAGE_ID))
        {
            SetUniqueIdMessage uniqueIdMessage = (SetUniqueIdMessage) message;
            _networkController.setClientUid(uniqueIdMessage.getUniqueId());
        }
        else if (message.getMessageId().equalsIgnoreCase(
                UpdateGameStateMessage.MESSAGE_ID))
        {
            System.out.println("GameState updated on client");
            UpdateGameStateMessage updateMessage = (UpdateGameStateMessage) message;

            // optimize view for clent:
            GameState s = updateMessage.getGameState();

            // search index of myself.
//            int index = s.getPlayerIndex(_networkController.getClientUid());
//            System.out.printf("Found myself on position %d, rotate gameboard%n", index);

//            // server stores 0->south, 1->north, 2->east, 3->west
//            // we need to rotate this map so we are on south
//
//            Player[] players = s.getPlayer();
//            Player[] newPlayers = new Player[players.length];
//
//            if (index == 0) // south
//            { // no rotation
//                newPlayers = players;
//            }
//            else if (index == 1) // north
//            {
//                // rotate 180°
//                newPlayers[0] = players[1];
//                newPlayers[1] = players[0];
//                newPlayers[2] = players[3];
//                newPlayers[3] = players[2];
//            }
//            else if (index == 2) // west
//            {
//                newPlayers[0] = players[2];
//                newPlayers[1] = players[3];
//                newPlayers[2] = players[1];
//                newPlayers[3] = players[0];
//            }
//            else // east
//            {
//                newPlayers[0] = players[3];
//                newPlayers[1] = players[2];
//                newPlayers[2] = players[0];
//                newPlayers[3] = players[1];
//            }
//
//            for (int i = 0; i < newPlayers.length; i++)
//            {
//                newPlayers[i].setPosition(PlayerPosition
//                        .getPositionForIndex(i));
//            }
//            s.setPlayer(newPlayers);
            
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
        if (_networkController.getClientUid() == 0)
            return; // do not know who am I
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        AffineTransform t = ((Graphics2D) g).getTransform();

        GameState s = ClientGameController.getInstance().getCurrentGameState();
        
        
        // rotate the gameboard so we see us on bottom
        int rotation = 0;
        int index = s.getPlayerIndex(_networkController.getClientUid());
        PlayerPosition position = PlayerPosition.getPositionForIndex(index);
        switch (position)
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
                    (double) (rotation * Math.PI) / 180, getSize().width / 2,
                    getSize().height / 2));
        }
        
        // draw players
        for (int i = 0; i < s.getPlayer().length; i++)
        {
            Player player = s.getPlayer()[i];
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
                    player.setPaddlePosition(_inputController.getCurrentPosition());
                }
                player.getPaddle().paint(g, getSize());
            }
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
}
