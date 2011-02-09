/**
 * author: Manuel Tscholl(mts3970)
 * created on: 03.02.2011
 * filename: MainController.java
 * project: KuBaTsch
 */
package at.kubatsch.server.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import at.kubatsch.model.Ball;
import at.kubatsch.model.GameState;
import at.kubatsch.model.Player;
import at.kubatsch.model.message.ConnectAsPlayerMessage;
import at.kubatsch.model.message.INetworkMessage;
import at.kubatsch.model.message.PaddleMovedMessage;
import at.kubatsch.model.message.ServerInfoMessage;
import at.kubatsch.server.model.IGameRule;
//import at.kubatsch.server.model.NoPlayerSurvivingRule;
import at.kubatsch.server.model.OnePlayerSurvivingRule;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.GameControllerBase;
import at.kubatsch.util.IEventHandler;
import at.kubatsch.model.message.UpdateGameStateMessage;


/**
 * @author Manuel Tscholl (mts3970)
 * 
 */
public class ServerGameController extends GameControllerBase
{
    private static Logger LOGGER = Logger.getLogger(ServerGameController.class);
    
    /**
     * Send all 50 gamestates to the client. 
     */
    private static int INTERVAL = 50;
    
    private NetworkControllerServer _networkServer;
    private boolean                 _isRoundRunning;
    private List<IGameRule>         _roundRules;

    /**
     * 
     * Initializes a new instance of the {@link ServerGameController} class.
     * @param portToListen
     * @throws IOException
     */
    private ServerGameController(int portToListen) throws IOException
    {
        super();
        _roundRules = new ArrayList<IGameRule>();
        _roundRules.add(new OnePlayerSurvivingRule());
//        _roundRules.add(new NoPlayerSurvivingRule());
        
        setStateUpdateInterval(INTERVAL);

        _networkServer = new NetworkControllerServer(portToListen);
        // if we are on the next timeslice we need to update all clients
        _stateUpdated.addHandler(new IEventHandler<EventArgs>()
        {
            @Override
            public void fired(Object sender, EventArgs e)
            {
                // lock state and send it to all clients
                GameState state = getCurrentGameState();
                synchronized (state)
                {
                    _networkServer
                            .sendMessageToClients(new UpdateGameStateMessage(
                                    state));
                }
            }
        });

        _networkServer
                .addClientMessageListener(new IEventHandler<NetworkMessageEventArgs>()
                {

                    @Override
                    public void fired(Object sender, NetworkMessageEventArgs e)
                    {
                        processMessage(e.getClientUid(), e.getMessage());
                    }
                });

        _networkServer
                .addClientDisconnectedListener(new IEventHandler<NetworkGameClientEventArgs>()
                {

                    @Override
                    public void fired(Object sender,
                            NetworkGameClientEventArgs e)
                    {
                        removeClient(e.getClientUid());
                    }
                });
    }

    /**
     * @param clientUid
     */
    protected void removeClient(int clientUid)
    {
        if (getCurrentGameState() == null)
        {
            return;
        }
        LOGGER.info(String.format("Client %d left the server", clientUid));
//        System.out.printf("Client %d left the server%n", clientUid);
       
        // remove player from gamestate
        int index = getCurrentGameState().getPlayerIndex(clientUid);
        if (index >= 0)
        {
            getCurrentGameState().getPlayer()[index]
                    .reset(getCurrentGameState().getPlayer()[index]
                            .getPosition());
            for (IGameRule rule : _roundRules)
            {
                rule.setupPlayer(getCurrentGameState().getPlayer()[index]);
            }
        }
    }

    /**
     * @param clientUid
     * @param message
     */
    protected void processMessage(int clientUid, INetworkMessage message)
    {
        // receive server info
        if (message.getMessageId().equalsIgnoreCase(
                ServerInfoMessage.MESSAGE_ID))
        {
            int players = 0;
            if (getCurrentGameState() != null)
            {
                players = getCurrentGameState().getPlayerCount();
            }
            LOGGER.info(String.format("Client requested server info: %d players", players));
            _networkServer.sendMessageToClients(new ServerInfoMessage(players));
        }
        // player wants to connect as player
        else if (message.getMessageId().equalsIgnoreCase(
                ConnectAsPlayerMessage.MESSAGE_ID))
        {
            ConnectAsPlayerMessage connectMessage = (ConnectAsPlayerMessage) message;
            acceptClient(clientUid, connectMessage.getName());
        }
        // a player sending a game message
        else
        {

            int index = getCurrentGameState().getPlayerIndex(clientUid);
            // an unregistered player?
            if (index < 0)
            {
                LOGGER.warn(String.format("Got a %s message from an unregistered player %d", message.getMessageId(), clientUid));
                
//                System.out.printf(
//                        "Got a %s message from an unregistered player %d%n",
//                        message.getMessageId(), clientUid);
                return;
            }

            Player p = getCurrentGameState().getPlayer()[index];

            if (message.getMessageId() == PaddleMovedMessage.MESSAGE_ID)
            {
                PaddleMovedMessage paddleMsg = (PaddleMovedMessage) message;

                p.setPaddlePosition(paddleMsg.getNewPosition());

                LOGGER.debug(String.format("got paddlePosition %f from %d", paddleMsg.getNewPosition(), clientUid));
                _networkServer.sendMessageToClients(new PaddleMovedMessage(clientUid, paddleMsg.getNewPosition()));
//                _stateUpdated.fireEvent(EventArgs.Empty);
            }
        }
    }

