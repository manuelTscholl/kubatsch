/**
 * This file is part of KuBatsch.
 *   created on: 02.01.2011
 *   filename: GamePanel.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.uiperformance;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class GamePanel extends JComponent implements Runnable
{
    private Thread     _updateThread;
    private List<Ball> _balls;
    private boolean    _running;
    
    private int _size;

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
    public GamePanel()
    {
        _balls = new ArrayList<Ball>();
        addBall(Color.black);
        setDoubleBuffered(true);
        
        _size = 800;
    }
    
    /**
     * @see javax.swing.JComponent#reshape(int, int, int, int)
     */
    @SuppressWarnings("deprecation")
    @Override
    public void reshape(int x, int y, int width, int height)
    {
        if(width > height) // landscape mode
        {
            width = height;
        }
        else // portrait
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

        _updateThread = new Thread(this);
        _updateThread.start();
    }

    public void stop()
    {
        if (_updateThread == null)
            return; // not running
        setRunning(false);

        try
        {
            _updateThread.interrupt();
            _updateThread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
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
        g.fillRect(0,0, getWidth(), getHeight());
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

    public synchronized void addBall(Color color)
    {
        Random rnd = new Random();
        Ball ball = new Ball(color);
        // place balls at center
        ball.setPosX((1 - ball.getSize()) / 2);
        ball.setPosY((1 - ball.getSize()) / 2);

        // random direction with random speed
        double speed = (rnd.nextInt(20) + 5) / 1000.0;
        if (rnd.nextBoolean())
        {
            speed = -speed;
        }
        ball.setSpeedX(speed);
        speed = (rnd.nextInt(20) + 5) / 1000.0;
        if (rnd.nextBoolean())
        {
            speed = -speed;
        }
        ball.setSpeedY(speed);
        getBalls().add(ball);
    }
    
    public synchronized void removeBall()
    {
        if (getBalls().size() > 0)
            getBalls().remove(0);
    }
}
