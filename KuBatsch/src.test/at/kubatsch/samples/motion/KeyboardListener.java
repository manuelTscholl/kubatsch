package at.kubatsch.samples.motion;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import at.kubatsch.client.model.gear.KeyboardConfig;

public class KeyboardListener
{
    private boolean        _left;
    private boolean        _right;

    private Lock           _flagLock     = new ReentrantLock();
    private Condition      _valueChanged = _flagLock.newCondition();
    private Thread         _updateThread;
    private Paddle         _paddles;
    private KeyboardConfig _config;

    public KeyboardListener(Paddle paddles, KeyboardConfig config)
    {
        _config = config;
        _paddles = paddles;
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener()
        {
            @Override
            public void eventDispatched(AWTEvent event)
            {
                boolean isGameRunning = true;
                if (isGameRunning)
                {
                    if (event.getID() == KeyEvent.KEY_PRESSED)
                    {
                        keyPressed((KeyEvent) event);
                    }
                    else if (event.getID() == KeyEvent.KEY_RELEASED)
                    {
                        keyReleased((KeyEvent) event);
                    }
                }
            }
        }, AWTEvent.KEY_EVENT_MASK);

        _updateThread = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (true)
                {
                    boolean left = isLeft();
                    boolean right = isRight();

                    if (!left && !right)
                    {
                        _flagLock.lock();
                        try
                        {
                            _valueChanged.await();
                        }
                        catch (InterruptedException e)
                        {
                        }
                        _flagLock.unlock();
                    }
                    else if (left && !right)
                    {
                        _paddles.movePaddle(-_config.getRepeateRate());
                    }
                    else if (!left && right)
                    {
                        _paddles.movePaddle(_config.getRepeateRate());
                    }

                    try
                    {
                        Thread.sleep(5);
                    }
                    catch (InterruptedException e)
                    {
                    }
                }
            }
        }, "KeyUpdater");
        _updateThread.start();
    }

    protected void keyReleased(KeyEvent event)
    {
        if (event.getKeyCode() == _config.getLeftKey())
        {
            setLeft(false);
        }
        else if (event.getKeyCode() == _config.getRightKey())
        {
            setRight(false);
        }
    }

    protected void keyPressed(KeyEvent event)
    {
        if (event.getKeyCode() == _config.getLeftKey())
        {
            setLeft(true);
        }
        else if (event.getKeyCode() == _config.getRightKey())
        {
            setRight(true);
        }
    }

    /**
     * Gets the left.
     * @return the left
     */
    private boolean isLeft()
    {
        try
        {
            _flagLock.lock();
            return _left;
        }
        finally
        {
            _flagLock.unlock();
        }
    }

    /**
     * Sets the left.
     * @param left the left to set
     */
    private void setLeft(boolean left)
    {
        try
        {
            _flagLock.lock();
            if (_left == left)
                return;
            _left = left;

            _valueChanged.signalAll();
        }
        finally
        {
            _flagLock.unlock();
        }
    }

    /**
     * Gets the right.
     * @return the right
     */
    private boolean isRight()
    {
        try
        {
            _flagLock.lock();
            return _right;
        }
        finally
        {
            _flagLock.unlock();
        }
    }

    /**
     * Sets the right.
     * @param right the right to set
     */
    private void setRight(boolean right)
    {
        try
        {
            _flagLock.lock();
            if (right == _right)
                return;
            _right = right;

            _valueChanged.signalAll();
        }
        finally
        {
            _flagLock.unlock();
        }
    }

}
