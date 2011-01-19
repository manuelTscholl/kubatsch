package at.kubatsch.samples.eventsample;

import at.kubatsch.util.EventArgs;

/**
 * Can contain a single prime number as event data.
 * @author Daniel Kuschny (dku2375)
 */
public class PrimeNumberEventArgs extends EventArgs
{
    private int _prime;
    
    /**
     * Initializes a new instance of the {@link PrimeNumberEventArgs} class.
     * @param prime
     */
    public PrimeNumberEventArgs(int prime)
    {
        super();
        _prime = prime;
    }

    /**
     * Gets the prime.
     * @return the prime
     */
    public int getPrime()
    {
        return _prime;
    }
    
}