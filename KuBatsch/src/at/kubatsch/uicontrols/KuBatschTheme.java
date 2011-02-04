/**
 * This file is part of KuBatsch.
 *   created on: 15.01.2011
 *   filename: KuBatschTheme.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * This class manages the loading and providing of sprites and additional paint
 * data like Fonts and Colors for the KuBaTsch application.
 * 
 * @author Daniel Kuschny (dku2375)
 */
public class KuBatschTheme
{
    private static final String       RESOURCES_DIR = "resources";
    
    public static final int           MAIN_SIZE;
    public static final Image         MAIN_BACKGROUND;
    public static final Image         CONTROL_SETTINGS;
    public static final Image         BLOOD_BACKGROUND;
    public static final Image         KUBATSCH_LOGO;
    public static final Font          MAIN_FONT;
    public static final Font          SMALL_FONT;

    public static final Dimension     BUTTON_SIZE;
    public static final ButtonTheme[] BUTTON_THEMES;

    public static final Image[]       TEXTBOX_BACKGROUND;
    public static final Color[]       TEXTBOX_FOREGROUND;
    public static final int           TEXTBOX_HEIGHT;

    public static enum TextBoxSize
    {
        SMALL(210),
        NORMAL(375),
        HUGE(445);

        /**
         * Initializes a new instance of the {@link KuBatschTheme.TextBoxSize}
         * class.
         */
        private TextBoxSize(int size)
        {
            _size = size;
        }

        private int _size;

        /**
         * Gets the size.
         * @return the size
         */
        public int getSize()
        {
            return _size;
        }

    }

    public static final Image     LIST_BACKGROUND;
    public static final Dimension LIST_SIZE;

    public static final Dimension CHOOSER_ITEM_SIZE;

    public static final Image     PADDLE_BACKGROUND;
    public static final int[]     PADDLE_PARTS;
    public static final Insets    PADDLE_HEALTH_INSETS;
    public static final Dimension       PADDLE_SIZE;

    public static final Image     HEALTH_BACKGROUND;
    public static final Image[]   HEALTH_BALLS;
    public static final Dimension HEALTH_BALL_SIZE;

    public static final Dimension SLIDER_SIZE;
    public static final Image     SLIDER_BACKGROUND;
    public static final Image     SLIDER_THUMB;
    public static final Dimension SLIDER_THUMB_SIZE;

    public static final Image[]   BALLS;
    public static final int       BALL_SIZE;

    static
    {
        // general
        MAIN_SIZE = 800;
        MAIN_BACKGROUND = getImage("background.jpg");
        BLOOD_BACKGROUND = getImage("blood-background.gif");
        KUBATSCH_LOGO = getImage("logo.png");
        CONTROL_SETTINGS = getImage("control-settings.png");

        Font mainFont;
        try
        {
            mainFont = Font.createFont(Font.TRUETYPE_FONT,
                    getResourceAsStream("defused.ttf"));
        }
        catch (Exception e)
        {
            mainFont = new Font("Arial", Font.PLAIN, 12);
        }
        MAIN_FONT = mainFont.deriveFont(30f);
        SMALL_FONT = mainFont.deriveFont(20f);

        // buttons
        BUTTON_SIZE = new Dimension(344, 87);
        String[] themes = { "button1", "button2", "button3", "button4",
            "button5" };
        BUTTON_THEMES = new ButtonTheme[themes.length];
        for (int i = 0; i < themes.length; i++)
        {
            BUTTON_THEMES[i] = new ButtonTheme(getImage(themes[i]
                    + "/normal.png"), getImage(themes[i] + "/active.png"),
                    getImage(themes[i] + "/glow.png"), Color.white, new Color(
                            157, 16, 16), BUTTON_SIZE);
        }

        // textbox
        TEXTBOX_FOREGROUND = new Color[] { new Color(157, 16, 16),
            new Color(24, 24, 24) };
        TEXTBOX_BACKGROUND = new Image[] { getImage("textbox/normal.png"),
            getImage("textbox/readonly.png") };
        TEXTBOX_HEIGHT = 45;

        // listbox
        LIST_SIZE = new Dimension(685, 305);
        LIST_BACKGROUND = getImage("list/normal.png");

        // chooser
        CHOOSER_ITEM_SIZE = new Dimension(195, TEXTBOX_HEIGHT);

        // paddle
        PADDLE_BACKGROUND = getImage("paddle/paddle.png");
        PADDLE_PARTS = new int[] { 15, 153 };
        PADDLE_HEALTH_INSETS = new Insets(9, 9, 9, 9);
        PADDLE_SIZE = new Dimension(168, 31);

        // health
        HEALTH_BACKGROUND = getImage("paddle/health.gif");
        HEALTH_BALLS = new Image[at.kubatsch.model.Color.values().length];
        HEALTH_BALLS[at.kubatsch.model.Color.BLUE.getIndex()] = getImage("paddle/blue.png");
        HEALTH_BALLS[at.kubatsch.model.Color.CYAN.getIndex()] = getImage("paddle/cyan.png");
        HEALTH_BALLS[at.kubatsch.model.Color.GOLD.getIndex()] = getImage("paddle/gold.png");
        HEALTH_BALLS[at.kubatsch.model.Color.GRAY.getIndex()] = getImage("paddle/gray.png");
        HEALTH_BALLS[at.kubatsch.model.Color.GREEN.getIndex()] = getImage("paddle/green.png");
        HEALTH_BALLS[at.kubatsch.model.Color.RED.getIndex()] = getImage("paddle/red.png");
        HEALTH_BALLS[at.kubatsch.model.Color.VIOLET.getIndex()] = getImage("paddle/violet.png");

        HEALTH_BALL_SIZE = new Dimension(14, 15);

        // Slider
        SLIDER_SIZE = new Dimension(390, 60);
        SLIDER_BACKGROUND = getImage("slider/background.png");
        SLIDER_THUMB = getImage("slider/tracker.png");
        SLIDER_THUMB_SIZE = new Dimension(31, 54);

        // balls
        BALLS = new Image[at.kubatsch.model.Color.values().length];
        BALLS[at.kubatsch.model.Color.BLUE.getIndex()] = getImage("ball/blue.png");
        BALLS[at.kubatsch.model.Color.CYAN.getIndex()] = getImage("ball/cyan.png");
        BALLS[at.kubatsch.model.Color.GOLD.getIndex()] = getImage("ball/gold.png");
        BALLS[at.kubatsch.model.Color.GRAY.getIndex()] = getImage("ball/gray.png");
        BALLS[at.kubatsch.model.Color.GREEN.getIndex()] = getImage("ball/green.png");
        BALLS[at.kubatsch.model.Color.RED.getIndex()] = getImage("ball/red.png");
        BALLS[at.kubatsch.model.Color.VIOLET.getIndex()] = getImage("ball/violet.png");
        BALL_SIZE = 26;
    }

