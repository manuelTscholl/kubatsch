/**
 * author: Manuel Tscholl(mts3970)
 * created on: 04.02.2011
 * filename: PaddleEventArgs.java
 * project: KuBaTsch
 */
package at.kubatsch.util;

import at.kubatsch.model.Paddle;

/**
 * @author Manuel Tscholl (mts3970)
 *
 */
public class PaddleEventArgs extends EventArgs
{
    private Paddle _paddle;
    
    /**
     * Gets the paddle.
     * @return the paddle
     */
    public Paddle getPaddle()
    {
        return _paddle;
    }

    /**
     * Initializes a new instance of the {@link PaddleEventArgs} class.
     * @param paddle
     */
    public PaddleEventArgs(Paddle paddle)
    {
        super();
        _paddle = paddle;
    }
    
    
}
