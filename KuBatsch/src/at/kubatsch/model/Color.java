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
    RED(0, new java.awt.Color(218,26,27)),
    BLUE(1, new java.awt.Color(0,86,193)),
    CYAN(2, new java.awt.Color(15,170,168)),
    GOLD(3, new java.awt.Color(249,147,1)),
    GRAY(4, new java.awt.Color(218,218,218)),
    GREEN(5, new java.awt.Color(6,152,44)),
    VIOLET(6, new java.awt.Color(189,9,210))
    ; 
    
    private int _index;
    private java.awt.Color _awtColor;
    
    /**
     * Gets the awtColor.
     * @return the awtColor
     */
    public java.awt.Color getAwtColor()
    {
        return _awtColor;
    }
    
    /**
     * A numerial index which can be used to map this enumeration to arrays. 
     * @return the index
     */
    public int getIndex()
    {
        return _index;
    }
    
    /**
     * Return the Color by the index. The default color is {@link Color#RED}
     * @param index which you want the color
     * @return color
     */
    public static final Color getColor(int index)
    {
        for (Color color : values())
        {
            if(color.getIndex() == index) 
                return color;
        }
        return Color.RED;
    }
    
    /**
     * Initializes a new instance of the {@link Color} class.
     * @param index a numerial index which can be used to map this enumeration to arrays. 
     */
    Color(int index, java.awt.Color color)
    {
        _index = index; 
        _awtColor = color;
    }
}
