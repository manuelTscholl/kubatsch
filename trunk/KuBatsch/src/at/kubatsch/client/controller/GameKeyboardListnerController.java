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

import javax.swing.JFrame;

import at.kubatsch.client.model.gear.KeyboardConfig;
import at.kubatsch.samples.motion.Paddle;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

/**
 * @author Martin Balter
 *
 */
public class GameKeyboardListnerController extends KeyAdapter
{
    private Paddle     _paddle;
    private KeyboardConfig  _config;
    private boolean    _left;
    private boolean    _right;
    private Thread     _keyThread;
    private KeyAdapter _keyAdapter;

    private Object     _flagLock = new Object();

    /**
     * Initializes a new instance of the @see GameKeyboardListner class.
     * @param paddle
     * @param config
     */
    public GameKeyboardListnerController(Paddle paddle, KeyboardConfig config, JFrame frame)
    {
        _paddle = paddle;
        _config = config;

        setLeft(false);
        setRight(false);

        _keyThread = new Thread("KeyThread")
        {
            @Override
            public void run()
            {
                while (true)
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
                        
                        if (isLeft() && isRight() == false)
                        {
                            _paddle.movePaddle(-0.001f);
                        }
                        else if (isLeft() == false && isRight())
                        {
                            _paddle.movePaddle(0.001f);
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

        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener()
        {
            
            @Override
            public void eventDispatched(AWTEvent event)
            {
                boolean isGameRunning = true;
                if(isGameRunning)
                {
                    if(event.getID() == KeyEvent.KEY_PRESSED)
                    {
                        _keyAdapter.keyPressed((KeyEvent)event);
                    }
                    else if(event.getID() == KeyEvent.KEY_RELEASED)
                    {
                        _keyAdapter.keyReleased((KeyEvent)event);
                    }
                }
            }
        }, AWTEvent.KEY_EVENT_MASK);
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
        synchronized (_flagLock)
        {
            _right = right;
            _flagLock.notify();
        }
    }

    /**
     * @param handler
     * @see at.kubatsch.samples.motion.Paddle#addPaddleMovedListener(at.kubatsch.util.IEventHandler)
     */
    public void addPaddleMovedListener(IEventHandler<EventArgs> handler)
    {
        _paddle.addPaddleMovedListener(handler);
    }

    /**
     * @param handler
     * @see at.kubatsch.samples.motion.Paddle#removePaddleMovedListener(at.kubatsch.util.IEventHandler)
     */
    public void removePaddleMovedListener(IEventHandler<EventArgs> handler)
    {
        _paddle.removePaddleMovedListener(handler);
    }
    
    

}
