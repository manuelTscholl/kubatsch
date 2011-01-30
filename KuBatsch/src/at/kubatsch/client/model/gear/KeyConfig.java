/**
 * author: Martin Balter
 * created on: 23.01.2011
 * filename: KeyConfig.java
 * project: KuBaTsch
 */
package at.kubatsch.client.model.gear;

import java.awt.event.KeyEvent;

import at.kubatsch.model.Config;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Martin Balter
 * Configuration for the Keyboard
 */
public class KeyConfig extends Config implements ControlType
{
    private static final long serialVersionUID = -6004370472373242035L;
    
    @XStreamAlias(value = "leftKey")
    private int   _leftKey;
    @XStreamAlias(value = "rightKey")
    private int   _rightKey;
    @XStreamAlias(value = "repeateRate")
    private double _repeateRate;

    /**
     * Initializes a new instance of the @see KeyConfig class.
     */
    public KeyConfig()
    {
        super();
    }

    /**
     * Gets the leftKey.
     * @return the leftKey
     */
    public int getLeftKey()
    {
        return _leftKey;
    }

    /**
     * Sets the leftKey.
     * @param vkLeft the leftKey to set
     */
    public void setLeftKey(int vkLeft)
    {
        _leftKey = vkLeft;
    }

    /**
     * Gets the rightKey.
     * @return the rightKey
     */
    public int getRightKey()
    {
        return _rightKey;
    }

    /**
     * Sets the rightKey.
     * @param rightKey the rightKey to set
     */
    public void setRightKey(int rightKey)
    {
        _rightKey = rightKey;
    }

    /**
     * Gets the repeateRate when the Key is presed for a while.
     * @return the repeateRate
     */
    public double getRepeateRate()
    {
        return _repeateRate;
    }

    /**
     * Sets the repeateRate when the key is presed for a while.
     * @param repeateRate the repeateRate to set
     */
    public void setRepeateRate(double repeateRate)
    {
        _repeateRate = repeateRate;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getConfigType()).append(System.getProperty("line.seperator"));
        sb.append("Left Key: ").append(this.getLeftKey()).append(System.getProperty("line.seperator"));
        sb.append("Right Key: ").append(this.getRightKey()).append(System.getProperty("line.seperator"));
        sb.append("Repeate Rate: ").append(this.getRepeateRate());

        return super.toString();
    }

    @Override
    public KeyConfig getDefaultConfig()
    {
        KeyConfig kc = new KeyConfig();
        kc.setLeftKey(KeyEvent.VK_LEFT);
        kc.setRightKey(KeyEvent.VK_RIGHT);
        
        return kc;
    }

    @Override
    public String getConfigType()
    {
        return "KEYBOARD";
    }

}
