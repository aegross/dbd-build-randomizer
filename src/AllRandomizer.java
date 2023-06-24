// imports
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random; // if Random doesn't work: Math RANDOM FORMULA: Math.random() * (max - min + 1) + min

public class AllRandomizer implements Randomizer {
    // constants
    final String filepathAll = "src/dbd_randomizer_files/all";

    // instance variables
    Scanner commandLineInputAll;
    int responseNum;
    boolean responseValid;
    Random random;

    // lists
    ArrayList<String> mapOfferings;

    // list sizes
    int numMapOfferings;

    // constructor
    public AllRandomizer() {
        // for user input
        commandLineInputAll = new Scanner(System.in);
        responseNum = -1;
        responseValid = false;

        // for generating random numbers
        random = new Random();
    }

    public void setup() {
        // section heading
        System.out.println("\n------------------------------------    Randomizer - All    -----------------------------------");

        // execute the request the user gives, as long as the prompt is valid
        while (!responseValid) {
            // print the prompt asking the user what they want
            System.out.println("\nFrom the options below, select what you would like to randomly generate.");
            System.out.println("\t(0) [RETURN TO MENU] \n\t(1) Map Offering");
            System.out.print("Enter Response: ");
            responseNum = commandLineInputAll.nextInt();

            // take the user's input and direct the application to the right randomizer
            // map offering
            if (responseNum == 1) {
                // generate the map offering; try-catch for possible missing file
                try { generateMapOffering(); }
                catch (FileNotFoundException f) {
                    // print the issue
                    System.out.println(f);
                    f.printStackTrace();
                }
            }
            // end the loop and return to main() in Main.java
            else if (responseNum == 0) {
                responseValid = true;
            }
            else {
                // the input was numerical, but invalid
                // if the input was a string, nextInt() will throw an error anyway (implement try-catch?)
                System.out.println("\nERROR: Invalid Input. Please Try Again.\n");
            }
        }
    }

    /**
     * generateMapOffering() - Randomly generates a map offering using the list in map_offerings.txt.
     * @throws FileNotFoundException if the map_offerings.txt file is not found
     */
    public void generateMapOffering() throws FileNotFoundException {
        // load the arraylist
        String mapOfferingFile = filepathAll + "/map_offerings.txt";
        mapOfferings = FileReader.readTextFile(mapOfferingFile);

        // count the number of offerings (get the length of the ArrayList)
        numMapOfferings = mapOfferings.size();
        // System.out.println("\nNumber of Map Offerings: " + numMapOfferings); // for debugging purposes

        // generate a random number from [0, (numMapOfferings - 1)]
        int randOfferingNum = random.nextInt(numMapOfferings);

        // print out the offering that corresponds with the random number
        // System.out.println("\nYou Selected: Map Offering"); // unsure if this is wanted
        System.out.println("\nRandom Map Offering: " + mapOfferings.get(randOfferingNum));
    }
}
