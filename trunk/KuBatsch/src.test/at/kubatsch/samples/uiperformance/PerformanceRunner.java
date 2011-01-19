/**
 * This file is part of KuBatsch.
 *   created on: 02.01.2011
 *   filename: PerformanceRunner.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.uiperformance;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Daniel Kuschny (dku2375)
 *
 */
public class PerformanceRunner extends JFrame implements WindowListener, ComponentListener
{
    private GamePanel _panel;
    /**
     * Initializes a new instance of the {@link PerformanceRunner} class.
     */
    public PerformanceRunner()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Performance Demo");
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
                _panel.addBall(new Color(r,g,b));
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
                _panel.removeBall();
            }
        });
        toolbar.add(removeBallButton);
        
        _panel = new GamePanel();
        add(_panel, BorderLayout.CENTER);
        
        pack();
    }
    
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e)
        {
        }
        
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                PerformanceRunner runner = new PerformanceRunner();
                runner.setVisible(true);
            }
        });
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
