
import java.util.Comparator;
import java.util.PriorityQueue;

public class CoffeeMachine
{
    // CLASS VARIABLES //

    private String currentMode = "NONE";
    private int availableDispensers = 3;
    private int globalTimer = 0;
    private PriorityQueue<Client> hotQueue = new PriorityQueue<>(Comparator.comparing(Client::getID));
    private PriorityQueue<Client> coldQueue = new PriorityQueue<>(Comparator.comparing(Client::getID));
    
    // CONSTRUCTOR //

    public CoffeeMachine() {}

    // METHODS //

    public synchronized void requestCoffee(Client client) throws InterruptedException
    {
        if(client.getType().equals("H"))
        {
            hotQueue.add(client);
            while((!currentMode.equals(client.getType()) && !currentMode.equals("NONE")) || availableDispensers == 0 || hotQueue.peek() != client)
            {
                wait();
            }
            hotQueue.poll();
            serveCoffee(client);
        }
        else
        {
            coldQueue.add(client);
            while((!currentMode.equals(client.getType()) && !currentMode.equals("NONE")) || availableDispensers == 0 || coldQueue.peek() != client)
            {
                wait();
            }
            coldQueue.poll();
            serveCoffee(client);
        }
    }

    private void serveCoffee(Client client) throws InterruptedException
    {
        if(currentMode.equals("NONE"))
        {
            currentMode = client.getType();
        }

        System.out.println("(" + globalTimer + ") " + client.getID() + " uses despenser (time: " + client.getBrewTime() + ")");

        availableDispensers--;
        availableDispensers++;
        if(availableDispensers == 3)
        {
            currentMode = "NONE";
            notifyAll();
        }
    }
}
