/**
 * This file is part of KuBatsch.
 *   created on: 25.01.2011
 *   filename: BloodChooser.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JList;

import at.kubatsch.util.Event;
import at.kubatsch.util.EventArgs;
import at.kubatsch.util.IEventHandler;

/**
 * This class is a special item choosing control similar to {@link JList}. It
 * allows to present a list of items as strings on a bloody background.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class BloodChooser extends JComponent
{
    /**
     * A unique serialization id. 
     */
    private static final long serialVersionUID = -6083095971611953210L;
    private List<Object> _items;
    private int          _selectedIndex;

    /**
     * Initializes a new instance of the {@link BloodChooser} class.
     */
    public BloodChooser()
    {
        _items = new ArrayList<Object>();
        addMouseListener(new MouseAdapter()
        {
            /**
             * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
             */
            @Override
            public void mousePressed(MouseEvent e)
            {
                selectItemAt(e.getPoint());
            }
        });
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /**
     * Adds a new item to the list of displayed elements.
     * @param item the new item to add
     */
    public void addItem(Object item)
    {
        _items.add(item);
        repaint();
    }

    /**
     * Adds an item from the list of displayed elements.
     * @param item the item to remove.
     */
    public void removeItem(Object item)
    {
        _items.remove(item);
        repaint();
    }

    /**
     * Gets the index of the currently selected item.
     * @return the index of the currently selected item.
     */
    public int getSelectedIndex()
    {
        return _selectedIndex;
    }

    /**
     * Sets the index of the currently selected item.
     * @param selectedIndex the index of the currently selected item.
     */
    public void setSelectedIndex(int selectedIndex)
    {
        _selectedIndex = Math
                .min(Math.max(0, selectedIndex), _items.size() - 1);
        _itemChanged.fireEvent(EventArgs.Empty);
        repaint();
    }

    /**
     * Gets the currently selected item.
     * @return the currently selected item if available or null.
     */
    public Object getSelectedItem()
    {
        if (_selectedIndex >= 0 && _selectedIndex < _items.size())
        {
            return _items.get(_selectedIndex);
        }
        return null;
    }

    /**
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(_items.size()
                * KuBatschTheme.CHOOSER_ITEM_SIZE.width,
                KuBatschTheme.CHOOSER_ITEM_SIZE.height);
    }

    /**
     * Selects the item located at the specified point.
     * @param pt the point.
     */
    public void selectItemAt(Point pt)
    {
        int i = pt.x / KuBatschTheme.CHOOSER_ITEM_SIZE.width;
        setSelectedIndex(i);
    }

    private Event<EventArgs> _itemChanged = new Event<EventArgs>(this);

    /**
     * Adds a new listener which will be called as soon a new item is selected.
     * @param handler the listener to add
     */
    public void addItemChangedHandler(IEventHandler<EventArgs> handler)
    {
        _itemChanged.addHandler(handler);
    }

    /**
     * Removes a listener from the list which will be called as soon a new item
     * is selected.
     * @param handler the listener to remove
     */
    public void removeItemChangedHandler(IEventHandler<EventArgs> handler)
    {
        _itemChanged.removeHandler(handler);
    }

    /**
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int x = 0;
        int y = 0;

        for (int i = 0; i < _items.size(); i++)
        {
            Object o = _items.get(i);
            int index = i == _selectedIndex ? 0 : 1;
            Image bg = KuBatschTheme.TEXTBOX_BACKGROUND[index];

            // background
            g.drawImage(bg, x, y, x + KuBatschTheme.CHOOSER_ITEM_SIZE.width, y
                    + KuBatschTheme.CHOOSER_ITEM_SIZE.height, 0, 0,
                    KuBatschTheme.CHOOSER_ITEM_SIZE.width,
                    KuBatschTheme.CHOOSER_ITEM_SIZE.height, this);

            String s = getItemText(o);

            Dimension textSize = SmallCapsUtility.calculateSize(this, s,
                    KuBatschTheme.MAIN_FONT, KuBatschTheme.SMALL_FONT);

            int textX = x
                    + (KuBatschTheme.CHOOSER_ITEM_SIZE.width - textSize.width)
                    / 2;
            int textY = y
                    + (KuBatschTheme.CHOOSER_ITEM_SIZE.height - textSize.height)
                    / 2;

            g.setColor(KuBatschTheme.TEXTBOX_FOREGROUND[index]);
            SmallCapsUtility.render(g, this, s, KuBatschTheme.MAIN_FONT,
                    KuBatschTheme.SMALL_FONT, textX, textY);

            x += KuBatschTheme.CHOOSER_ITEM_SIZE.width;
        }
    }

    /**
     * Gets a string representing the given item.
     * @param o the item. 
     * @return the string representing the given item. 
     */
    private String getItemText(Object o)
    {
        return String.format("%s", o);
    }
}
