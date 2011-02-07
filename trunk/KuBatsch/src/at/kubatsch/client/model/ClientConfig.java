/**
 * author: Martin Balter
 * created on: 18.01.2011
 * filename: ClientConfig.java
 * project: KuBaTsch
 */
package at.kubatsch.client.model;

import at.kubatsch.client.controller.ClientConfigController;
import at.kubatsch.client.model.gear.KeyboardConfig;
import at.kubatsch.client.model.gear.MouseConfig;
import at.kubatsch.model.Color;
import at.kubatsch.model.Config;
import at.kubatsch.util.KuBaTschUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Configuration for the Client. Is used by the {@link ClientConfigController}
 * @author Martin Balter
 */
public class ClientConfig extends Config
{
    private static final long  serialVersionUID = 6500513234673037329L;
    public static final String CONFIG_ID        = "clientconfig";

    @XStreamAlias(value = "name")
    private String             _name;
    @XStreamAlias(value = "controlType")
    private Config[]           _controlType;
    @XStreamAlias(value = "north")
    private Color              _northColor;
    @XStreamAlias(value = "south")
    private Color              _southColor;
    @XStreamAlias(value = "west")
    private Color              _westColor;
    @XStreamAlias(value = "east")
    private Color              _eastColor;
    @XStreamAlias(value = "music")
    private float             _music;
    @XStreamAlias(value = "effects")
    private float             _effects;
    @XStreamAlias(value = "hudAlpha")
    private float             _hudAlpha;

    /**
     * Initializes a new instance of the @see ClientConfig class.
     * Is also used to create a standard {@link ClientConfig}.
     */
    public ClientConfig()
    {
        super();
        _controlType = new Config[2];
        _controlType[0] = new KeyboardConfig();
        _controlType[1] = new MouseConfig();

        setEffects(1);
        setMusic(1);
        setName("Player");
        setNorthColor(Color.BLUE);
        setEastColor(Color.GREEN);
        setSouthColor(Color.RED);
        setWestColor(Color.GOLD);
        setHudAlpha(0.5f);
    }

    /**
     * Gets the nickname of the player.
     * @return the name
     */
    public String getName()
    {
        return _name;
    }

    /**
     * Sets the nickname of the player.
     * @param name the name to set
     */
    public void setName(String name)
    {
        _name = name;
    }

    /**
     * Gets the controlTypes.
     * <p>At the first position [0] is the {@link KeyboardConfig}<br>
     * At the secound position [1] is the {@link MouseConfig}
     * @return the controlTypes
     */
    public Config[] getControlType()
    {
        return _controlType;
    }

    /**
     * Sets the controlType.
     * <p>At the first position [0] is the {@link KeyboardConfig}<br>
     * At the secound position [1] is the {@link MouseConfig}
     * @param controlType the controlType to set
     */
    public void setControlType(Config[] controlType)
    {
        _controlType = controlType;
    }

    /**
     * Gets the northColor of the paddle.
     * @return the north color
     */
    public Color getNorthColor()
    {
        return _northColor;
    }

    /**
     * Sets the northColor of the paddle.
     * @param northColor the north color of the paddle to set
     */
    public void setNorthColor(Color northColor)
    {
        _northColor = northColor;
    }

    /**
     * Gets the southColor of the paddle.
     * @return the southColor
     */
    public Color getSouthColor()
    {
        return _southColor;
    }

    /**
     * Sets the southColor of the paddle.
     * @param southColor the southColor of the paddle to set
     */
    public void setSouthColor(Color southColor)
    {
        _southColor = southColor;
    }

    /**
     * Gets the westColor of the paddle.
     * @return the westColor
     */
    public Color getWestColor()
    {
        return _westColor;
    }

    /**
     * Sets the westColor of the paddle.
     * @param westColor the westColor of the paddle to set
     */
    public void setWestColor(Color westColor)
    {
        _westColor = westColor;
    }

    /**
     * Gets the eastColor of the paddle.
     * @return the eastColor
     */
    public Color getEastColor()
    {
        return _eastColor;
    }

    /**
     * Sets the eastColor of the paddle.
     * @param eastColor the eastColor of the paddle to set
     */
    public void setEastColor(Color eastColor)
    {
        _eastColor = eastColor;
    }

    /**
     * Gets the background music volume.
     * @return the backgroudn music volume
     */
    public float getMusic()
    {
        return _music;
    }

    /**
     * Sets the background music volume.
     * @param music the background music volume to set
     */
    public void setMusic(float music)
    {
        _music = KuBaTschUtils.getValueBetweenRange(music, 0f, 1f);
    }

    /**
     * Gets the background music effects volume.
     * @return the background effects volume
     */
    public float getEffects()
    {
        return _effects;
    }

    /**
     * Sets the background music effects volume.
     * @param effects the background music effects volume to set
     */
    public void setEffects(float effects)
    {
        _effects = KuBaTschUtils.getValueBetweenRange(effects, 0f, 1f);
    }

    /**
     * Gets the hud alpha.
     * @return the hud alpha
     */
    public double getHudAlpha()
    {
        return _hudAlpha;
    }

    /**
     * Sets the hud alpha.
     * @param hudAlpha the hud alpha to set
     */
    public void setHudAlpha(float hudAlpha)
    {
        _hudAlpha = KuBaTschUtils.getValueBetweenRange(hudAlpha, 0f, 1f);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ");
        sb.append(this.getName());
        sb.append(System.getProperty("line.seperator"));

        sb.append("ControlTypes: ");
        sb.append(this.getControlType()[0].getConfigType());
        sb.append(" / ");
        sb.append(this.getControlType()[1].getConfigType());
        sb.append(System.getProperty("line.seperator"));

        sb.append("North: ");
        sb.append(this.getNorthColor());
        sb.append(System.getProperty("line.seperator"));

        sb.append("East: ");
        sb.append(this.getEastColor());
        sb.append(System.getProperty("line.seperator"));

        sb.append("South: ");
        sb.append(this.getSouthColor());
        sb.append(System.getProperty("line.seperator"));

        sb.append("West: ");
        sb.append(this.getWestColor());
        sb.append(System.getProperty("line.seperator"));

        sb.append("Music: ");
        sb.append(this.getMusic());
        sb.append(System.getProperty("line.separator"));

        sb.append("SoundEffects: ");
        sb.append(this.getEffects());
        sb.append(System.getProperty("line.seperator"));

        sb.append("HudAlpha: ");
        sb.append(this.getHudAlpha());
        sb.append(System.getProperty("line.seperator"));

        return sb.toString();
    }

    /**
     * Returns the the typename as a "clientconfig" ({@link ClientConfig#CONFIG_ID});
     * @see at.kubatsch.model.Config#getConfigType()
     */
    @Override
    public String getConfigType()
    {
        return CONFIG_ID;
    }
}