    /**
     * @see at.kubatsch.util.GameControllerBase#setCurrentGameState(at.kubatsch.model.GameState)
     */
    @Override
    public synchronized void setCurrentGameState(GameState currentGameState)
    {
        super.setCurrentGameState(currentGameState);
        if (currentGameState == null)
            return;
        for (int i = 0; i < currentGameState.getPlayer().length; i++)
        {
            for (IGameRule rule : _roundRules)
            {
                rule.setupPlayer(currentGameState.getPlayer()[i]);
            }
        }
    }

    /**
     * Only one Instance of this class can be created
     * @return the existing or the new Instance of MainController
     * @throws IOException
     */
    public static ServerGameController getInstance(int portToListen)
            throws IOException
    {
        if (_mainController == null)
        {
            return new ServerGameController(portToListen);
        }

        return (ServerGameController) _mainController;
    }

    /**
     * @param client
     */
    protected void acceptClient(int uid, String name)
    {
        if (getCurrentGameState().getPlayerIndex(uid) >= 0)
            return; // already registered
        // find a free playing position for him
        
        boolean acceptSuccess = false;
        for (int i = 0; i < getCurrentGameState().getPlayer().length; i++)
        {
            if (getCurrentGameState().getPlayer()[i].getUid() == -1) // free
                                                                     // space?
            {
                LOGGER.info(String.format("Registered player %d on position %d",  uid,
                        i));
                
//                System.out.printf("Registered player %d on position %d%n", uid,
//                        i);
                getCurrentGameState().getPlayer()[i].setName(name);
                getCurrentGameState().getPlayer()[i].setUid(uid);
                acceptSuccess = true;
                break;
            }
        }
        
        // server is full
        if(!acceptSuccess)
        {
            _networkServer.kickClient(uid);
        }
        
        _stateUpdated.fireEvent(EventArgs.Empty);
    }

    /**
     * @see at.kubatsch.util.GameControllerBase#runGame()
     */
    @Override
    protected void runGame()
    {
        while (isRunning())
        {
            // do we have enough players on the server? if not -> stop the round

            // check rules if game is finished or we can start a roudn
            boolean roundFinished = true;
            boolean canStartRound = true;
            for (IGameRule rule : _roundRules)
            {
                if (!rule.isFinished(getCurrentGameState()))
                {
                    roundFinished = false;
                }
                if (!rule.canStartRound(getCurrentGameState()))
                {
                    canStartRound = false;
                }
            }

            // is round finished?
            if (_isRoundRunning && roundFinished)
            {
                finishRound();
            }
            // is game not running and we can start a round?
            else if (!_isRoundRunning && canStartRound)
            {
                startRound();
            }
            // is game running and we are on a unplayable state -> finish too
            else if (_isRoundRunning && !canStartRound)
            {
                finishRound();
            }
            // cannot start round?
            else if (!_isRoundRunning && !canStartRound)
            {
                getCurrentGameState().getStatusLbl().setText(
                        "waiting for other players");
                _stateUpdated.fireEvent(EventArgs.Empty);
            }

            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
            }
        }
    }

    /**
     * 
     */
    private void startRound()
    {
        _isRoundRunning = true;
        for (IGameRule rule : _roundRules)
        {
            rule.startRound(getCurrentGameState());
        }

        try
        {
            // 3
            getCurrentGameState().getStatusLbl().setText("3");
            _stateUpdated.fireEvent(EventArgs.Empty);
            Thread.sleep(1000);
            // 2
            getCurrentGameState().getStatusLbl().setText("2");
            _stateUpdated.fireEvent(EventArgs.Empty);
            Thread.sleep(1000);
            // 1
            getCurrentGameState().getStatusLbl().setText("1");
            _stateUpdated.fireEvent(EventArgs.Empty);
            Thread.sleep(1000);
            getCurrentGameState().getStatusLbl().setText("");
            // add main ball to the game to start game
            getCurrentGameState().getBalls().add(createDefaultBall());
            _stateUpdated.fireEvent(EventArgs.Empty);
        }
        catch (InterruptedException e)
        {
        }
    }

    /**
     * @return
     */
    private Ball createDefaultBall()
    {
        Ball ball = Ball.createRandom();
        for (IGameRule rule : _roundRules)
        {
            rule.setupBall(ball);
        }
        return ball;
    }

    /**
     * 
     */
    private void finishRound()
    {
        _isRoundRunning = false;
        for (IGameRule rule : _roundRules)
        {
            rule.finishRound(getCurrentGameState());
        }
    }

}
