import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class SurvivorRandomizer implements Randomizer {
    // constants
    final String filepathSurvivor = "src/dbd_randomizer_files/survivor";

    // instance variables
    Scanner commandLineInputSurvivor;
    int responseNum;
    boolean responseValid;
    Random random;

    // collections
    HashMap<String,String> survivorList;
    String[] survivorListKeys; // pairs with survivorList
    HashMap<String,String> survivorListLegendary;
    String[] survivorListLegendaryKeys; // pairs with survivorListLegendary
    ArrayList<String> survivorPerks;
    ArrayList<String> survivorOfferings;
    // todo: something for items

    // collection sizes
    int numSurvivors;
    int numSurvivorsLegendary;
    int numSurvivorPerks;
    int numSurvivorOfferings;
    // todo: something for items

    // constructor
    public SurvivorRandomizer() {
        // for user input
        commandLineInputSurvivor = new Scanner(System.in);
        responseNum = -1;
        responseValid = false;

        // for generating random numbers
        random = new Random();
    }

    public void setup() {
        // formatting
        System.out.println("\n---------------------------------    Randomizer - Survivor    ---------------------------------\n");

        // read all the "general" files
        try {
            // survivor list
            String survivorListFilepath = filepathSurvivor + "/survivor_list.csv";
            survivorList = FileReader.readCSVFile(survivorListFilepath);
            numSurvivors = survivorList.size();
            // get the keys from the survivor hashmap so that random numbers can be used later
            survivorListKeys = survivorList.keySet().toArray(new String[0]);

            // survivor list
            String survivorListLegendaryFilepath = filepathSurvivor + "/survivor_list_legendary.csv";
            survivorListLegendary = FileReader.readCSVFile(survivorListLegendaryFilepath);
            numSurvivorsLegendary = survivorListLegendary.size();
            // get the keys from the survivor (legendary) hashmap so that random numbers can be used later
            survivorListLegendaryKeys = survivorListLegendary.keySet().toArray(new String[0]);

            // survivor perks
            String survivorPerkFilepath = filepathSurvivor + "/survivor_perks.txt";
            survivorPerks = FileReader.readTextFile(survivorPerkFilepath);
            numSurvivorPerks = survivorPerks.size();

            // survivor offerings
            String survivorOfferingsFilepath = filepathSurvivor + "/survivor_offerings.txt";
            survivorOfferings = FileReader.readTextFile(survivorOfferingsFilepath);
            numSurvivorOfferings = survivorOfferings.size();

            // todo: item stuff
        }
        catch (FileNotFoundException f) {
            // print the issue
            System.out.println(f);
            f.printStackTrace();
        }

        // execute the request the user gives, as long as the prompt is valid
        while (!responseValid) {
            // print the prompt asking the user what they want
            System.out.println("\nFrom the options below, select what you would like to randomly generate.");
            System.out.println("\t(0) [EXIT PROGRAM] \n\t(1) Full Survivor Build " +
                    "\n\t(2) Full Survivor Build (Including Legendary Cosmetics) " +
                    "\n\t(3) Survivor \n\t(4) Survivor (Including Legendary Cosmetics) " +
                    "\n\t(5) Perks \n\t(6) Items \n\t(7) Item Add-Ons \n\t(8) Offerings");
            System.out.print("Enter Response: ");
            responseNum = commandLineInputSurvivor.nextInt();
            System.out.println(); // spacing

            // take the user's input and direct the application to the right randomizer
            // full survivor build
            if (responseNum == 1) {
                // ...
            }
            // full survivor build (including legendary cosmetics)
            else if (responseNum == 2) {
                // ...
            }
            // survivor
            else if (responseNum == 3) {
                generateSurvivor();
            }
            // survivor (including legendary cosmetics)
            else if (responseNum == 4) {
                generateSurvivorLegendary();
            }
            // survivor perks
            else if (responseNum == 5) {
                generateSurvivorPerks(4);
                // todo: wrapper function
            }
            // items
            else if (responseNum == 6) {
                // ...
            }
            // item add-ons
            else if (responseNum == 7) {
                // ...
            }
            // offerings
            else if (responseNum == 8) {
                generateSurvivorOffering();
            }
            // terminate the program
            else if (responseNum == 0) {
                responseValid = true;
                System.exit(0);
            }
            else {
                // the input was numerical, but invalid
                // if the input was a string, nextInt() will throw an error anyway (implement try-catch?)
                System.out.println("ERROR: Invalid Input. Please Try Again.\n");
            }
        }
    }

    //// generation functions

    /**
     * generateSurvivor() - Randomly generates a random survivor from the list of survivors in survivor_list.csv,
     * and returns the key of said survivor for future use.
     *
     * @return the key of the selected survivor for the survivorList HashMap
     */
    public String generateSurvivor() {
        // generate a random number
        int randSurvivor = random.nextInt(numSurvivors);

        // get the key and value from the randomly selected entry using the list of keys (ex: "dwight")
        // in the HashMap - an array or ArrayList is necessary because the random number is an index
        String key = survivorListKeys[randSurvivor];
        String value = survivorList.get(key);

        // print out the randomly selected survivor
        System.out.println("Randomly Selected Survivor: " + value); // + " (" + key + ")");

        // return the key for later use, if necessary
        return key;
    }

    /**
     * generateSurvivorLegendary() - Randomly generates a survivor from the list of killers in survivor_list_legendary.csv,
     * which includes legendary (and other distinct) cosmetics as character options. The key of the generated survivor
     * is returned for future use.
     *
     * Note that the key-value pairings are flipped for survivor_list_legendary.csv compared to survivor_list.csv; this
     * is done so that a legendary survivor's true name can be printed and used without issue.
     *
     * @return the key of the selected survivor for the survivorListLegendary HashMap
     */
    public String generateSurvivorLegendary() {
        // generate a random number
        int randSurvivor = random.nextInt(numSurvivorsLegendary);

        // get the key and value from the randomly selected entry using the list of keys (ex: "Dwight Fairfield")
        // in the HashMap - an array or ArrayList is necessary because the random number is an index
        String key = survivorListLegendaryKeys[randSurvivor];
        String value = survivorListLegendary.get(key);

        // print out the randomly selected survivor
        String trueName = survivorList.get(value);

        if (trueName.equals(key)) {
            // if the true survivor name is the same as the key (not a legendary cosmetic) just print the name
            System.out.println("Randomly Selected Survivor: " + key);
        }
        else {
            // otherwise, print the cosmetic name with the true name in parentheses
            System.out.println("Randomly Selected Survivor: " + key + " (" + trueName + ")");
        }

        // return the value (true key) for later use, if necessary
        return value;
    }

    /**
     * generateSurvivorPerks() - randomly generates a number of unique survivor perks from the list present in
     * survivor_perks.txt.
     * @param numPerks the number of survivor perks to be generated
     */
    public void generateSurvivorPerks(int numPerks) {
        // make a list for the random numbers that will be chosen
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        int randomNum;

        // generate numPerks amount of random numbers
        for (int i = 0; i < numPerks; i++) {
            // if the number has already been chosen, do NOT add it (all selected perks should be unique)
            // otherwise, add it
            do randomNum = random.nextInt(numSurvivorPerks);
            while (randomNumbers.contains(randomNum));

            // print out the perk that was selected
            randomNumbers.add(randomNum);
            System.out.println("Random Survivor Perk #" + (i+1) + ": " + survivorPerks.get(randomNum));
        }
    }

    /**
     * generateSurvivorOffering() - randomly generates a survivor offering from the list of offerings present
     * in survivor_offerings.txt.
     */
    public void generateSurvivorOffering() {
        // generate a random number from [0, (numSurvivorOfferings - 1)]
        int randOfferingNum = random.nextInt(numSurvivorOfferings);

        // print out the offering that corresponds with the random number
        System.out.println("Random Survivor Offering: " + survivorOfferings.get(randOfferingNum));
    }

    //// wrapper functions - used when individual components are selected, and gives the user more options

    //// full build wrapper function
}
