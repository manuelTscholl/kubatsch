/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: PlayerPosition.java
 *   project: KuBatsch
 */
package at.kubatsch.model;

/**
 * This enumeration lists all positions where players can be placed.
 * @author Daniel Kuschny (dku2375)
 */
public enum PlayerPosition
{
    SOUTH,
    NORTH,
    WEST,
    EAST;
    
    public static PlayerPosition getPositionForIndex(int index)
    {
        switch (index)
        {
            case 0:
                return SOUTH;
            case 1:
                return NORTH;
            case 2:
                return WEST;
            case 3:
                return EAST;
            default:
                throw new IndexOutOfBoundsException();
        }
    }
}
