/**
 * This file is part of KuBatsch.
 *   created on: 22.12.2010
 *   filename: EventSample.java
 *   project: KuBatsch
 */
package at.kubatsch.samples.eventsample;

import at.kubatsch.util.IEventHandler;

/**
 * This sample shows how to use the event library using an asynchronous
 * operation .
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class EventSample
{
    /**
     * Runs the sample
     * @param args
     */
    public static void main(String[] args)
    {
        // find all prime numbers till this one:
        int limit = 100000;

        // create our prime calculator which will
        // notify any number found via event.
        PrimeCalculator calculator = new PrimeCalculator(limit);

        // add a new handler which simply prints the found numbers
        calculator.numberFound
                .addHandler(new IEventHandler<PrimeNumberEventArgs>()
                {
                    @Override
                    public void fired(Object sender, PrimeNumberEventArgs e)
                    {
                        // print it 
                        System.out.printf("Found Prime:%d %n", e.getPrime());
                    }
                });
        // we can add multiple handlers which all get notified
        // calculator.numberFound.addHandler(new PrintToFileHandler());
        // calculator.numberFound.addHandler(new StoreInDataBaseHandler());
        // calculator.numberFound.addHandler(new RefreshUiHandler());
        
        // in c# we would have something like: 
        // (handlers would be methods with a special signature: delegates)
        // calculator.numberFound += printToConsole;
        // calculator.numberFound += printToFile;
        // calculator.numberFound += storeInDatabase;
        
        // run the job
        calculator.start();
    }
}
