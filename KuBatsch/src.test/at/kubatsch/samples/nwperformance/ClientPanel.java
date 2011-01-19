/**
 * This file is part of KuBatsch.
 *   created on: 03.01.2011
 *   filename: ClientPanel.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.nwperformance;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;

/**
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class ClientPanel extends JComponent
{
    private PlayerInfo[] _nfos;
    private Color[]      _colors;

    /**
     * Gets the nfos.
     * @return the nfos
     */
    public synchronized PlayerInfo[] getNfos()
    {
        return _nfos;
    }

    /**
     * Sets the nfos.
     * @param nfos the nfos to set
     */
    public synchronized void setNfos(PlayerInfo[] nfos)
    {
        _nfos = nfos;
        if (_colors == null || _colors.length != _nfos.length)
        {
            _colors = new Color[_nfos.length];
            for (int i = 0; i < nfos.length; i++)
            {
                _colors[i] = randomColor();
            }
        }
        repaint();
    }

    /**
     * Initializes a new instance of the {@link ClientPanel} class.
     */
    public ClientPanel()
    {
        setDoubleBuffered(true);
    }

    /**
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        PlayerInfo[] nfos = getNfos();
        if (nfos.length == 0)
            return;
        int interval = getHeight() / nfos.length;

        for (int i = 0; i < nfos.length; i++)
        {
            g.setColor(_colors[i]);

            int y = i * interval;
            g.drawString(
                    String.format("%s - %d", nfos[i].getName(),
                            nfos[i].getHealth()), 10, y + 20);

            int x = (int) (getWidth() * nfos[i].getPosition());
            g.fillOval(x, y, 5, 5);
        }
    }

    private Color randomColor()
    {
        Random rnd = new Random();
        int r = rnd.nextInt(255);
        int g = rnd.nextInt(255);
        int b = rnd.nextInt(255);
        return new Color(r, g, b);

    }
}
