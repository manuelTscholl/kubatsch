/**
 * author: Daniel Kuschny (dku2375)
 * created on: 05.02.2011
 * filename: GameStateEventArgs.java
 * project: KuBatsch
 */
package at.kubatsch.client.controller;

import at.kubatsch.util.EventArgs;
import at.kubatsch.model.GameState;

/**
 * @author Daniel Kuschny (dku2375)
 *
 */
public class GameStateEventArgs extends EventArgs
{
    private GameState _state;

    /**
     * Gets the state.
     * @return the state
     */
    public GameState getState()
    {
        return _state;
    }

    /**
     * Initializes a new instance of the {@link GameStateEventArgs} class.
     * @param state
     */
    public GameStateEventArgs(GameState state)
    {
        _state = state;
    }
}