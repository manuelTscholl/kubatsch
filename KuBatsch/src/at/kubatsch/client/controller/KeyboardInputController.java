/**
 * author: Martin Balter
 * created on: 27.01.2011
 * filename: GameKeyboardListner.java
 * project: KuBaTsch
 */
package at.kubatsch.client.controller;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import at.kubatsch.client.model.gear.KeyboardConfig;
import at.kubatsch.util.Event;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;
import at.kubatsch.util.KuBaTschUtils;

/**
 * A controller handling the input via keyboard. It allows controlling a
 * floating point variable ranged between 0-1 via a "Left" and "Right" key,
 * @author Martin Balter
 * 
 */
public class KeyboardInputController extends KeyAdapter implements
        IInputController
{
    /**
     * The offset used per step.
     */
    private static final float OFFSET           = 0.01f;

    private float              _currentValue;
    private KeyboardConfig     _config;
    private boolean            _left;
    private boolean            _right;
    private Thread             _keyThread;
    private KeyAdapter         _keyAdapter;
    private AWTEventListener   _globalKeyListener;

    private Object             _flagLock        = new Object();
    private Event<EventArgs>   _positionChanged = new Event<EventArgs>(this);

    /**
     * Initializes a new instance of the {@link KeyboardInputController} class.
     * @param paddle
     * @param config
     */
    public KeyboardInputController(KeyboardConfig config)
    {
        _currentValue = 0;
        _config = config;

        setLeft(false);
        setRight(false);

        _keyThread = new Thread("KeyThread")
        {
            @Override
            public void run()
            {
                while (!isInterrupted())
                {
                    synchronized (_flagLock)
                    {
                        if (!(isLeft() || isRight()))
                        {
                            try
                            {
                                _flagLock.wait();
                            }
                            catch (InterruptedException e)
                            {
                            }
                        }

                        if (isLeft() && !isRight())
                        {
                            move(-OFFSET);
                        }
                        else if (isRight() && !isLeft())
                        {
                            move(OFFSET);
                        }
                        try
                        {
                            Thread.sleep(_config.getRepeateRate());
                        }
                        catch (InterruptedException e)
                        {
                            interrupt();
                        }
                    }
                }
            }
        };
        _keyThread.start();

        _keyAdapter = new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == _config.getLeftKey())
                {
                    setLeft(true);
                }
                else if (e.getKeyCode() == _config.getRightKey())
                {
                    setRight(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == _config.getLeftKey())
                {
                    setLeft(false);
                }

                else if (e.getKeyCode() == _config.getRightKey())
                {
                    setRight(false);
                }
            }
        };

        _globalKeyListener = new AWTEventListener()
        {

            @Override
            public void eventDispatched(AWTEvent event)
            {
                if (event.getID() == KeyEvent.KEY_PRESSED)
                {
                    _keyAdapter.keyPressed((KeyEvent) event);
                }
                else if (event.getID() == KeyEvent.KEY_RELEASED)
                {
                    _keyAdapter.keyReleased((KeyEvent) event);
                }
            }
        };
    }

    /**
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable
    {
        _keyThread.interrupt();
        super.finalize();
    }

    private void move(float offset)
    {
        _currentValue = KuBaTschUtils.getValueBetweenRange(_currentValue
                + offset, 0f, 1f);

        _positionChanged.fireEvent(EventArgs.Empty);
    }

    /**
     * Gets the left.
     * @return the left
     */
    private boolean isLeft()
    {
        return _left;
    }

    /**
     * Sets the left.
     * @param left the left to set
     */
    private void setLeft(boolean left)
    {
        if (_left == left)
            return;
        synchronized (_flagLock)
        {
            _left = left;
            _flagLock.notify();
        }
    }

    /**
     * Gets the right.
     * @return the right
     */
    private boolean isRight()
    {
        return _right;
    }

    /**
     * Sets the right.
     * @param right the right to set
     */
    private void setRight(boolean right)
    {
        if (_right == right)
            return;
        synchronized (_flagLock)
        {
            _right = right;
            _flagLock.notify();
        }
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#addHandler(at.kubatsch.util.IEventHandler)
     */
    public void addPositionChangedListener(IEventHandler<EventArgs> handler)
    {
        _positionChanged.addHandler(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.util.Event#removeHandler(at.kubatsch.util.IEventHandler)
     */
    public void removePositionChangedListener(IEventHandler<EventArgs> handler)
    {
        _positionChanged.removeHandler(handler);
    }

    /**
     * @see at.kubatsch.client.controller.IInputController#getCurrentPosition()
     */
    @Override
    public float getCurrentPosition()
    {
        return _currentValue;
    }

    /**
     * @see at.kubatsch.client.controller.IInputController#enable()
     */
    @Override
    public void enable()
    {
        Toolkit.getDefaultToolkit().addAWTEventListener(_globalKeyListener,
                AWTEvent.KEY_EVENT_MASK);
    }

    /**
     * @see at.kubatsch.client.controller.IInputController#disable()
     */
    @Override
    public void disable()
    {
        Toolkit.getDefaultToolkit().removeAWTEventListener(_globalKeyListener);
        setLeft(false);
        setRight(false);
    }
}
