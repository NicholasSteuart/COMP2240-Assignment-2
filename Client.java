/* COMP2240 Assignment 2
 * File: Client.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 23/9/24
 * Date Last Modified: 24/9/24
 * Description: Implements the Thread functionality for a Client using the coffee machine in Problem 2.
 */
public class Client implements Runnable
{
    // CLASS VARIABLES //

    private final String ID;        //Client's unique ID of the form Hn or Cn, depending on if the client is brewing a hot or cold coffee and n being a unique postive integer
    private String type;            //The Client's type, whether it's asking for a Hot (H) or Cold (C) coffee
    private int brewTime;           //The amount of time the Client's coffee will take to brew
    private CoffeeMachine machine;  //The Coffee Machine Monitor which controls the critical section of problem 2

    // CONSTRUCTORS //

    //PRE-CONDITION: No pre-conditions
    //POST-CONDITION: Default Constructor instantiated with default values assigned to Class Variables ID, type, brewTime and machine

    public Client() 
    {
        ID = "";
        type = "";
        brewTime = 0;
        machine = null;
    }
    //PRE-CONDITION: No pre-conditions
    //POST-CONDITION: Specialised Constructor instantiated with Parameters ID, type, brewTime and machine assigned to Class Variables ID, type, brewTime and machine respectively

    public Client(String ID, String type, int brewTime, CoffeeMachine machine)
    {
        this.ID = ID;
        this.type = type;
        this.brewTime = brewTime;
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
            machine.requestCoffee(this);    //Thread tries to enter it's critical section
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();     //Interrupt Thread if it must wait
        }
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
}
