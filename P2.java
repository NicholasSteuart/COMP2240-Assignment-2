/* COMP2240 Assignment 2
 * File: P2.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 7/9/24
 * Date Last Modified: 7/9/24
 * Description: MAIN file. Reads in data from file and performs concurrency based testing.
 */

// PACKAGES //

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class P2
{
    public static int currentCount = 0, totalCount = 0;
    public static ArrayList<Client> clientList = new ArrayList<>(); 

    public static void main(String[] args) throws Exception
    {
        File file = new File(args[0]);
        try(Scanner sc = new Scanner(file))
        {
            CoffeeMachine coffeeMachine = new CoffeeMachine();
            int clients = sc.nextInt();

            for(int i = 0; i < clients; i++)
            {
                String id = sc.next();
                String type = id.substring(0,1);
                int brewTime = sc.nextInt();
                clientList.add(new Client(id, type, brewTime, coffeeMachine));
            }

            sc.close();

            for(Client client: clientList)
            {
                new Thread(client).start();
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File " + args[0] + " was not found.");
        }

    }
}