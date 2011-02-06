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

/**
 * @author Daniel Kuschny (dku2375)
 *
 */
public interface IGameRule
{
    public boolean canStartRound(GameState state);
    public void startRound(GameState state);
    
    public boolean isFinished(GameState state);
    public void finishRound(GameState state);
    
    public void setupPlayer(Player player);
    public void setupBall(Ball ball);
}
