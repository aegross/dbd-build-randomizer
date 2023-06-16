// imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class KillerRandomizer implements Randomizer {
    // constants
    final String filepathKiller = "src/dbd_randomizer_files/killer";

    // instance variables
    Scanner commandLineInputKiller;
    int responseNum;
    boolean responseValid;
    Random random;

    // collections
    HashMap<String,String> killerList;
    String[] killerListKeys; // pairs with killerList
    HashMap<String,String> killerListLegendary;
    String[] killerListLegendaryKeys; // pairs with killerListLegendary
    ArrayList<String> killerPerks;
    ArrayList<String> killerOfferings;

    // collection sizes
    int numKillers;
    int numKillersLegendary;
    int numKillerPerks;
    int numKillerOfferings;

    // constructor
    public KillerRandomizer() {
        // for user input
        commandLineInputKiller = new Scanner(System.in);
        responseNum = -1;
        responseValid = false;

        // for generating random numbers
        random = new Random();
    }

    public void setup() {
        // ...
        System.out.println("\n---------------------------------    Randomizer - Killer    -----------------------------------\n");

        // read all the "general" files
        try {
            // killer list
            String killerListFilepath = filepathKiller + "/killer_list.csv";
            killerList = FileReader.readCSVFile(killerListFilepath);
            numKillers = killerList.size();
            // get the keys from the killer hashmap so that random numbers can be used later
            killerListKeys = killerList.keySet().toArray(new String[0]);

            // killer list, including legendary or other notable cosmetics
            String killerListLegendaryFilepath = filepathKiller + "/killer_list_legendary.csv";
            killerListLegendary = FileReader.readCSVFile(killerListLegendaryFilepath);
            numKillersLegendary = killerListLegendary.size();
            // get the keys from the killer (legendary) hashmap so that random numbers can be used later
            killerListLegendaryKeys = killerListLegendary.keySet().toArray(new String[0]);

            // killer perks
            String killerPerkFilepath = filepathKiller + "/killer_perks.txt";
            killerPerks = FileReader.readTextFile(killerPerkFilepath);
            numKillerPerks = killerPerks.size();

            // killer offerings
            String killerOfferingFilepath = filepathKiller + "/killer_offerings.txt";
            killerOfferings = FileReader.readTextFile(killerOfferingFilepath);
            numKillerOfferings = killerOfferings.size();
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
            System.out.println("\t(0) [EXIT PROGRAM] \n\t(1) Full Killer Build " +
                    "\n\t(2) Full Killer Build (Including Legendary Cosmetics) " +
                    "\n\t(3) Killer \n\t(4) Killer (Including Legendary Cosmetics) " +
                    "\n\t(5) Perks \n\t(6) Add-Ons \n\t(7) Offerings");
            System.out.print("Enter Response: ");
            responseNum = commandLineInputKiller.nextInt();
            System.out.println(""); // spacing

            // take the user's input and direct the application to the right randomizer
            // full killer build
            if (responseNum == 1) {
                responseValid = true;
                // todo: full killer build
            }
            // full killer build (including legendary cosmetics)
            else if (responseNum == 2) {
                // todo: full killer build (including legendary cosmetics)
            }
            // individual killer
            else if (responseNum == 3) {
                generateKiller();
            }
            // individual killer (including legendary cosmetics)
            else if (responseNum == 4) {
                generateKillerLegendary();
            }
            // killer perks
            else if (responseNum == 5) {
                // todo: perk wrapper function
                generateKillerPerks(4);
            }
            // killer add-ons
            else if (responseNum == 6) {
                // todo: generate add-ons
            }
            // killer offerings
            else if (responseNum == 7) {
                generateKillerOffering();
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

            // todo: ask the user if they want to generate something else
            // System.out.println("");
        }
    }

    // generation functions

    /**
     * generateKiller() - Randomly generates a random killer from the list of killers in killer_list.csv,
     * and returns the key of said killer for possible future use.
     *
     * @return the key of the selected killer for the killerList HashMap
     */
    public String generateKiller() {
        // generate a random number
        int randKiller = random.nextInt(numKillers);

        // get the key and value from the randomly selected entry using the list of keys (ex: "trapper")
        // in the HashMap - an array or ArrayList is necessary because the random number is an index
        String key = killerListKeys[randKiller];
        String value = killerList.get(key);

        // print out the randomly selected killer
        System.out.println("Randomly Selected Killer: " + value); // + " (" + key + ")");

        // return the key for later use, if necessary
        return key;
    }

    /**
     * generateKillerLegendary() - Randomly generates a killer from the list of killers in killer_list_legendary.csv,
     * which includes legendary (and other distinct) cosmetics as character options. The key of the generated killer
     * is returned for possible future use.
     *
     * Note that the key-value pairings are flipped for killer_list_legendary.csv compared to killer_list.csv; this
     * is done so that a legendary killer's true name can be printed and used without issue.
     *
     * @return the key of the selected killer for the killerListLegendary HashMap
     */
    public String generateKillerLegendary() {
        // generate a random number
        int randKiller = random.nextInt(numKillersLegendary);

        // get the key and value from the randomly selected entry using the list of keys (ex: "The Trapper")
        // in the HashMap - an array or ArrayList is necessary because the random number is an index
        String key = killerListLegendaryKeys[randKiller];
        String value = killerListLegendary.get(key);

        // print out the randomly selected killer
        String trueName = killerList.get(value);

        if (trueName.equals(key)) {
            // if the true killer name is the same as the key (not a legendary cosmetic) just print the killer name
            System.out.println("Randomly Selected Killer: " + key);
        }
        else {
            // otherwise, print the cosmetic name with the true name in parentheses
            System.out.println("Randomly Selected Killer: " + key + " (" + killerList.get(value) + ")");
        }

        // return the key for later use, if necessary
        return key;
    }

    /**
     * generateKillerPerks() - randomly generates a number of unique killer perks from the list present in
     * killer_perks.txt.
     * @param numPerks the number of killer perks to be generated
     */
    public void generateKillerPerks(int numPerks) {
        // make a list for the random numbers that will be chosen
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        int randomNum;

        // generate numPerks amount of random numbers
        for (int i = 0; i < numPerks; i++) {
            // if the number has already been chosen, do NOT add it (all selected perks should be unique)
            // otherwise, add it
            do randomNum = random.nextInt(numKillerPerks);
            while (randomNumbers.contains(randomNum));

            // print out the perk that was selected
            randomNumbers.add(randomNum);
            System.out.println("Random Killer Perk #" + (i+1) + ": " + killerPerks.get(randomNum));
        }
    }

    public void generateAddOns(String killerKey, int numAddOns) {
        // todo
    }

    /**
     * generateKillerOffering() - randomly generates a killer offering from the list of offerings present
     * in killer_offerings.txt.
     */
    public void generateKillerOffering() {
        // generate a random number from [0, (numKillerOfferings - 1)]
        int randOfferingNum = random.nextInt(numKillerOfferings);

        // print out the offering that corresponds with the random number
        // System.out.println("\nYou Selected: Map Offering"); // unsure if this is wanted
        System.out.println("\nRandom Killer Offering: " + killerOfferings.get(randOfferingNum));
    }

    // wrapper functions - used when individual components are selected, and gives the user more options
    // ...
}
