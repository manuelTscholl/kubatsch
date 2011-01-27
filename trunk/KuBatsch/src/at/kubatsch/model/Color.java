/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: Color.java
 *   project: KuBatsch
 */
package at.kubatsch.model;

/**
 * This enumerations lists all colors available for specific elements within KuBaTsch.
 * Colors provided by this enumeration will be available for choosing the paddle-color 
 * or the ball color. 
 * @author Daniel Kuschny (dku2375)
 *
 */
public enum Color
{
    RED(0, java.awt.Color.red),
    BLUE(1, java.awt.Color.blue),
    CYAN(2, java.awt.Color.cyan),
    GOLD(3, java.awt.Color.orange),
    GRAY(4, java.awt.Color.gray),
    GREEN(5, java.awt.Color.green),
    VIOLET(6, java.awt.Color.magenta)
    ;
    
    private int _index;
    
    /**
     * A numerial index which can be used to map this enumeration to arrays. 
     * @return the index
     */
    public int getIndex()
    {
        return _index;
    }
    
    public static final Color getColor(int index)
    {
        for (Color color : values())
        {
            if(color.getIndex() == index) 
                return color;
        }
        return Color.RED;
    }
    
    private java.awt.Color _color;
    
    /**
     * Initializes a new instance of the {@link Color} class.
     * @param index a numerial index which can be used to map this enumeration to arrays. 
     * @param awtColor deprecated
     */
    Color(int index, java.awt.Color awtColor)
    {
        _index = index; 
        _color = awtColor;
    }
    /**
     * "Need to remove this, only for testdummy"
     * @return the color
     */
    @Deprecated
    public java.awt.Color getColor()
    {
        return _color;
    }
}
