/**
 * author: Daniel Kuschny (dku2375)
 * created on: 27.01.2011
 * filename: DataPackage.java
 * project: KuBatsch
 */
package at.kubatsch.samples.simplenetwork;

import java.io.Serializable;

/**
 * @author Daniel Kuschny (dku2375)
 *
 */
public class DataPackage implements Serializable
{
    private int _x;
    private int _y;
    private int _vx;
    private int _vy;
    
    
    /**
     * Initializes a new instance of the {@link DataPackage} class.
     * @param x
     * @param y
     * @param vx
     * @param vy
     */
    public DataPackage(int x, int y, int vx, int vy)
    {
        super();
        _x = x;
        _y = y;
        _vx = vx;
        _vy = vy;
    }
    /**
     * Gets the x.
     * @return the x
     */
    public int getX()
    {
        return _x;
    }
    /**
     * Sets the x.
     * @param x the x to set
     */
    public void setX(int x)
    {
        _x = x;
    }
    /**
     * Gets the y.
     * @return the y
     */
    public int getY()
    {
        return _y;
    }
    /**
     * Sets the y.
     * @param y the y to set
     */
    public void setY(int y)
    {
        _y = y;
    }
    /**
     * Gets the vx.
     * @return the vx
     */
    public int getVx()
    {
        return _vx;
    }
    /**
     * Sets the vx.
     * @param vx the vx to set
     */
    public void setVx(int vx)
    {
        _vx = vx;
    }
    /**
     * Gets the vy.
     * @return the vy
     */
    public int getVy()
    {
        return _vy;
    }
    /**
     * Sets the vy.
     * @param vy the vy to set
     */
    public void setVy(int vy)
    {
        _vy = vy;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return String.format("DataPackage [_x=%s, _y=%s, _vx=%s, _vy=%s]", _x,
                _y, _vx, _vy);
    }
    
    
    
    
}
