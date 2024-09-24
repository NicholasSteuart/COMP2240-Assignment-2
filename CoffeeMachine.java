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

    private String currentMode = "NONE";    //The current mode of the coffee machine, whether it is brewing "HOT" or "COLD" coffee at the moment
    private int availableDispensers = 3;    //The current dispensers available by the coffee machine
    private int timer = 0;                  //The global time of the simulation
    
    // CONSTRUCTOR //

    //PRE-CONDITION: No pre-conditions
    //POST-CONDITION: Default Class Constructor instantiated

    public CoffeeMachine() {}

    // METHODS //

    //PRE-CONDITION: Parameter Client client must not be null
    //POST-CONDITION: Thread Object finishes it's parallel processing
    public synchronized void requestCoffee(Client client) throws InterruptedException
    {
        while(!currentMode.equals(client.getType()) && !currentMode.equals("NONE") || availableDispensers == 0)
        {
            wait(); //Monitors waits the current Thread object IF the currentMode does not match the Thread client's type AND if the currentMode is not equal to NONE OR if there are no available dispensers available
        }

        if(currentMode.equals("NONE"))  //Otherwise, if the coffee machine is currently not brewing, currentMode is now equal to the client's type
        {
            currentMode = client.getType();
        }

        availableDispensers--;  //Decrement available dispensers
        System.out.println("(" + timer + ") " + client.getID() + " uses dispenser " + availableDispensers + " (time: " + client.getBrewTime() + ")");
        availableDispensers++; //Increment available dispensers as the client's coffee has been brewed
        if(availableDispensers == 3)    //IF the coffee machine has no particular type of coffee brewing
        {
            currentMode = "NONE";   //currentMode is now set to default
            notifyAll();            //Notify all Thread objects waiting that the coffee machine is available
        }
    }
}
