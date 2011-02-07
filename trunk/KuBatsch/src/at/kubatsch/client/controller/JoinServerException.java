/**
 * author: Daniel Kuschny (dku2375)
 * created on: 28.01.2011
 * filename: JoinServerException.java
 * project: KuBatsch
 */
package at.kubatsch.client.controller;

/**
 * Exception für the {@link JoinServerController}. Has standard static messages
 * @author Daniel Kuschny (dku2375)
 */
public class JoinServerException extends Exception
{
    public static final String UNKNOWN = "Connection Failed";
    public static final String INVALID_HOST_FORMAT = "Use IP:Port or Host:Port as Format";
    public static final String HOST_NOT_FOUND = "Host Not Found";
    public static final String REFUSED = "Connection Refused";
    public static final String SERVER_FULL = "Server is Full";
    public static final String SERVER_INFO_NULL = "The ServerInfo is null";

    private static final long serialVersionUID = -6640577586075667104L;

    /**
     * Initializes a new instance of the {@link JoinServerException} class.
     */
    public JoinServerException()
    {
        super();
    }

    /**
     * Initializes a new instance of the {@link JoinServerException} class.
     * @param message
     * @param cause
     */
    public JoinServerException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Initializes a new instance of the {@link JoinServerException} class.
     * @param message
     */
    public JoinServerException(String message)
    {
        super(message);
    }

    /**
     * Initializes a new instance of the {@link JoinServerException} class.
     * @param cause
     */
    public JoinServerException(Throwable cause)
    {
        super(cause);
    }

}
