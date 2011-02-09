/**
 * This file is part of KuBatsch.
 *   created on: 26.01.2011
 *   filename: PaddlePainter.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import at.kubatsch.model.Color;

/**
 * Paints the Gui for the paddle
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class PaddlePainter
{
    public static void paint(Graphics g, Rectangle targetBounds, Color color,
            float health)
    {
        Image paddle = KuBatschTheme.PADDLE_BACKGROUND;
        int[] parts = KuBatschTheme.PADDLE_PARTS;
        int imageWidth = paddle.getWidth(null);
        int imageHeight = paddle.getHeight(null);

        int rightSideWidth = imageWidth - parts[1];

        // draw left region
        g.drawImage(paddle, targetBounds.x, targetBounds.y, targetBounds.x
                + parts[0], targetBounds.y + imageHeight, 0, 0, parts[0], imageHeight,
                null);
        // center region
        g.drawImage(paddle, targetBounds.x + parts[0], targetBounds.y, 
                            (int)targetBounds.getMaxX() - rightSideWidth, targetBounds.y + imageHeight, 
                            parts[0], 0, parts[1], imageHeight, null);
        // draw right region
        g.drawImage(paddle, (int)targetBounds.getMaxX() - rightSideWidth, targetBounds.y, (int)targetBounds.getMaxX(), targetBounds.y + imageHeight,
                            parts[1], 0, imageWidth, imageHeight, null);
        
        // health bar
        targetBounds.x += KuBatschTheme.PADDLE_HEALTH_INSETS.left;
        targetBounds.y += KuBatschTheme.PADDLE_HEALTH_INSETS.top;
        targetBounds.width -= (KuBatschTheme.PADDLE_HEALTH_INSETS.left + KuBatschTheme.PADDLE_HEALTH_INSETS.right);
        targetBounds.height -= (KuBatschTheme.PADDLE_HEALTH_INSETS.top + KuBatschTheme.PADDLE_HEALTH_INSETS.bottom);
        
        HealthBarPainter.paint(g, targetBounds, color, health);
    }
}
