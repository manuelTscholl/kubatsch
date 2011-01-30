/**
 * author: Martin Balter
 * created on: 26.01.2011
 * filename: GameMouseController.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.motion;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * @author Martin Balter
 * 
 */
public class GameMouseController
{
    private Paddle             _paddle;
    private float              _sensity;
    private Component          _component;
    private Robot              _r2d2;
    private MouseMotionAdapter _mouseMotionAdapter;
    private KeyAdapter         _keyAdapter;

    public GameMouseController(Paddle paddle, float sensity, JFrame component)
            throws AWTException
    {
        _paddle = paddle;
        _sensity = sensity;
        _component = component;
        _r2d2 = new Robot();

        _mouseMotionAdapter = new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                int middleOfFrame = getMiddleOfFrame();

                int way = _component.getX() + e.getX() - middleOfFrame;
                _paddle.movePaddle((getRelationToFrameSize(way) * _sensity));
                _r2d2.mouseMove(middleOfFrame, _component.getY() + middleOfFrame);
                System.out.println(_paddle.getX());
            }

            /**
             * This converts the pixel into the range between 0 and 1 which the game based on
             * @param position position of the coordinate
             * @return the interolation of the values
             */
            protected float getRelationToFrameSize(int position)
            {
                return (float) position / (float) _component.getWidth();
            }

            private int getMiddleOfFrame()
            {
                return _component.getX() + _component.getWidth() / 2;
            }

        };

        _keyAdapter = new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE)
                {
                    // TODO unhide Curser
                }

            }
        };

        _component.addMouseMotionListener(_mouseMotionAdapter);
        _component.addKeyListener(_keyAdapter);
        _component.setCursor(Toolkit.getDefaultToolkit()
                .createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                        new Point(0, 0), "Empty"));
    }

}
