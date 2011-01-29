/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: ChangeViewClickListener.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import at.kubatsch.client.controller.ViewController;

/**
 * Changes the view on the specified container on a click. 
 * @author Daniel Kuschny (dku2375)
 *
 */
public class ChangeViewClickListener extends MouseAdapter
{
    private String _viewId;
    
    /**
     * Initializes a new instance of the {@link ChangeViewClickListener} class.
     * @param container
     * @param view
     */
    public ChangeViewClickListener(String view)
    {
        _viewId = view;
    }

    /**
     * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        ViewController.getInstance().switchToView(_viewId);
    }
}
