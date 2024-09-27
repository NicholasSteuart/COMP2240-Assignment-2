/* COMP2240 Assignment 2
 * File: P2.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 7/9/24
 * Date Last Modified: 27/9/24
 * Description: MAIN file. Reads in data from file and outputs Monitor-controlled concurrent thread execution simulating Problem 2.
 */

// PACKAGES //

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class P2
{
    // CLASS VARIABLES //

    public static int currentCount = 0, totalCount = 0; //Keeps track of the global current unique Client ID the file is upto
    public static ArrayList<Client> clientList = new ArrayList<>(); //Stores the Client Objects read in from file

    // MAIN //

    public static void main(String[] args) throws Exception
    {
        File file = new File(args[0]); //Read in terminal argument as a new File Object

        // READ IN FROM FILE //

        try(Scanner sc = new Scanner(file))
        {
            int totalClients = sc.nextInt(); //Reads in the total amount of Client Objects in the file
            CoffeeMachine coffeeMachine = new CoffeeMachine(totalClients);  //Instantiates the Coffee Machine used in problem 2
            int positionInQueue = 1;    //Manages the position the Thread object is assigned to be run

            for(int i = 0; i < totalClients; i++)
            {
                String id = sc.next();  //Stores in the current read-in Client's ID
                String type = id.substring(0,1);    //Stores the current read-in Client's type
                int brewTime = sc.nextInt();    //Stores te current read-in Client's brewTime
                clientList.add(new Client(id, type, brewTime, positionInQueue, coffeeMachine));  //Adds a new Client to the current Client list. 
                positionInQueue++;
            }

            sc.close(); //Close Scanner

            // START THREAD EXECUTION //

            for(Client client: clientList)
            {
                new Thread(client).start();
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File " + args[0] + " was not found."); //File defined in the terminal line argument was invalid and doesn't exist in the directory
        }
    }
}