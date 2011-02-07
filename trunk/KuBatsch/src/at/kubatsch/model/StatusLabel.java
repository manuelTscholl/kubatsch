/**
 * author: Daniel Kuschny (dku2375)
 * created on: 04.02.2011
 * filename: Countdown.java
 * project: KuBatsch
 */
package at.kubatsch.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import at.kubatsch.client.controller.ViewController;
import at.kubatsch.client.view.GameView;
import at.kubatsch.model.rules.ICollisionRule;
import at.kubatsch.uicontrols.KuBatschTheme;
import at.kubatsch.uicontrols.SmallCapsUtility;

/**
 * This is the StatusLabel rendered within the GameBoard. It can be used to show
 * outputs like the countdown.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class StatusLabel implements IDrawable
{
    /**
     * 
     */
    private static final long serialVersionUID = 8272973867314622352L;
    private String            _text;

    /**
     * Gets the text.
     * @return the text
     */
    public String getText()
    {
        return _text;
    }

    /**
     * Sets the text.
     * @param text the text to set
     */
    public void setText(String text)
    {
        _text = text;
    }


    /**
     * @see at.kubatsch.model.ICollidable#getCollisionMap()
     */
    @Override
    public Point2D.Float[] getCollisionMap()
    {
        return new Point2D.Float[0];
    }

    /**
     * @see at.kubatsch.model.ICollidable#getPosition()
     */
    @Override
    public Point2D.Float getPosition()
    {
        return new Point2D.Float(0.5f, 0.75f);
    }

    /**
     * @see at.kubatsch.model.ICollidable#collidesWith(at.kubatsch.model.ICollidable)
     */
    @Override
    public boolean collidesWith(ICollidable other)
    {
        return false;
    }

    /**
     * @see at.kubatsch.model.ICollidable#getMinPoint()
     */
    @Override
    public Point2D.Float getMinPoint()
    {
        return new Point2D.Float(0, 0);
    }

    /**
     * @see at.kubatsch.model.ICollidable#getMaxPoint()
     */
    @Override
    public Point2D.Float getMaxPoint()
    {
        return new Point2D.Float(0, 0);
    }

    /**
     * @see at.kubatsch.model.ICollidable#getCollisionRules()
     */
    @Override
    public Iterable<ICollisionRule> getCollisionRules()
    {
        return null;
    }

    /**
     * @see at.kubatsch.model.ICollidable#addCollisionRule(at.kubatsch.model.rules.ICollisionRule)
     */
    @Override
    public void addCollisionRule(ICollisionRule rule)
    {}

    /**
     * @see at.kubatsch.model.ICollidable#removeCollisionRule(at.kubatsch.model.rules.ICollisionRule)
     */
    @Override
    public void removeCollisionRule(ICollisionRule rule)
    {}

    /**
     * @see at.kubatsch.model.IDrawable#paint(java.awt.Graphics,
     *      java.awt.Dimension)
     */
    @Override
    public void paint(Graphics g, Dimension realSize)
    {
        if(_text == null) return;
        g.setColor(Color.white);
        Dimension size = SmallCapsUtility.calculateSize(
                ViewController.getInstance().getView(GameView.PANEL_ID), _text,
                KuBatschTheme.MAIN_FONT, KuBatschTheme.SMALL_FONT);
        int x = (int)(realSize.width * getPosition().x);
        x -= size.width/2;
        SmallCapsUtility.render(g, ViewController.getInstance().getView(GameView.PANEL_ID), 
                _text, KuBatschTheme.MAIN_FONT, KuBatschTheme.SMALL_FONT, x, (int)(realSize.height * getPosition().y));
    }
}
