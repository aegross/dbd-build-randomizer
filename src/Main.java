// imports
import java.util.Scanner;

public class Main {

    /**
     * main() - the driver method of the application. This runs first when the program is executed, and serves
     * to point the user to the randomizer of choice (Killer, Survivor, or All).
     * @param args
     */
    public static void main(String[] args) {
        // variables for user input
        Scanner commandLineInput = new Scanner(System.in);
        int responseNum;
        boolean responseValid = false;

        // randomizer object (not the most efficient method, but that's ok)
        Randomizer randomizer;

        // ask the user what build they want to generate (killer, survivor, or something pertaining to both),
        // then take the user's input and direct the application to the right randomizer
        System.out.println(); // padding
        while (!responseValid) {
            // intro formatting
            System.out.println("--------------------------    Dead By Daylight - Build Randomizer    --------------------------\n");

            // killer = 0, survivor = 1, all = 2
            System.out.println("Select the side that you want to generate builds (or individual build components) for.");
            System.out.println("\t(0) [EXIT PROGRAM] \n\t(1) Killer \n\t(2) Survivor \n\t(3) Shared");
            System.out.print("Enter Response: ");
            responseNum = commandLineInput.nextInt();

            // killer
            if (responseNum == 1) {
                // create a new KillerRandomizer and move control to that class
                randomizer = new KillerRandomizer();
                randomizer.setup();
            }
            // survivor
            else if (responseNum == 2) {
                // create a new SurvivorRandomizer and move control to that class
                randomizer = new SurvivorRandomizer();
                randomizer.setup();
            }
            // all (shared between both sides)
            else if (responseNum == 3) {
                // create a new AllRandomizer and move control to that class
                randomizer = new AllRandomizer();
                randomizer.setup();
            }
            // terminate the program
            else if (responseNum == 0) {
                responseValid = true;
                System.exit(0);
            }
            else {
                // the input was numerical, but invalid
                // if the input was a string, nextInt() will throw an error anyway (implement try-catch?)
                System.out.println("\nERROR: Invalid Input. Please Try Again.\n");
            }
        }
    }
}