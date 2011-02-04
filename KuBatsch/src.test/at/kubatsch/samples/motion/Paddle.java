/**
 * author: Martin Balter
 * created on: 26.01.2011
 * filename: Paddle.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.motion;

import at.kubatsch.util.Event;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;
import at.kubatsch.util.KuBaTschUtils;

/**
 * @author Martin Balter
 * Test paddle because i have none ^^
 */
public class Paddle
{
    private float _x = 0.5f;
    private float _paddleSize = 0.2f;

    /**
     * Gets the x.
     * @return the x
     */
    public float getX()
    {
        return _x;
    }

    public float getPaddleSize()
    {
        return _paddleSize;
    }
    
    /**
     * Sets the x.
     * @param x the x to set
     */
    public void setX(float x)
    {
        _x = KuBaTschUtils.getValueBetweenRange(x, 0f, 1f - _paddleSize);
        _paddleMoved.fireEvent(EventArgs.Empty);
    }
    
    public void movePaddle(float way)
    {
        setX(getX() + way);
    }
    
    private Event<EventArgs> _paddleMoved = new Event<EventArgs>(this);

    /**
     * @param handler
     * @see at.kubatsch.util.Event#addHandler(at.kubatsch.util.IEventHandler)
     */
    public void addPaddleMovedListener(IEventHandler<EventArgs> handler)
    {
        _paddleMoved.addHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#removeHandler(at.kubatsch.util.IEventHandler)
     */
    public void removePaddleMovedListener(IEventHandler<EventArgs> handler)
    {
        _paddleMoved.removeHandler(handler);
    }
    
    
}
