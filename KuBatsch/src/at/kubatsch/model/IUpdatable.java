/**
 * This file is part of KuBatsch.
 *   created on: 24.01.2011
 *   filename: IUpdatable.java
 *   project: KuBatsch
 */
package at.kubatsch.model;

/**
 * {@link ICollidable}s implementing this interface will be notified upon the
 * main game loop to update theirselfes for the next timeslice. 
 * @author Daniel Kuschny (dku2375)
 *
 */
public interface IUpdatable extends ICollidable
{
    public void update();
}
