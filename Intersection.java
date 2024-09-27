/* COMP2240 Assignment 2
 * File: Intersection.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 9/9/24
 * Date Last Modified: 27/9/24
 * Description: Implements the functionality of the Intersection in the Problem 1. It utilises Semaphores to prevent
 * collisions, deadlock, livelock and starvation of MAC operations.
 */

// PACKAGES //

import java.util.concurrent.Semaphore;

public class Intersection 
{
    // CLASS VARIABLES //

    private final Semaphore semaphore;              //Semaphore used to ensure mutual exclusion
    private int trail1Count = 0, trail2Count = 0;   //Counts the total amount of times trail 1 and trail 2 have been crossed respectively 

    // CONSTRUCTORS //

    //PRE-CONDITION: No pre-conditions
    //POST-CONDITION: Default Constructor instantiated

    public Intersection()
    {
        semaphore = null;
    }
    //PRE-CONDITION: No pre-conditions
    //POST-CONDITION: Specialised Constructor instantiated with Parameters permit parsed into a newly instantiated Semaphore to determine it's value

    public Intersection(int permit)
    {
        this.semaphore = new Semaphore(permit);
    }

    // METHODS //

    //PRE-CONDITION: Intersection Class instantiated with an instantiated Semaphore
    //POST-CONDITION: Either returns true if the semaphore has been acquired by a Thread or false if the Thread was interrupted.
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
    //PRE-CONDITION: Intersection Class instantiated with an instantiated Semaphore
    //POST-CONDITION: Returns true
    public boolean exitIntersection() 
    {
        semaphore.release();        //Semaphore signals its release
        return true;
    }
    //PRE-CONDITION: Parameter trail must be a String of the value "ED1", "ED2", "CSR1", or "CSR2"
    //POST-CONDITION: Returns a String containing the updated trail completion information
    public String updateTrailCompletions(String trail)
    {
        if(trail.charAt(trail.length() - 1) == '1') trail1Count++; //IF trail ends in a 1, increment trail 1 count, else increment trail 2 count
        else trail2Count++;
        
        return "Total crossed in Trail1: " + trail1Count + " Trail2: " + trail2Count;
    }
}
