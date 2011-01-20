/**
 * author: Manuel Tscholl(mts3970)
 * created on: 19.01.2011
 * filename: ClientGamePanel.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.networkballserver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;

import at.kubatsch.samples.uiperformance.Ball;
import at.kubatsch.samples.uiperformance.GamePanel;

/**
 * @author Manuel Tscholl (mts3970)
 * 
 */
public class ClientGamePanel extends JComponent
{
    private Thread            _updateThread;
    private Thread            _networkUpdateThread;
    private List<Ball>        _balls;
    private boolean           _running;
    private Socket            _socket;
    private InputStream       _inputStream;
    private ObjectInputStream _objectInputStream;

    private int               _size;

    /**
     * Gets the running.
     * @return the running
     */
    public synchronized boolean isRunning()
    {
        return _running;
    }

    public synchronized List<Ball> getBalls()
    {
        return _balls;
    }

    public synchronized void setBalls(List<Ball> balls)
    {
        System.out.println(balls.size());
        _balls = balls;
    }

    /**
     * Sets the running.
     * @param running the running to set
     */
    public synchronized void setRunning(boolean running)
    {
        _running = running;
    }

    /**
     * Initializes a new instance of the {@link GamePanel} class.
     */
    public ClientGamePanel(String host, int port)
    {
        _balls = new ArrayList<Ball>();
        setDoubleBuffered(true);
        _size = 800;

        try
        {
            _socket = new Socket(host, port);
            _inputStream = _socket.getInputStream();
            _objectInputStream = new ObjectInputStream(_inputStream);
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * @see javax.swing.JComponent#reshape(int, int, int, int)
     */
    @SuppressWarnings("deprecation")
    @Override
    public void reshape(int x, int y, int width, int height)
    {
        if (width > height) // landscape mode
        {
            width = height;
        }
        else
        // portrait
        {
            height = width;
        }
        _size = width;

        super.reshape(x, y, width, height);
    }

    /**
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(_size, _size);
    }

    public void start()
    {
        if (_updateThread != null)
            return; // already running

        // initialize
        setRunning(true);

        _updateThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                runUpdate();
            }
        });
        _updateThread.start();

        _networkUpdateThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                runNetwork();
            }
        });
        _networkUpdateThread.start();

    }

    public void stop()
    {
        setRunning(false);

        if (_updateThread != null)
        {
            try
            {
                _updateThread.interrupt();
                _updateThread.join();
                _updateThread = null;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        if (_networkUpdateThread != null)
        {
            try
            {
                _networkUpdateThread.interrupt();
                _networkUpdateThread.join();
                _networkUpdateThread = null;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void runNetwork()
    {
        while (isRunning())
        {

            try
            {
                Ball[] balls = (Ball[]) _objectInputStream.readObject();

                setBalls(Arrays.asList(balls));

            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }

            catch (ClassNotFoundException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    public void runUpdate()
    {
        while (isRunning())
        {
            updateGame();
            try
            {
                Thread.sleep(30);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Ball ball : getBalls())
        {
            g.setColor(ball.getColor());

            double realPosX = getWidth() * ball.getPosX();
            double realPosY = getHeight() * ball.getPosY();
            double realSizeX = ball.getSize() * getWidth();
            double realSizeY = ball.getSize() * getHeight();

            g.fillOval((int) realPosX, (int) realPosY, (int) realSizeX,
                    (int) realSizeY);
        }
    }

    private synchronized void updateGame()
    {
        for (Ball ball : getBalls())
        {
            // move balls to new location
            ball.move();
            // collision detection
            if (ball.getPosX() + ball.getSize() >= 1) // right wall
            {
                ball.setPosX(1 - ball.getSize());
                ball.setSpeedX(-ball.getSpeedX());
            }
            if (ball.getPosX() <= 0) // left wall
            {
                ball.setPosX(0);
                ball.setSpeedX(-ball.getSpeedX());
            }

            if (ball.getPosY() + ball.getSize() >= 1) // bottom wall
            {
                ball.setPosY(1 - ball.getSize());
                ball.setSpeedY(-ball.getSpeedY());
            }
            if (ball.getPosY() <= 0) // top wall
            {
                ball.setPosY(0);
                ball.setSpeedY(-ball.getSpeedY());
            }
        }

        repaint();
    }
}
