/**
 * This file is part of KuBatsch.
 *   created on: 22.12.2010
 *   filename: PrimeCalculator.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.eventsample;

import at.kubatsch.util.Event;
import at.kubatsch.util.EventWrapper;

/**
 * This class calculates prime numbers till a specified limit and reports them
 * using an event.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class PrimeCalculator extends Thread
{
    private int _limit;

    /**
     * Initializes a new instance of the {@link PrimeCalculator} class.
     * @param limit Till which number to search for primes.
     */
    public PrimeCalculator(int limit)
    {
        _limit = limit;
    }

    /**
     * Performs the calculation of primes.
     * @see java.lang.Thread#run()
     */
    @Override
    public void run()
    {
        for (int i = 2; i < _limit; i++)
        {
            if(isPrime(i))
            {
                // we found a prime: notify handlers
                // fire event using the internal event structure
                // bypass a new eventargs instance containing the event data.
                _numberFound.fireEvent(new PrimeNumberEventArgs(i));
            }
        }
    }
    
    /**
     * Gets a value indicating whether the specified number is a prime.
     * @param number The number to check.
     */
    private boolean isPrime(int number)
    {
        // search for a number which modulo is zero
        for (int i = 2; i < number; i++)
        {
            // check if number can be divided by i
            if( (number % i) == 0)
            {
                // if so: no prime.
                return false;
            }
        }
        // no divider found: success!
        return true;
    }

    // !Event Part!

    // create a new private event for internal usage like event raising
    private final Event<PrimeNumberEventArgs>       _numberFound = new Event<PrimeNumberEventArgs>(
                                                                   this);
    // create a public wrapper so we can add listeners from outside
    // make it final so we can't modify it from outside.
    public final EventWrapper<PrimeNumberEventArgs> numberFound  = new EventWrapper<PrimeNumberEventArgs>(
                                                                   _numberFound);
}
