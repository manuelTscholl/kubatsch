package at.kubatsch.samples.motion;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

import at.kubatsch.client.model.gear.KeyboardConfig;
import at.kubatsch.model.Color;
import at.kubatsch.server.model.ServerConfig;
import at.kubatsch.uicontrols.PaddlePainter;
import at.kubatsch.util.ConfigManager;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

public class DummyProgram
{

    public static void main(String[] args) 
    {
        final JFrame f = new JFrame("TestCase");
        f.setSize(800, 800);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        final Paddle p = new Paddle();
        p.addPaddleMovedListener(new IEventHandler<EventArgs>()
        {
            
            @Override
            public void fired(Object sender, EventArgs e)
            {
                f.repaint();
            }
        });
        
        JPanel paddlPanel = new JPanel()
        {
            @Override
            public void paint(Graphics g)
            {
                super.paint(g);
                int realX = (int)(p.getX() * getWidth());
                PaddlePainter.paint(g, new Rectangle(realX, 50, (int)(getWidth()*p.getPaddleSize()),30),  Color.RED, 0.7f);
            }  
        };
        f.add(paddlPanel);

        
        
//        GameKeyboardListnerController blub = new GameKeyboardListnerController(
//                p, new KeyboardConfig().getDefaultConfig(), f);
//        blub.addPaddleMovedListener(new IEventHandler<EventArgs>()
//        {
//
//            @Override
//            public void fired(Object sender, EventArgs e)
//            {
//                f.repaint();
//            }
//        });
        
        
        
        KeyboardListener listener = new KeyboardListener(p, new KeyboardConfig());
        
        try
        {
            new GameMouseController(p, 2f, f);
        }
        catch (AWTException e1)
        {
            e1.printStackTrace();
        }
        
        // f.addKeyListener(new KeyAdapter()
        // {
        // @Override
        // public void keyPressed(KeyEvent e)
        // {
        // System.out.println("pressed");
        // if (e.getKeyCode() == _config.getLeftKey())
        // {
        // _left = true;
        // }
        // else if (e.getKeyCode() == _config.getRightKey())
        // {
        // _right = true;
        // }
        // e.consume();
        //
        // // if (_left || _right)
        // // {
        // // _keyThread.start();
        // // }
        // }
        //
        // @Override
        // public void keyReleased(KeyEvent e)
        // {
        //
        // System.out.println("release");
        // if (e.getKeyCode() == KeyEvent.VK_LEFT)
        // {
        // _left = false;
        // }
        //
        // else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        // {
        // _right = false;
        // }
        // e.consume();
        // }
        // });

        f.setVisible(true);
    }
}
