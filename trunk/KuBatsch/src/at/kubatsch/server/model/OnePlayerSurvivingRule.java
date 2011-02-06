/**
 * author: Daniel Kuschny (dku2375)
 * created on: 04.02.2011
 * filename: OnePlayerSurvivingRule.java
 * project: KuBatsch
 */
package at.kubatsch.server.model;

import at.kubatsch.model.Ball;
import at.kubatsch.model.GameState;
import at.kubatsch.model.Player;
import at.kubatsch.model.rules.PlayerHitAreaReflectRule;
import at.kubatsch.model.rules.IncreaseBallSpeedRule;
import at.kubatsch.model.rules.PaddleReflectRule;
import at.kubatsch.model.rules.PlayTickRule;

/**
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class OnePlayerSurvivingRule implements IGameRule
{
    private static final int        MIN_PLAYERS = 2;

    /**
     * @see at.kubatsch.server.model.IGameRule#canStartRound(at.kubatsch.model.GameState)
     */
    @Override
    public boolean canStartRound(GameState state)
    {
        return state.getPlayerCount() >= MIN_PLAYERS;
    }

    /**
     * @see at.kubatsch.server.model.IGameRule#startRound()
     */
    @Override
    public void startRound(GameState state)
    {
        for (int i = 0; i < state.getPlayer().length; i++)
        {
            state.getPlayer()[i].setHealth(1);
        }
    }

    /**
     * @see at.kubatsch.server.model.IGameRule#isFinished(at.kubatsch.model.GameState)
     */
    @Override
    public boolean isFinished(GameState state)
    {
        int survivingPlayers = 0;
        for (int i = 0; i < state.getPlayer().length; i++)
        {
            if (state.getPlayer()[i].getUid() >= 0
                    && state.getPlayer()[i].isAlive())
            {
                survivingPlayers++;
            }
        }
        return survivingPlayers <= 1;
    }

    /**
     * @see at.kubatsch.server.model.IGameRule#finishRound()
     */
    @Override
    public void finishRound(GameState state)
    {
        for (int i = 0; i < state.getPlayer().length; i++)
        {
            if (state.getPlayer()[i].getUid() >= 0 
                    && state.getPlayer()[i].isAlive())
            {
                state.getPlayer()[i]
                        .setWins(state.getPlayer()[i].getWins() + 1);
                break;
            }
        }
        
        state.getBalls().clear();
        state.getSpecialItems().clear();
    }

    /**
     * @see at.kubatsch.server.model.IGameRule#setupPlayer(at.kubatsch.model.Player)
     */
    @Override
    public void setupPlayer(Player player)
    {
        player.getPlayerHitArea().addCollisionRule(new PlayerHitAreaReflectRule());
        player.getPaddle().addCollisionRule(new PaddleReflectRule());
        
    }
    
    public void setupBall(Ball ball)
    {
        ball.addCollisionRule(new PlayTickRule());
        ball.addCollisionRule(new IncreaseBallSpeedRule());
    }

}
