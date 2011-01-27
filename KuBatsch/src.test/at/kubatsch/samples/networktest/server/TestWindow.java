/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: PerformanceRunner.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.networktest.server;

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
        add(toolbar, BorderLayout.NORTH);
        
        final Queue<Ball> balls = new LinkedList<Ball>();
        
        // collisionball checkbox
        final JCheckBox addCollisionChk = new JCheckBox("Create Collision Ball?");
        toolbar.add(addCollisionChk);
        addCollisionChk.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                _createCollisionBall = addCollisionChk.isSelected();
            }
        });
        
        // add ball button
        final BallBallCollisionRule ballRule = new BallBallCollisionRule();
        final JButton addBallBtn = new JButton("Add Ball");
        addBallBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Random rnd = new Random();
                
                at.kubatsch.model.Color[] colors = at.kubatsch.model.Color.values();
                at.kubatsch.model.Color rndColor = colors[rnd.nextInt(colors.length)];
                
                Ball newBall = new Ball(rndColor);
                if(_createCollisionBall)
                {
                    newBall.addCollisionRule(ballRule);
                }
                newBall.setPosition(0.5f,0.5f);
                // random direction with random speed
                float speedX = (rnd.nextInt(10) + 5) / 1000.0f;
                if (rnd.nextBoolean())
                {
                    speedX = -speedX;
                }

                float speedY = (rnd.nextInt(10) + 5) / 1000.0f;
                if (rnd.nextBoolean())
                {
                    speedY = -speedY;
                }
                
                newBall.setVelocity(speedX, speedY);
                
                balls.add(newBall);
                _collidablePanel.addCollidable("BALL",newBall);
            }
        });
        toolbar.add(addBallBtn);
        
        // remove ball button
        final JButton removeBallButton = new JButton("Remove Ball");
        removeBallButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Ball b = balls.poll();
                _collidablePanel.removeCollidable(b);
            }
        });
        toolbar.add(removeBallButton);
        
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
    }
}
