/**
 * author: Daniel Kuschny (dku2375)
 * created on: 05.02.2011
 * filename: ClientGameController.java
 * project: KuBatsch
 */
package at.kubatsch.client.controller;

import at.kubatsch.util.GameControllerBase;

/**
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class ClientGameController extends GameControllerBase
{
    private static ClientGameController _instance;
    public static ClientGameController getInstance()
    {
        if(_instance == null) _instance = new ClientGameController();
        return _instance;
    }

    private ClientGameController()
    {
        setStateUpdateInterval(1);
    }

    /**
     * @see at.kubatsch.util.GameControllerBase#runGame()
     */
    @Override
    protected void runGame()
    {
        // simply let the controller interpolate the
        // gamestate
    }

}
