/* COMP2240 Assignment 2
 * File: Client.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 23/9/24
 * Date Last Modified: 23/9/24
 * Description: Implements the functionality for a thread of a client using the coffee machine in Problem 2.
 */
public class Client implements Runnable
{
    // CLASS VARIABLES //

    private String id;
    private String type;
    private int brewTime;
    private CoffeeMachine machine;

    // CONSTRUCTORS //

    //PRE-CONDITION:
    //POST-CONDITION:
    public Client() 
    {
        id = "";
        type = "";
        brewTime = 0;
        machine = null;
    }
    //PRE-CONDITION:
    //POST-CONDITION:
    public Client(String id, String type, int brewTime, CoffeeMachine machine)
    {
        this.id = id;
        this.type = type;
        this.brewTime = brewTime;
        this.machine = machine;
    }
    
    // METHODS //
    @Override
    public void run()
    {
        try
        {
            machine.requestCoffee(this);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    // ACCESSORS //

    //PRE-CONDITION:
    //POST-CONDITION:
    public String getID()
    {
        return id;
    }
    //PRE-CONDITION:
    //POST-CONDITION:
    public String getType()
    {
        return type;
    }
    //PRE-CONDITION:
    //POST-CONDITION:
    public int getBrewTime()
    {
        return brewTime;
    }
}
