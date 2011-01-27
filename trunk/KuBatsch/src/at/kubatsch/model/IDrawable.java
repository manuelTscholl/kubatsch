/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: IDrawable.java
 *   project: KuBatsch
 */
package at.kubatsch.model;

import java.awt.Dimension;
import java.awt.Graphics;

/**
 * Classes implementing this interface can be drawn into any graphics object.
 * This class is used to enable drawing of classes like {@link Ball} or
 * {@link Paddle}
 * @author Daniel Kuschny (dku2375)
 * 
 */
public interface IDrawable extends ICollidable
{
    /**
     * Called as the elements need to be painted.
     * @param g The graphics to draw into.
     * @param realSize All positions, maps and other sizes are specified as
     *            floating point between 0 and 1. This real size allows 
     *            interpolating those 0-1 values to real coordinates. 
     * 
     */
    public void paint(Graphics g, Dimension realSize);
}
