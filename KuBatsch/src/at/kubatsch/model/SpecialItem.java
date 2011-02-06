/**
 * author: Manuel Tscholl(mts3970)
 * created on: 03.02.2011
 * filename: SpecialItem.java
 * project: KuBaTsch
 */
package at.kubatsch.model;

import java.awt.Dimension;
import java.awt.Graphics;

/**
 * @author Manuel Tscholl (mts3970)
 *
 */
public abstract class SpecialItem extends CollidableBase implements IDrawable
{

    /**
     * 
     */
    private static final long serialVersionUID = 84010523470059271L;

    /**
     * @see at.kubatsch.model.IDrawable#paint(java.awt.Graphics, java.awt.Dimension)
     */
    @Override
    public abstract void paint(Graphics g, Dimension realSize);

    
  
}
