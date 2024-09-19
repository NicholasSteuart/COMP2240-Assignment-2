/* COMP2240 Assignment 2
 * File: Mac.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 9/9/24
 * Date Last Modified: 9/9/24
 * Description: Implements the functionality for the Medical-Supply Automated Cart (MAC) used in Problem 1.
 */
public class Mac implements Runnable
{
    // CLASS VARIABLES //

    private String id;              //MAC's ID
    private String status;          //Contains the status of the MAC, whether it has "Stock" or "Empty"
    private String destination;     //Contains the destination the MAC must travel to
    private int totalCrossings;
    private final Intersection intersection;

    // CONSTRUCTOR // 

    //PRE-CONDITION: No Pre-conditions
    //POST-CONDITION: Default Constructor instantiated with default values assigned to Class Variables id and status
    public Mac() 
    {
        id = "";
        status = "";
        destination = "";
        totalCrossings = 0;
        intersection = null;
    }
    //PRE-CONDITION: No Pre-conditions
    //POST-CONDITION: Specialised Constructor instantiated with Parameters id and status assigned to Class Variables id and status respectively
    public Mac(String id, String status, String destination, Intersection intersection)
    {
        this.id = id;
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
                System.out.println(id + " (" + status + "): Waiting at the Intersection. Going towards " + destination);
                if(intersection.enterIntersection())
                {
                    System.out.println(id + " (" + status + "): Crossing intersection " + intersection.passCheckpoint(1));
                    System.out.println(id + " (" + status + "): Crossing intersection " + intersection.passCheckpoint(2));
                    System.out.println(id + " (" + status + "): Crossing intersection " + intersection.passCheckpoint(3));
                    System.out.println(id + " (" + status + "): Crossed the intersection.");
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
        System.out.println(id +  ": Finished.");
    }

    //PRE-CONDITION:
    //POST-CONDITION:
    public void executeTask()
    {
        status = (status == "Empty") ? "Stock" : "Empty";
        changeDirection();
    }
    //PRE-CONDITION:
    //POST-CONDITION:
    public void changeDirection()
    {
        switch(destination)
        {
            case "CSR1": 
                destination = "ED1";
                break;
            case "CSR2":
                destination = "ED2";
                break;
            case "ED1":
                destination = "CSR1";
                break;       
            case "ED2":
                destination = "CSR2";
                break;
        }
    }

    // MUTATORS //

    //PRE-CONDITION: Mac Constructor must be instantiated
    //POST-CONDITION: Class Variable id mutated by Parameter id
    public void setID(String id)
    {
        this.id = id;
    }
    //PRE-CONDITION: Mac Constructor must be instantiated
    //POST-CONDITION: Class Variable status mutated by Parameter status
    public void setStatus(String status)
    {
        this.status = status;
    }
    //PRE-CONDITION: Mac Constructor must be instantiated
    //POST-CONDITION: Class Variable destination mutated by Parameter destination
    public void setDestination(String destination)
    {
        this.destination = destination;
    }
    //PRE-CONDITION:
    //POST-CONDITION:
    public void setTotalCrossings(int totalCrossings)
    {
        this.totalCrossings = totalCrossings;
    }

    // ACCESSORS //

    //PRE-CONDITION: Mac Constructor must be instantiated
    //POST-CONDITION: Class Variable id returned
    public String getID()
    {
        return id;
    }
    //PRE-CONDITION: Mac Constructor must be instantiated
    //POST-CONDITION: Class Variable status returned
    public String getStatus()
    {
        return status;
    }
    //PRE-CONDITION: Mac Constructor must be instantiated
    //POST-CONDITION: Class Variable destination returned
    public String getDestination()
    {
        return destination;
    }
    //PRE-CONDITION:
    //POST-CONDITION:
    public int getTotalCrossings()
    {
        return totalCrossings;
    }
}
