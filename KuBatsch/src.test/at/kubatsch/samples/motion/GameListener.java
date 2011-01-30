/**
 * author: Martin Balter
 * created on: 26.01.2011
 * filename: GameListener.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.motion;

/**
 * @author Martin Balter
 * This interface is that the Input Devisce knows how to move the 
 */
public interface GameListener 
{
    public void GameListner(Paddle paddle);
    
    public void movePaddle(float movement);
}
