/* COMP2240 Assignment 2
 * File: Client.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 23/9/24
 * Date Last Modified: 27/9/24
 * Description: Implements the Thread functionality for a Client using the coffee machine in Problem 2.
 */
public class Client implements Runnable
{
    // CLASS VARIABLES //

    private final String ID;        //Client's unique ID of the form Hn or Cn, depending on if the client is brewing a hot or cold coffee and n being a unique postive integer
    private String type;            //The Client's type, whether it's asking for a Hot (H) or Cold (C) coffee
    private int brewTime;           //The amount of time the Client's coffee will take to brew
    private int positionInQueue;    //Stores the Thread's position in queue to be run as the read in file states that the Threads must be run in the order they are ready in from file
    private CoffeeMachine machine;  //The Coffee Machine Monitor which controls the critical section of problem 2


    // CONSTRUCTORS //

    //PRE-CONDITION: No pre-conditions
    //POST-CONDITION: Default Constructor instantiated with default values assigned to Class Variables ID, type, brewTime and machine

    public Client() 
    {
        ID = "";
        type = "";
        brewTime = 0;
        positionInQueue = 0;
        machine = null;

    }
    //PRE-CONDITION: No pre-conditions
    //POST-CONDITION: Specialised Constructor instantiated with Parameters ID, type, brewTime, positionInQueue and machine assigned to Class Variables ID, type, brewTime, positionInQueue and machine respectively

    public Client(String ID, String type, int brewTime, int positionInQueue, CoffeeMachine machine)
    {
        this.ID = ID;
        this.type = type;
        this.brewTime = brewTime;
        this.positionInQueue = positionInQueue;
        this.machine = machine;
    }
    
    // METHODS //

    //PRE-CONDITION: An instance of this Class must be instantiated as a Thread Object
    //POST-CONDITION: Thread Object finishes it's parallel processing.
    @Override
    public void run()
    {
        try
        {
            machine.enterMonitor(this);    
            machine.brewCoffee(this);
            machine.exitMonitor(this);     
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();     //Interrupt Thread if it must wait
        }
    }

    // MUTATOR //

    //PRE-CONDITION: An instance of this Class must be instantiated
    //POST-CONDITION: Class variable positionInQueue mutated by parameter positionInQueue
    public void setPositionInQueue(int positionInQueue)
    {
        this.positionInQueue = positionInQueue;
    }
    
    // ACCESSORS //

    //PRE-CONDITION: Client Constructor instantiated and Class Variable ID must be instantiated
    //POST-CONDITION: Class Variable ID returned
    public String getID()
    {
        return ID;
    }
    //PRE-CONDITION: Client Constructor instantiated and Class Variable type must be instantiated
    //POST-CONDITION: Class Variable type returned
    public String getType()
    {
        return type;
    }
    //PRE-CONDITION: Client Constructor instantiated and Class Variable brewTime must be instantiated
    //POST-CONDITION: Class Variable brewTime returned
    public int getBrewTime()
    {
        return brewTime;
    }
    //PRE-CONDITION: Client Constructor instantiated and Class Variable positionInQueue must be instantiated
    //POST-CONDITION: Class Variable positionInQueue returned
    public int getPositionInQueue()
    {
        return positionInQueue;
    }
}
