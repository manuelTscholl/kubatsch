package at.kubatsch.samples.motion;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import at.kubatsch.client.model.gear.KeyConfig;
import at.kubatsch.uicontrols.PaddlePainter;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

public class DummyProgram
{

    public static void main(String[] args) throws AWTException
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
                PaddlePainter.paint(g, new Rectangle(realX, 50, (int)(getWidth()*p.getPaddleSize()),30), at.kubatsch.model.Color.RED, 0.7f);
            }  
        };
        f.add(paddlPanel);

//        GameKeyboardListnerController blub = new GameKeyboardListnerController(
//                p, new KeyConfig().getDefaultConfig(), f);
//        blub.addPaddleMovedListener(new IEventHandler<EventArgs>()
//        {
//
//            @Override
//            public void fired(Object sender, EventArgs e)
//            {
//                System.out.println("Moved");
//                f.repaint();
//            }
//        });
//        KeyboardListener listener = new KeyboardListener(p);
        
        new GameMouseController(p, 2f, f);
        
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
