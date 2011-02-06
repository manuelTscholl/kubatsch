/**
 * author: Martin Balter
 * created on: 26.01.2011
 * filename: GameMouseController.java
 * project: KuBaTsch
 */
package at.kubatsch.client.controller;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import at.kubatsch.util.Event;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;
import at.kubatsch.util.KuBaTschUtils;

/**
 * @author Martin Balter
 * 
 */
public class MouseInputController implements IInputController
{
    private static final Cursor EMPTY_CURSOR = Toolkit.getDefaultToolkit()
    .createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
            new Point(0, 0), "Empty");
    
    private float              _sensity;
    private Component          _component;
    private Robot              _r2d2;
    private MouseMotionAdapter _mouseMotionAdapter;
    
    private float              _currentValue;
    private Event<EventArgs>   _positionChanged = new Event<EventArgs>(this);


    public MouseInputController(float sensity, Component component) throws AWTException
    {
        _sensity = sensity;
        _component = component;
        _r2d2 = new Robot();

        _mouseMotionAdapter = new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                Point frameCenter = new Point(getMiddleOfFrame(), getMiddleOfFrame());
                SwingUtilities.convertPointToScreen(frameCenter, _component);
                
                int way =  e.getLocationOnScreen().x - frameCenter.x;
                
                move((getRelationToFrameSize(way) * _sensity));
                
                _r2d2.mouseMove(frameCenter.x, frameCenter.y);
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
                return _component.getWidth() / 2;
            }

        };

        _component.addMouseMotionListener(_mouseMotionAdapter);
        _component.setCursor(Toolkit.getDefaultToolkit()
                .createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                        new Point(0, 0), "Empty"));
    }
    
    
    

    private synchronized void move(float offset)
    {
        _currentValue = KuBaTschUtils.getValueBetweenRange(_currentValue
                + offset, 0f, 1f);
        _positionChanged.fireEvent(EventArgs.Empty);
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
        _component.addMouseMotionListener(_mouseMotionAdapter);
        _component.setCursor(EMPTY_CURSOR);
    }
    

    /**
     * @see at.kubatsch.client.controller.IInputController#disable()
     */
    @Override
    public void disable()
    {
        _component.removeMouseMotionListener(_mouseMotionAdapter);
        _component.setCursor(Cursor.getDefaultCursor());
    }
}
