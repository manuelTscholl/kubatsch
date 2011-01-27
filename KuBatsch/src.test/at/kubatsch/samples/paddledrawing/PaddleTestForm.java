/**
 * This file is part of KuBatsch.
 *   created on: 26.01.2011
 *   filename: PaddleTestForm.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.paddledrawing;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import at.kubatsch.model.Color;

/**
 * A testapplication for rendering paddles. 
 * @author Daniel Kuschny (dku2375)
 */
public class PaddleTestForm extends JFrame
{
    /**
     * A unique serialization id.
     */
    private static final long serialVersionUID = -8910759630574031330L;
    private PaddlePaintPanel _panel;

    /**
     * Initializes a new instance of the {@link PaddleTestForm} class.
     */
    public PaddleTestForm()
    {
        setTitle("Paddle Rendering Test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        
        _panel = new PaddlePaintPanel();
        add(_panel, BorderLayout.CENTER);

        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        add(controls, BorderLayout.NORTH);
        
        // a slider for adjusting the width 
        final JSlider sizeSlider = new JSlider(10, 100, 10);
        sizeSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                _panel.setPaddleWidth((int)(_panel.getWidth() *(sizeSlider.getValue() / 100.0)));
            }
        });
        controls.add(sizeSlider);
        
        // a slider for adjusting the health 
        final JSlider healthSlider = new JSlider(0, 100, 50);
        healthSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                _panel.setHealth((healthSlider.getValue() / 100.0f));
            }
        });
        controls.add(healthSlider);
              
        // a combobox for selecting the color 
        final JComboBox colorComboBox = new JComboBox(Color.values());
        colorComboBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                _panel.setPaddleColor((Color)colorComboBox.getSelectedItem());
            }
        });
        controls.add(colorComboBox);
        
        pack();
    }  
    
    /**
     * The applications main entry point. 
     * @param args not used. 
     */
    public static void main(String[] args)
    {
        new PaddleTestForm().setVisible(true);
    }
}
