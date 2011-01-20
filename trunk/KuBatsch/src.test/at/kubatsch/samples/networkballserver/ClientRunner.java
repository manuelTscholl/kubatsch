/**
 * author: Manuel Tscholl(mts3970)
 * created on: 19.01.2011
 * filename: ClientRunner.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.networkballserver;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import at.kubatsch.samples.uiperformance.GamePanel;

/**
 * @author Manuel Tscholl (mts3970)
 *
 */
public class ClientRunner extends JFrame implements WindowListener, ComponentListener
{
    private ClientGamePanel _panel;
    /**
     * Initializes a new instance of the {@link ServerRunner} class.
     */
    public ClientRunner(ClientGamePanel panel)
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Performance Demo Client");
        addWindowListener(this); 
        addComponentListener(this);
        setLayout(new BorderLayout());
        
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        add(toolbar, BorderLayout.NORTH);
        
        
        // allow resize toggle
        final JCheckBox allowResizeBtn = new JCheckBox("Allow Resize", true);
        allowResizeBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setResizable(allowResizeBtn.isSelected());
            }
        });
        toolbar.add(allowResizeBtn);
        
        // separator
        toolbar.add(new JToolBar.Separator());
        
        // add ball button
        final JButton addBallBtn = new JButton("Add Ball");
        addBallBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Random rnd = new Random();
                int r = rnd.nextInt(255);
                int g = rnd.nextInt(255);
                int b = rnd.nextInt(255);
                //_panel.addBall(new Color(r,g,b));
            }
        });
        toolbar.add(addBallBtn);
        
        // add ball button
        final JButton removeBallButton = new JButton("Remove Ball");
        removeBallButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //_panel.removeBall();
            }
        });
        toolbar.add(removeBallButton);        
        _panel = panel;
        add(_panel, BorderLayout.CENTER);
        
        pack();
    }
    
    
    @Override
    public void windowOpened(WindowEvent e)
    {
        _panel.start();
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        _panel.stop();
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
    }

    /**
     * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
     */
    @Override
    public void componentResized(ComponentEvent e)
    {
        pack();
    }

    /**
     * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
     */
    @Override
    public void componentMoved(ComponentEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    /** 
     * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
     */
    @Override
    public void componentShown(ComponentEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
     */
    @Override
    public void componentHidden(ComponentEvent e)
    {
        // TODO Auto-generated method stub
        
    }
}
