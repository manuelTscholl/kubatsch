/**
 * author: Daniel Kuschny (dku2375)
 * created on: 04.02.2011
 * filename: ClientMessage.java
 * project: KuBatsch
 */
package at.kubatsch.model.message;

import java.io.Serializable;

/**
 * This is the base interface for sending data from the client to the server or
 * from the server to the client
 * @author Daniel Kuschny (dku2375)
 * 
 */
public abstract interface INetworkMessage extends Serializable
{
    public String getMessageId();
}