    public static class ButtonTheme
    {
        private Image         _normalImage;
        private Image         _activeImage;
        private BufferedImage _glowImage;
        private Color         _fontColor;
        private Color         _activeColor;
        private Dimension     _size;

        /**
         * Initializes a new instance of the {@link ButtonTheme} class.
         * @param normalImage
         * @param activeImage
         * @param glowImage
         * @param fontColor
         */
        public ButtonTheme(Image normalImage, Image activeImage,
                Image glowImage, Color fontColor, Color activeColor,
                Dimension size)
        {
            _normalImage = normalImage;
            _activeImage = activeImage;
            _activeColor = activeColor;
            _glowImage = new BufferedImage(BUTTON_SIZE.width,
                    BUTTON_SIZE.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = _glowImage.createGraphics();
            g.drawImage(glowImage, 0, 0, null);
            g.dispose();
            _fontColor = fontColor;
            _size = size;
        }

        /**
         * Gets the activeColor.
         * @return the activeColor
         */
        public Color getActiveColor()
        {
            return _activeColor;
        }

        /**
         * Gets the size.
         * @return the size
         */
        public Dimension getSize()
        {
            return _size;
        }

        /**
         * Gets the glowImage.
         * @return the glowImage
         */
        public BufferedImage getGlowImage()
        {
            return _glowImage;
        }

        /**
         * Gets the normalImage.
         * @return the normalImage
         */
        public Image getNormalImage()
        {
            return _normalImage;
        }

        /**
         * Gets the activeImage.
         * @return the activeImage
         */
        public Image getActiveImage()
        {
            return _activeImage;
        }

        /**
         * Gets the fontColor.
         * @return the fontColor
         */
        public Color getFontColor()
        {
            return _fontColor;
        }
    }

    private static Image getImage(String name)
    {
        try
        {
            return ImageIO.read(getResource(name));
        }
        catch (IOException e)
        {
            return null;
        }
    }

    public static SmallCapsLabel getLabel(String text)
    {
        SmallCapsLabel label = new SmallCapsLabel(text);
        initializeLabel(label);
        return label;
    }

    public static void initializeLabel(SmallCapsLabel label)
    {
        label.setFont(MAIN_FONT);
        label.setSmallCapsFont(SMALL_FONT);
        label.setForeground(Color.white);
    }

    /**
     * Loads a resource as a stream.
     * @param resource The filename of the resource.
     * @return The stream for reading the resource.
     */
    private static InputStream getResourceAsStream(String resource)
    {
        return KuBatschTheme.class.getResourceAsStream(String.format("%s/%s",
                RESOURCES_DIR, resource));
    }

    /**
     * Loads a resource as a URL.
     * @param resource The filename of the resource.
     * @return The URL for accessing the resource.
     */
    private static URL getResource(String resource)
    {
        String full = String.format("%s/%s", RESOURCES_DIR, resource);
        return KuBatschTheme.class.getResource(full);
    }

    /**
     * @return
     */
    public static BloodTextfield getTextBox(TextBoxSize size)
    {
        BloodTextfield txt = new BloodTextfield();
        initializeTextBox(size, txt);
        return txt;
    }

    /**
     * @return
     */
    public static BloodTextfield getNumericalTextBox(TextBoxSize size)
    {
        BloodIntTextfield txt = new BloodIntTextfield();
        initializeTextBox(size, txt);
        return txt;
    }

    /**
     * @return
     */
    public static BloodIntTextfield getNumericalTextBox(TextBoxSize size,
            int minValue, int maxValue)
    {
        BloodIntTextfield txt = new BloodIntTextfield();
        initializeTextBox(size, txt);
        txt.setMinValue(minValue);
        txt.setMaxValue(maxValue);
        return txt;
    }

    private static void initializeTextBox(TextBoxSize size, BloodTextfield txt)
    {
        int width = size.getSize();
        txt.setSize(width, TEXTBOX_HEIGHT);
        txt.setFont(MAIN_FONT.deriveFont(30f));
        txt.setPreferredSize(new Dimension(width, TEXTBOX_HEIGHT));
    }
}
