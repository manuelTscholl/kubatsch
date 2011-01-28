/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: PerformanceRunner.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.networktest.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import at.kubatsch.model.PlayerPosition;
import at.kubatsch.model.Ball;
import at.kubatsch.model.BallBallCollisionRule;
import at.kubatsch.model.PlayerHitArea;
import at.kubatsch.model.HitPanelReflectRule;

/**
 * @author Daniel Kuschny (dku2375)
 *
 */
public class TestWindow extends JFrame
{
    private CollidablePanel _collidablePanel;
    private boolean _createCollisionBall;
    public static String[] _args;
    /**
     * Initializes a new instance of the {@link TestWindow} class.
     */
    public TestWindow()
    {
        setTitle("Collision Test");
        setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                pack();
            }
        });
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowOpened(WindowEvent e)
            {
                _collidablePanel.start();
            }

            @Override
            public void windowClosing(WindowEvent e)
            {
                _collidablePanel.stop();
            }

        });
        
        
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);        
        
        // add ball button
        final BallBallCollisionRule ballRule = new BallBallCollisionRule();
        
        
        _collidablePanel = new CollidablePanel();
        
        final HitPanelReflectRule hitAreaRule = new HitPanelReflectRule();
        PlayerHitArea area;
        
        area = new PlayerHitArea(PlayerPosition.SOUTH);
        area.addCollisionRule(hitAreaRule);
        _collidablePanel.addCollidable("HITAREA",area);
        
        area = new PlayerHitArea(PlayerPosition.NORTH);
        area.addCollisionRule(hitAreaRule);
        _collidablePanel.addCollidable("HITAREA",area);
        
        area = new PlayerHitArea(PlayerPosition.EAST);
        area.addCollisionRule(hitAreaRule);
        _collidablePanel.addCollidable("HITAREA",area);
        
        area = new PlayerHitArea(PlayerPosition.WEST);
        area.addCollisionRule(hitAreaRule);
        _collidablePanel.addCollidable("HITAREA",area);
        
        
        add(_collidablePanel, BorderLayout.CENTER);
        
        pack();
    }
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new TestWindow().setVisible(true);
            }
        });
        
        _args= args;
    }
}
