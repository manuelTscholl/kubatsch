/**
 * author: Daniel Kuschny (dku2375)
 * created on: 28.01.2011
 * filename: StartServerException.java
 * project: KuBatsch
 */
package at.kubatsch.client.controller;

/**
 * @author Daniel Kuschny (dku2375)
 */
public class StartServerException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = 6326978850630286016L;
   
    public static final String PORT_RESERVED = "Port reserved";

    /**
     * Initializes a new instance of the {@link StartServerException} class.
     */
    public StartServerException()
    {
        super();
    }

    /**
     * Initializes a new instance of the {@link StartServerException} class.
     * @param message
     * @param cause
     */
    public StartServerException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Initializes a new instance of the {@link StartServerException} class.
     * @param message
     */
    public StartServerException(String message)
    {
        super(message);
    }

    /**
     * Initializes a new instance of the {@link StartServerException} class.
     * @param cause
     */
    public StartServerException(Throwable cause)
    {
        super(cause);
    }
}
