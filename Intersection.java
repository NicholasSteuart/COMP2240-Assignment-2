/* COMP2240 Assignment 2
 * File: Intersection.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 9/9/24
 * Date Last Modified: 21/9/24
 * Description: Implements the functionality of the Intersection in the Problem 1. It utilises Semaphores to prevent
 * collisions, deadlock, livelock and starvation of MAC operations.
 */

// PACKAGES //

import java.util.concurrent.Semaphore;

public class Intersection 
{
    // CLASS VARIABLES //

    private final Semaphore semaphore;
    private int trail1Count = 0, trail2Count = 0;

    // CONSTRUCTORS //

    //PRE-CONDITION:
    //POST-CONDITION:
    public Intersection()
    {
        semaphore = null;
    }
    //PRE-CONDITION:
    //POST-CONDITION:
    public Intersection(int permit)
    {
        this.semaphore = new Semaphore(permit);
    }

    // METHODS //

    //PRE-CONDITION:
    //POST-CONDITION:
    public boolean enterIntersection() throws InterruptedException 
    {   
        try
        {
            semaphore.acquire();
            return true;
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            return false;
        }
    }
    //PRE-CONDITION:
    //POST-CONDITION:
    public boolean exitIntersection() 
    {
        semaphore.release();
        return true;
    }
    //PRE-CONDITION:
    //POST-CONDITION:
    public String updateTrailCompletions(String trail)
    {
        if(trail.charAt(trail.length() - 1) == '1') trail1Count++;
        else trail2Count++;
        
        return "Total crossed in Trail1: " + trail1Count + " Trail2: " + trail2Count;
    }
}
