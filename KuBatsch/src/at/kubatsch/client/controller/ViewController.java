/**
 * author: Daniel Kuschny (dku2375)
 * created on: 28.01.2011
 * filename: ViewManager.java
 * project: KuBatsch
 */
package at.kubatsch.client.controller;

import java.awt.CardLayout;
import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

import at.kubatsch.client.view.INotifiableView;

/**
 * A view controller for managing a list of views within a {@link Container}
 * using the {@link CardLayout}.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class ViewController
{
    private Container               _container;
    private CardLayout              _layout;
    private Map<String, JComponent> _views;
    private String                  _currentView;

    private static ViewController   _instance;

    /**
     * Gets the globally registered instance.
     * @see ViewController#createGlobalInstance(Container)
     * @return {@link ViewController} instance
     */
    public static ViewController getInstance()
    {
        return _instance;
    }

    /**
     * Initializes a GlobalInstance of the {@link ViewController}
     * @param container
     */
    public static void createGlobalInstance(Container container)
    {
        _instance = new ViewController(container);
    }

    /**
     * Initializes a new instance of the {@link ViewController} class.
     * @param container for the {@link ViewController}
     */
    private ViewController(Container container)
    {
        _container = container;
        _layout = (CardLayout) _container.getLayout();
        _views = new HashMap<String, JComponent>();
    }

    /**
     * Registers a new view within the current viewcontroller.
     * @param viewId the view id
     * @param view the view
     */
    public void registerView(String viewId, JComponent view)
    {
        if (_views.isEmpty())
        {
            _currentView = viewId;
        }
        _container.add(view, viewId);
        _views.put(viewId, view);
    }
    
    /**
     * Gets the id of the currently displayed view..
     * @return the view id.
     */
    public String getCurrentViewId()
    {
        return _currentView;
    }

    /**
     * Shows the specified view.
     * @param viewId the id of the regisered view to display
     */
    public void switchToView(String viewId)
    {
        if(_currentView.equalsIgnoreCase(viewId)) return;
        JComponent current = _views.get(_currentView);
        JComponent newView = _views.get(viewId);
        if (newView == null)
            return;

        if(newView instanceof INotifiableView)
        {
            ((INotifiableView)newView).viewDisplaying();
        }
        if(current instanceof INotifiableView)
        {
            ((INotifiableView)current).viewHidding();
        }
        _layout.show(_container, viewId);
        _currentView = viewId;
    }

    /**
     * Gets the view registered with the specified id.
     * @param panelId the view id
     * @return the view
     */
    @SuppressWarnings("unchecked")
    public <T extends JComponent> T getView(String panelId)
    {
        return (T)_views.get(panelId);
    }
}
