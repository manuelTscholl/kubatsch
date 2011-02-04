/**
 * author: Martin Balter
 * created on: 23.01.2011
 * filename: MouseConfig.java
 * project: KuBaTsch
 */
package at.kubatsch.client.model.gear;

import at.kubatsch.model.Config;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Martin Balter
 *Configuration for the Keyboard
 */
public class MouseConfig extends Config implements ControlType
{
    
    private static final long serialVersionUID = -3167263316021675648L;
    public static final String CONFIG_ID = "mouseconfig";
    
    
    @XStreamAlias(value = "sensitivity")
    private double _sensitivity;

    /**
     * Initializes a new instance of the @see MouseConfig class.
     */
    public MouseConfig()
    {
        super();
        this.setSensitivity(1d);
    }

    /**
     * Initializes a new instance of the @see MouseConfig class.
     * @param sensitivity
     */
    public MouseConfig(double sensitivity)
    {
        super();
        _sensitivity = sensitivity;
    }

    /**
     * Gets the sensitivity when the mouse moves.
     * @return the sensitivity of the mouse
     */
    public double getSensitivity()
    {
        return _sensitivity;
    }

    /**
     * Sets the sensitivity when the mouse moves.
     * @param sensitivity the sensitivity of the mouse to set
     */
    public void setSensitivity(double sensitivity)
    {
        _sensitivity = sensitivity;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getConfigType()).append(System.getProperty("line.separator"));
        sb.append("Sensitivity: ").append(this.getSensitivity());

        return super.toString();
    }


    @Override
    public String getConfigType()
    {
        return CONFIG_ID;
    }
}
