/**
 * This file is part of KuBatsch.
 *   created on: 26.01.2011
 *   filename: HealthBarPainter.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import at.kubatsch.model.Color;

/**
 * This painter allows rendering of health bars with two balls on each edge.
 * @author Daniel Kuschny (dku2375)
 *
 */
public class HealthBarPainter
{
    /**
     * Paints the health bar. 
     * @param g the graphics to render into 
     * @param targetBounds the bounds to render into 
     * @param color the color 
     * @param health the health 
     */
    public static void paint(Graphics g, Rectangle targetBounds, Color color,
            float health)
    {
        // ball offset
        targetBounds.x += KuBatschTheme.HEALTH_BALL_SIZE.width;
        targetBounds.width -= (KuBatschTheme.HEALTH_BALL_SIZE.width * 2);
        
        int realWidth = (int)(targetBounds.width * health);
        int diff = targetBounds.width - realWidth;
        
        targetBounds.x += diff/2;
        targetBounds.width -= diff;
        
        // paint health 
        Image img = KuBatschTheme.HEALTH_BACKGROUND;
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        g.drawImage(img, targetBounds.x, targetBounds.y, (int)targetBounds.getMaxX(), (int)targetBounds.getMaxY(), 
                         0, 0, w, h, null);
        
        // paint balls
        if(color == null)
        {
            color = Color.GRAY;
        }
        
        Image ball = KuBatschTheme.HEALTH_BALLS[color.getIndex()];
        // left
        int ballY = targetBounds.y - 1;
        g.drawImage(ball, targetBounds.x - KuBatschTheme.HEALTH_BALL_SIZE.width, ballY, null);
        // right
        g.drawImage(ball, (int)targetBounds.getMaxX(), ballY, null);
        
        
    }
}
