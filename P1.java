/* COMP2240 Assignment 2
 * File: P1.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 7/9/24
 * Date Last Modified: 27/9/24
 * Description: MAIN file. Reads in data from file and outputs Semaphore-controlled concurrent thread execution simulating Problem 1.
 */

// PACKAGES //

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class P1
{ 
    // CLASS VARIABLES //

    public static int currentCount = 0, totalCount = 0;     //Keeps track of the global current unique MAC ID the file is upto, and total count of MACs starting from a particular destination 
    public static ArrayList<Mac> macList = new ArrayList<>(); //Stores the MAC Objects read in from file

    // MAIN //

    public static void main(String[] args) throws Exception
    {
        File file = new File(args[0]);      //Read in terminal argument as a new File Object

        // READS IN FROM FILE //
        
        try(Scanner sc = new Scanner(file))
        {
            Intersection intersection = new Intersection(1);    //Instantiate the intersection with only one permit 

            // CREATE MACS //

            createMacs(sc.next(), intersection);    //Creates CSR1 starting MACs
            createMacs(sc.next(), intersection);    //Creates CSR2 starting MACs
            createMacs(sc.next(), intersection);    //Creates ED1 starting MACs
            createMacs(sc.next(), intersection);    //Creates ED2 starting MACs

            int totalCrossings = Integer.parseInt(sc.next().substring(2));  //Reads in and stores from file the total amount of times a MAC must cross the intersection

            sc.close();     //Close Scanner

            // START THREAD EXECUTION //

            for(Mac mac: macList)   
            {
                mac.setTotalCrossings(totalCrossings);
                new Thread(mac).start();
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File " + args[0] + " was not found.");  //File defined in the terminal line argument was invalid and doesn't exist in the directory
        }
    }
    
    // METHODS //
    
    //PRE-CONDITIONS: 
        //Parameter String token must be of the form "CSR1=1", "CSR2=2", "ED1=1", or "ED2=1"
        //Parameter Intersection intersection must have been instantiated
    //POST-CONDITIONS: macList populated by new MAC Objects
    public static void createMacs(String token, Intersection intersection)
    {
        int eqPosition = token.indexOf("=");    //Returns the position of the equal sign in the token
        totalCount += Integer.parseInt(token.substring(eqPosition + 1, token.length() - 1));    //Increments totalCount by the value after the equals sign
        String status = "";         //Stores the status of the current MAC being read in 
        String destination = "";    //Stores the destination of the current MAC being read in

        //Determines how to instantiate the MAC(s) based on the token parsed in
        switch (token.substring(0, eqPosition))
        {
            case "CSR1" -> {
                status = "Stock";
                destination = "ED1";
            }
            case "CSR2" -> {
                status = "Stock";
                destination = "ED2";
            }
            case "ED1" -> {
                status = "Empty";
                destination = "CSR1";
            }
            case "ED2" -> {
                status = "Empty";
                destination = "CSR2";
            }
        }

        //Adds a new mac to the current Macs list. 
        //currentCount keeps track of the MAC ID we are upto.
        for(int i = currentCount; i < totalCount; i++)
        {
            macList.add(new Mac("MAC-" + (i + 1), status, destination, intersection));
        }
        currentCount = totalCount;  //Allows us to continue from the next available MAC ID of the next token
    }
}