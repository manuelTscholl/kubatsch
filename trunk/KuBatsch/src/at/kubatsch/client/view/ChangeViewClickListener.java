/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: ChangeViewClickListener.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Changes the view on the specified container on a click. 
 * @author Daniel Kuschny (dku2375)
 *
 */
public class ChangeViewClickListener extends MouseAdapter
{
    private Container _container;
    private String _viewId;
    
    /**
     * Initializes a new instance of the {@link ChangeViewClickListener} class.
     * @param container
     * @param view
     */
    public ChangeViewClickListener(Container container, String view)
    {
        _container = container;
        _viewId = view;
    }

    /**
     * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        CardLayout cl = (CardLayout)(_container.getLayout());
        cl.show(_container, _viewId);
    }
}
