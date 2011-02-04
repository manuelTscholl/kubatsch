/**
 * author: Manuel Tscholl(mts3970)
 * created on: 03.02.2011
 * filename: MainController.java
 * project: KuBaTsch
 */
package at.kubatsch.server.controller;

import java.io.IOException;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import at.kubatsch.model.Ball;
import at.kubatsch.model.GameState;
import at.kubatsch.model.ICollidable;
import at.kubatsch.model.ICollisionRule;
import at.kubatsch.model.IUpdatable;
import at.kubatsch.model.Paddle;
import at.kubatsch.model.Player;
import at.kubatsch.model.SpecialItem;
import at.kubatsch.server.controller.NetworkControllerServer;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.GameControllerBase;
import at.kubatsch.util.IEventHandler;
import at.kubatsch.util.PaddleEventArgs;

/**
 * @author Manuel Tscholl (mts3970)
 * 
 */
public class GameController extends GameControllerBase
{
    private NetworkControllerServer _networkServer;

    /**
     * 
     * Initializes a new instance of the {@link GameController} class.
     * @param portToListen
     * @throws IOException
     */
    private GameController(int portToListen) throws IOException
    {
        super();
        _networkServer = new NetworkControllerServer(portToListen);
        _stateUpdated.addHandler(new IEventHandler<EventArgs>()
        {
            @Override
            public void fired(Object sender, EventArgs e)
            {
                _networkServer.setGameState(getCurrentGameState());
            }
        });

        _networkServer
                .addNewPaddleArrivedHandler(new IEventHandler<EventArgs>()
                {
                    @Override
                    public void fired(Object sender, EventArgs e)
                    {
                        if (e instanceof PaddleEventArgs)
                        {

                        }
                    }
                });

    }

    /**
     * Only one Instance of this class can be created
     * @return the existing or the new Instance of MainController
     * @throws IOException
     */
    public static GameController getInstance(int portToListen)
            throws IOException
    {
        if (_mainController == null)
        {
            return new GameController(portToListen);
        }

        return (GameController) _mainController;
    }

    public synchronized void updatePaddle(Paddle paddle)
    {
        for (Player player : getCurrentGameState().getPlayer())
        {
            if (player != null)
            {
                if (player.getPaddle().getPaddlePosition() == paddle
                        .getPaddlePosition())
                {
                    player.setPaddle(paddle);
                }
            }
        }
        _stateUpdated.fireEvent(EventArgs.Empty);
    }
}
