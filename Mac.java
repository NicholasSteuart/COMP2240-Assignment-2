/* COMP2240 Assignment 2
 * File: Mac.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 9/9/24
 * Date Last Modified: 24/9/24
 * Description: Implements the Thread functionality for the Medical-Supply Automated Cart (MAC) used in Problem 1.
 */
public class Mac implements Runnable
{
    // CLASS VARIABLES //

    private final String ID;                    //MAC's ID in the form MAC-n where n is an incrementing unique positive integer
    private String status;                      //Status of the MAC, whether it has "Stock" or "Empty"
    private String destination;                 //Destination the MAC must travel to
    private int totalCrossings = 0;             //Counts the total number of times the MAC has crossed the intersection
    private final Intersection intersection;    //The intersection which controls the critical section of problem 1

    // CONSTRUCTORS // 

    //PRE-CONDITION: No Pre-conditions
    //POST-CONDITION: Default Constructor instantiated with default values assigned to Class Variables ID, status, destination and intersection.
    public Mac() 
    {
        ID = "";
        status = "";
        destination = "";
        intersection = null;
    }
    //PRE-CONDITION: No Pre-conditions
    //POST-CONDITION: Specialised Constructor instantiated with Parameters ID, status, destination and intersection assigned to Class Variables id, status, destination and intersection respectively
    public Mac(String ID, String status, String destination, Intersection intersection)
    {
        this.ID = ID;
        this.status = status;
        this.destination = destination;
        this.intersection = intersection;
    }

    // METHODS //

    //PRE-CONDITION: An instance of this Class must be instantiated as a Thread Object
    //POST-CONDITION: Thread Object finishes it's parallel processing.
    @Override
    public void run()
    {
        int currentCrosses = 0; //Counts the current amount of times the Mac Thread Object has crossed the intersection.
        while(currentCrosses < totalCrossings)
        {
            try 
            {
                System.out.println(ID + " (" + status + "): Waiting at the Intersection. Going towards " + destination);
                if(intersection.enterIntersection())    //IF critical section is available...
                {       
                    crossCheckpoints();                 //Cross checkpoints 1, 2 and 3 in the intersection
                    intersection.exitIntersection();    //Exit critical section
                    currentCrosses++;                   //Cross intersection so increment
                    executeTask();                      //MAC executes it's task
                    System.out.println(intersection.updateTrailCompletions(destination));  
                }
            } 
            catch (InterruptedException e) 
            {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(ID +  ": Finished.");        //Mac Thread Object outputs it's finished
    }
    //PRE-CONDITION: An instance of this Class must be instantiated as a Thread Object
    //POST-CONDITION: MAC Thread Object has finished executing it's critical section
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
    //PRE-CONDITION: An instance of this Class must be instantiated
    //POST-CONDITION: Mac Thread Object has executed it's tasks
    public void executeTask()
    {
        status = (status.equals("Empty")) ? "Stock" : "Empty";  //Status changed based on whether the MAC is delivering or resupplying it's payload
        changeDirection();  //Update the MAC's current destination
    }
    //PRE-CONDITION: An instance of this Class must be instantiated
    //POST-CONDITION: Mac's destination is updated to reflect where it must travel to on it's next crossing
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

    //PRE-CONDITION: An instance of this Class must be instantiated
    //POST-CONDITION: Class variable totalCrossings mutated by parameter totalCrossings
    public void setTotalCrossings(int totalCrossings)
    {
        this.totalCrossings = totalCrossings;
    }
}
