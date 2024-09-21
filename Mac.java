/* COMP2240 Assignment 2
 * File: Mac.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 9/9/24
 * Date Last Modified: 21/9/24
 * Description: Implements the functionality for the Medical-Supply Automated Cart (MAC) used in Problem 1.
 */
public class Mac implements Runnable
{
    // CLASS VARIABLES //

    private final String ID;        //MAC's ID
    private String status;          //Contains the status of the MAC, whether it has "Stock" or "Empty"
    private String destination;     //Contains the destination the MAC must travel to
    private int totalCrossings;
    private final Intersection intersection;

    // CONSTRUCTORS // 

    //PRE-CONDITION: No Pre-conditions
    //POST-CONDITION: Default Constructor instantiated with default values assigned to Class Variables id and status
    public Mac() 
    {
        ID = "";
        status = "";
        destination = "";
        totalCrossings = 0;
        intersection = null;
    }
    //PRE-CONDITION: No Pre-conditions
    //POST-CONDITION: Specialised Constructor instantiated with Parameters id and status assigned to Class Variables id and status respectively
    public Mac(String ID, String status, String destination, Intersection intersection)
    {
        this.ID = ID;
        this.status = status;
        this.destination = destination;
        this.intersection = intersection;
    }

    // METHODS //

    //PRE-CONDITION:
    //POST-CONDITION:
    @Override
    public void run()
    {
        int currentCrosses = 0;
        while(currentCrosses < totalCrossings)
        {
            try 
            {
                System.out.println(ID + " (" + status + "): Waiting at the Intersection. Going towards " + destination);
                if(intersection.enterIntersection())
                {
                    crossCheckpoints();
                    intersection.exitIntersection();   
                    currentCrosses++;
                    executeTask();
                    System.out.println(intersection.updateTrailCompletions(destination));
                }
            } 
            catch (InterruptedException e) 
            {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(ID +  ": Finished.");
    }
    //PRE-CONDITION:
    //POST-CONDITION:
    public void crossCheckpoints() throws InterruptedException
    {
        System.out.println(ID + " (" + status + "): Crossing intersection Checkpoint 1.");
        Thread.sleep(50);
        System.out.println(ID + " (" + status + "): Crossing intersection Checkpoint 2.");
        Thread.sleep(50);
        System.out.println(ID + " (" + status + "): Crossing intersection Checkpoint 3.");
        Thread.sleep(50);
        System.out.println(ID + " (" + status + "): Crossed the intersection.");
    }
    //PRE-CONDITION:
    //POST-CONDITION:
    public void executeTask()
    {
        status = (status.equals("Empty")) ? "Stock" : "Empty";
        changeDirection();
    }
    //PRE-CONDITION:
    //POST-CONDITION:
    public void changeDirection()
    {
        switch(destination)
        {
            case "CSR1" -> destination = "ED1";
            case "CSR2" -> destination = "ED2";
            case "ED1" -> destination = "CSR1";
            case "ED2" -> destination = "CSR2";
        }
    }

    // MUTATOR //

    //PRE-CONDITION:
    //POST-CONDITION:
    public void setTotalCrossings(int totalCrossings)
    {
        this.totalCrossings = totalCrossings;
    }
}
