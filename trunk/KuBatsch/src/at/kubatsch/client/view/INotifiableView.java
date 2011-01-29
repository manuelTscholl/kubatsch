/**
 * author: Daniel Kuschny (dku2375)
 * created on: 28.01.2011
 * filename: INotifiableView.java
 * project: KuBatsch
 */
package at.kubatsch.client.view;

/**
 * A view which gets informed if it gets displayed or hidden.
 * @author Daniel Kuschny (dku2375)
 *
 */
public interface INotifiableView
{
    /**
     * Called as the view gets displayed.
     */
    public void viewDisplaying();
    
    /**
     * Called as the view gets hidden.
     */
    public void viewHidding();
    
}
