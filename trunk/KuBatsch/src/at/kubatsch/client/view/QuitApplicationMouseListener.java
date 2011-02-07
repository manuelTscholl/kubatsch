/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: QuitApplicationMouseListener.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

/**
 * This mouse listener can be used to close a window and quit the application. 
 * @author Daniel Kuschny (dku2375)
 */
public class QuitApplicationMouseListener extends MouseAdapter
{
    private Component _component;

    /**
     * Initializes a new instance of the {@link QuitApplicationMouseListener} class.
     * @param component the component in the form to be closed. 
     */
    public QuitApplicationMouseListener(Component component)
    {
        _component = component;
    }

    /**
     * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        Frame frame = (Frame) SwingUtilities.getRoot(_component);
        frame.setVisible(false);
        System.exit(0);
    }
}
