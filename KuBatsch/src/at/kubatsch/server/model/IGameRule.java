/**
 * author: Daniel Kuschny (dku2375)
 * created on: 04.02.2011
 * filename: GameRule.java
 * project: KuBatsch
 */
package at.kubatsch.server.model;

import at.kubatsch.model.Ball;
import at.kubatsch.model.GameState;
import at.kubatsch.model.Player;
import at.kubatsch.util.GameControllerBase;

/**
 * Game Rules for the {@link GameControllerBase}. This rule is used to set
 * when a game starts or ends
 * @author Daniel Kuschny (dku2375)
 */
public interface IGameRule
{
    /**
     * Check if the game can start the round
     * @param state gamestate to check
     * @return can start round
     */
    public boolean canStartRound(GameState state);
    
    /**
     * Start the round of the game
     * @param state start the round of the {@link GameState}
     */
    public void startRound(GameState state);
    
    /**
     * Is a {@link GameState} finished
     * @param state {@link GameState} to check
     * @return is Game finished
     */
    public boolean isFinished(GameState state);
    
    /**
     * Check if Game Round is finished
     * @param state {@link GameState} to check
     */
    public void finishRound(GameState state);
    
    /**
     * setupPlayers
     * @param player
     */
    public void setupPlayer(Player player);
    
    /**
     * Step the Ball
     * @param ball
     */
    public void setupBall(Ball ball);
}
