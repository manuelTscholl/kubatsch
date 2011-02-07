/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: SmallCapsUtility.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class SmallCapsUtility
{
    /**
     * Calculates the size of a small caps text.
     * @param c the component to get the FontMetrics from
     * @param text the text to render
     * @param normalFont the font used for big caps
     * @param smallFont the font used for small caps.
     * @return the size of the text.
     */
    public static Dimension calculateSize(JComponent c, String text,
            Font normalFont, Font smallFont)
    {
        if(text == null) return new Dimension();
        char[] chars = text.toCharArray();

        FontMetrics normalCapsFm = c.getFontMetrics(normalFont);
        int normalCapsH = normalCapsFm.getAscent();

        FontMetrics smallCapsFm = c.getFontMetrics(smallFont);
        int smallCapsH = smallCapsFm.getAscent();

        int fullH = Math.max(normalCapsH, smallCapsH);
        int x = 0;

        for (int i = 0; i < chars.length; i++)
        {
            char ch = chars[i];
            FontMetrics fm = Character.isLowerCase(ch) ? smallCapsFm : normalCapsFm;
            x += fm.charWidth(ch);
        }

        return new Dimension(x, fullH);
    }
    
    /**
     * Renders a small caps text. 
     * @param g The graphics to draw into 
     * @param c the component to get the FontMetrics from
     * @param text the text to render
     * @param normalFont the font used for big caps
     * @param smallFont the font used for small caps.
     * @param x the x position to draw 
     * @param y the y position to draw
     */
    public static void render(Graphics g, JComponent c, String text, Font normalFont, Font smallCapsFont, int x, int y)
    {
        if(text == null) return;
        char[] chars = text.toCharArray();

        FontMetrics normalCapsFm = c.getFontMetrics(normalFont);
        int normalCapsH = normalCapsFm.getAscent();

        FontMetrics smallCapsFm = c.getFontMetrics(smallCapsFont);
        int smallCapsH = smallCapsFm.getAscent();

        int fullH = Math.max(normalCapsH, smallCapsH);

        for (int i = 0; i < chars.length; i++)
        {
            char ch = chars[i];

            FontMetrics fm = Character.isLowerCase(ch) ? smallCapsFm : normalCapsFm;
            Font font = Character.isLowerCase(ch) ? smallCapsFont : normalFont;

            g.setFont(font);
            int w = fm.charWidth(ch);
            g.drawString("" + chars[i], x, y + fullH);

            x += w;
        }
    }
}
