/**
 * author: Manuel Tscholl(mts3970)
 * created on: 26.01.2011
 * filename: MaximumPlayerReached.java
 * project: KuBaTsch
 */
package at.kubatsch.samples.networktest;

/**
 * @author Manuel Tscholl (mts3970)
 *
 */
public class MaximumPlayerReachedException extends RuntimeException
{

    /**
     * Initializes a new instance of the {@link MaximumPlayerReachedException} class.
     */
    public MaximumPlayerReachedException()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Initializes a new instance of the {@link MaximumPlayerReachedException} class.
     * @param message
     * @param cause
     */
    public MaximumPlayerReachedException(String message, Throwable cause)
    {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * Initializes a new instance of the {@link MaximumPlayerReachedException} class.
     * @param message
     */
    public MaximumPlayerReachedException(String message)
    {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * Initializes a new instance of the {@link MaximumPlayerReachedException} class.
     * @param cause
     */
    public MaximumPlayerReachedException(Throwable cause)
    {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
