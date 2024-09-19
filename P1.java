/* COMP2240 Assignment 2
 * File: P1.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 7/9/24
 * Date Last Modified: 7/9/24
 * Description: MAIN file. Reads in data from file and performs concurrency based testing.
 */

 // PACKAGES //
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.util.Scanner;
 import java.util.ArrayList;

public class P1
{ 
    public static int currentCount = 0, totalCount = 0;
    public static ArrayList<Mac> macList = new ArrayList<>();         
    public static void main(String[] args) throws Exception
    {
        File file = new File(args[0]);
        try(Scanner sc = new Scanner(file))
        {
            int totalCrossings = 0;
            Intersection intersection = new Intersection(1);

            // CREATE MACS //
            //System.out.println(sc.next());
            createMacs(sc.next(), intersection);
            createMacs(sc.next(), intersection);
            createMacs(sc.next(), intersection);
            createMacs(sc.next(), intersection);

            totalCrossings = Integer.parseInt(sc.next().substring(2));

            sc.close();

            for(Mac mac: macList)
            {
                mac.setTotalCrossings(totalCrossings);
                Thread t = new Thread(() -> mac.run());
                t.start();
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File " + args[0] + " was not found.");
            e.printStackTrace();
        }
    }
    
    // METHODS //
    
    //PRE-CONDITIONS:
    //POST-CONDITIONS:
    public static void createMacs(String token, Intersection intersection)
    {
        int eqPosition = token.indexOf("=");    //Returns the position of the equal sign in the token
        totalCount += Integer.parseInt(token.substring(eqPosition + 1, token.length() - 1));    //Increments totalCount by the value after the equals sign
        String status = "";
        String destination = "";

        //Determines how to instantiate the MAC(s) based on the token parsed in
        switch (token.substring(0, eqPosition))
        {
            case "CSR1":
                status = "Stock";
                destination = "ED1";
                break;
            case "CSR2":
                status = "Stock";
                destination = "ED2";
                break;
            case "ED1":
                status = "Empty";
                destination = "CSR1";
                break;
            case "ED2":
                status = "Empty";
                destination = "CSR2";
                break;
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