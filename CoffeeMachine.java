/* COMP2240 Assignment 2
 * File: CoffeeMachine.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 23/9/24
 * Date Last Modified: 27/9/24
 * Description: Implements the functionality of a Monitor used to maintain the operation of a coffee machine for Problem 2.
 */
public class CoffeeMachine
{
    // CLASS VARIABLES //

    private String currentMode = "NONE";            //The current mode of the coffee machine, whether it is brewing "HOT" or "COLD" coffee at the moment
    private int availableDispensers = 3;            //The current dispensers available by the coffee machine
    private int globalTime = 0;                     //The global time of the simulation
    private int nextThreadToRun = 1;                //Keeps track of the next Client Thread to run
    private int startTime;                          //The starting time a Client thread enters brewing
    private int totalClients;                       //Total number of Client Threads running the simulation
    private boolean isDone = false;                 //Flag to make sure the finish message only executes once

    // CONSTRUCTOR //

    //PRE-CONDITION: No pre-conditions
    //POST-CONDITION: Default Class Constructor instantiated
    public CoffeeMachine()
    {   
        totalClients = 0;
    }
    //PRE-CONDITION: No pre-conditions
    //POST-CONDITION: Specialised Constructor instantiated with Parameter totalClients assigned to Class Variable totalClients
    public CoffeeMachine(int totalClients)
    {
        this.totalClients = totalClients;
    }

    // METHODS //

    //PRE-CONDITION: Constructor CoffeeMachine instantiated and Parameter Client client not null
    //POST-CONDITION: Parameter client Thread Object is either waiting or has finished it's first critical section: Entering the Monitor
    public synchronized void enterMonitor(Client client) throws InterruptedException
    {
        //If client is the first Object in the System that needs to run
        if(currentMode.equals("NONE") && client.getPositionInQueue() == nextThreadToRun)
        {
            currentMode = client.getType();
        }
        //Conditions for a Thread to enter the Monitor
        //Condition 1:  Client Threads need to be run in the order the Threads data was read in
        //Condition 2:  The current mode of the coffee machine needs to be equal to the client
        //Condition 3:  A dispenser needs to be available

        //IF even one is not fulfilled, the Thread must wait
        while(client.getPositionInQueue() != nextThreadToRun || (!currentMode.equals(client.getType()) && !currentMode.equals("NONE")) || availableDispensers == 0)
        {
            wait();
        }

        //Enters critical section
        currentMode = client.getType(); //Set the currentMode to the clients type
        availableDispensers--;          //Reduce available dispensers
        nextThreadToRun++;              //Increment to the next Client Thread that is to run next
    }
    //PRE-CONDITION: Constructor CoffeeMachine instantiated and Parameter Client client not null
    //POST-CONDITION: Parameter client Thread Object has finished it's second critical section: Exiting the Monitor
    public synchronized void exitMonitor(Client client) 
    {
        availableDispensers++;          //Relinquish the dispenser used by the client
        if(availableDispensers == 3)    //IF all dispensers are free, set the current mode to default 
        {
            currentMode = "NONE";
        }
        notifyAll();                    //Notify waiting Threads to wake up and see if they can enter the Monitor now
        //Checks if all client Threads have finished their execution and outputs the done message
        if(nextThreadToRun - 1 == totalClients && !isDone)
        {
            isDone = true;  //Locks the if statement from ever being entered again
            System.out.println("(" + globalTime + ") DONE");
        }
    }
    //PRE-CONDITION: Constructor CoffeeMachine instantiated and Parameter Client client not null
    //POST-CONDITION: Parameter client Thread Object completed it's brewing
    public void brewCoffee(Client client)
    {
        int startTime = globalTime;         //Start time of the current threads running in parallel
        int dispenserNum = 3 - availableDispensers; //The actual dispenser number the client thread is using
        System.out.println("(" + globalTime + ") " + client.getID() + " uses dispenser " + dispenserNum + " (time: " + client.getBrewTime() + ")");   //Assignment-specified output message
        //IF this client Thread happens to have the longest brewing time in the coffee machine right now, it will increment the global timer 
        if(client.getBrewTime() > globalTime - startTime)   
        {
            globalTime += client.getBrewTime();
        }
    }
}
