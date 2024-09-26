/* COMP2240 Assignment 2
 * File: CoffeeMachine.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 23/9/24
 * Date Last Modified: 24/9/24
 * Description: Implements the functionality of a Monitor used to maintain the operation of a coffee machine for Problem 2.
 */
public class CoffeeMachine
{
    // CLASS VARIABLES //

    private String currentMode = "NONE";            //The current mode of the coffee machine, whether it is brewing "HOT" or "COLD" coffee at the moment
    private int availableDispensers = 3;            //The current dispensers available by the coffee machine
    private int globalTime = 0;                     //The global time of the simulation
    private int nextThreadToRun = 1;    
    
    // CONSTRUCTOR //

    //PRE-CONDITION: No pre-conditions
    //POST-CONDITION: Default Class Constructor instantiated

    public CoffeeMachine() {}

    // METHODS //

    //PRE-CONDITION: 
    //POST-CONDITION: 
    public synchronized void enterMonitor(Client client) throws InterruptedException
    {
        //Conditions for a Thread to enter the Monitor
        //Condition 1:  Client Threads need to be run in the order the Threads data was read in
        //Condition 2:  The current mode of the coffee machine needs to be equal to the client
        //Condition 3:  A dispenser needs to be available

        //IF even one is not fulfilled, the Thread must wait
        while(client.getPositionInQueue() != nextThreadToRun || (!currentMode.equals(client.getType()) && !currentMode.equals("NONE")) || availableDispensers == 0)
        {
            wait();
        }

        currentMode = (client.getType().equals("H")) ? "H" : "C";
        availableDispensers--; 
        nextThreadToRun++;
    }
    //PRE-CONDITION: 
    //POST-CONDITION:
    public synchronized void exitMonitor(Client client) 
    {
        availableDispensers++;
        if(availableDispensers == 3)
        {
            currentMode = "NONE";
        }
        notifyAll();
    }
    //PRE-CONDITION: 
    //POST-CONDITION:
    public void brewCoffee(Client client)
    {
        int dispenserNum = 3 - availableDispensers;
        System.out.println("(" + globalTime + ") " + client.getID() + " uses dispenser " + dispenserNum + " (time: " + client.getBrewTime() + ")");
        if(client.getBrewTime() > globalTime)
        {
            globalTime += client.getBrewTime();
        }
    }
}
