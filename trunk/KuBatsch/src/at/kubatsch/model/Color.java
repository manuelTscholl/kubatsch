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
    RED(0),
    BLUE(1),
    CYAN(2),
    GOLD(3),
    GRAY(4),
    GREEN(5),
    VIOLET(6)
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
    
    /**
     * Initializes a new instance of the {@link Color} class.
     * @param index a numerial index which can be used to map this enumeration to arrays. 
     * @param awtColor deprecated
     */
    Color(int index)
    {
        _index = index; 
    }
}
