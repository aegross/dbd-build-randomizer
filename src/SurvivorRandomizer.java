import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class SurvivorRandomizer implements Randomizer {
    // constants
    final String filepathSurvivor = "/dbd_randomizer_files/survivor";

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
    ArrayList<String> itemTypes;

    // collection sizes
    int numSurvivors;
    int numSurvivorsLegendary;
    int numSurvivorPerks;
    int numSurvivorOfferings;
    int numItemTypes;

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
        System.out.println("\n---------------------------------    Randomizer - Survivor    ---------------------------------");

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

            // item types
            String itemTypeFilepath = filepathSurvivor + "/item_types.txt";
            itemTypes = FileReader.readTextFile(itemTypeFilepath);
            numItemTypes = itemTypes.size();
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
            System.out.println("\t(0) [RETURN TO MENU] \n\t(1) Full Survivor Build " +
                    "\n\t(2) Full Survivor Build (Including Legendary Cosmetics) " +
                    "\n\t(3) Survivor \n\t(4) Survivor (Including Legendary Cosmetics) " +
                    "\n\t(5) Perks \n\t(6) Items \n\t(7) Item Add-Ons \n\t(8) Offerings");
            System.out.print("Enter Response: ");
            responseNum = commandLineInputSurvivor.nextInt();
            System.out.println(); // spacing

            // take the user's input and direct the application to the right randomizer
            // full survivor build
            if (responseNum == 1) {
                generateFullSurvivorBuild(false);
            }
            // full survivor build (including legendary cosmetics)
            else if (responseNum == 2) {
                generateFullSurvivorBuild(true);
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
                survivorPerkWrapper();
            }
            // items
            else if (responseNum == 6) {
                generateItem(generateItemType());
            }
            // item add-ons
            else if (responseNum == 7) {
                itemAddOnWrapper();
            }
            // offerings
            else if (responseNum == 8) {
                generateSurvivorOffering();
            }
            // end the loop and return to main() in Main.java
            else if (responseNum == 0) {
                responseValid = true;
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
        String str;

        // generate numPerks amount of random numbers
        for (int i = 0; i < numPerks; i++) {
            // if the number has already been chosen, do NOT add it (all selected perks should be unique)
            // otherwise, add it
            do {
                randomNum = random.nextInt(numSurvivorPerks + 1);
                // if the max index was selected, that represents none
                if (randomNum == numSurvivorPerks) break; // don't repeat; this can be selected more than once
            }
            while (randomNumbers.contains(randomNum));

            // print out the perk(s) that were selected
            if (randomNum == numSurvivorPerks) {
                // if none was selected, don't add the number to the list of random numbers
                // set str manually since the index isn't valid (out of bounds)
                str = "(None)";
            }
            else {
                randomNumbers.add(randomNum);
                str = survivorPerks.get(randomNum);
            }
            System.out.println("Random Survivor Perk #" + (i+1) + ": " + str);
        }
    }

    /**
     * generateItemType() - randomly generates an item type (toolbox, med-kit, etc.) from the list
     * present in item_types.txt.
     * @return the file string corresponding to the item type generated
     */
    public String generateItemType() {
        // generate a random number
        int randItemNum = random.nextInt(numItemTypes + 1);

        // get the random item type and return it
        if (randItemNum == numItemTypes) {
            return "none"; // the index numItemTypes is out of bounds, refers to none
        }
        else {
            return itemTypes.get(randItemNum);
        }
    }

    /**
     * generateItem() - randomly generates an item of the given item type from the list(s) present in the
     * items folder.
     * @param itemType the type of the item to be generated
     */
    public void generateItem(String itemType) {
        // short-circuit: if the item type is none, print none and return
        if (itemType.equals("none")) {
            System.out.println("Random Item: (None)");
            return;
        }

        // otherwise, attempt to open the respective .txt file with the items of that type
        String itemFilepath = filepathSurvivor + "/items/" + itemType + "/" + itemType + ".txt";
        ArrayList<String> itemList = new ArrayList<>();

        try {
            itemList = FileReader.readTextFile(itemFilepath);
        }
        catch (FileNotFoundException f) {
            // print the issue
            System.out.println(f);
            f.printStackTrace();
        }

        // get the number of items of that type and generate a random number
        int numItems = itemList.size();
        int randItemNum = random.nextInt(numItems);

        // print the randomly selected item
        System.out.println("Random Item: " + itemList.get(randItemNum));
    }

    /**
     * generateItemAddOns() - randomly generates one to two add-ons for the given item type based on the
     * list(s) of add-ons present in the items folder.
     * @param itemType the type of add-ons to be generated
     * @param numAddOns the number of add-ons to be generated (1 or 2)
     */
    public void generateItemAddOns(String itemType, int numAddOns) {
        // short-circuit: if the item type is none immediately return
        if (itemType.equals("none")) return;

        // attempt to open the respective itemType_addons.txt file
        String addOnFilepath = filepathSurvivor + "/items/" + itemType + "/" + itemType + "_addons.txt";
        ArrayList<String> addOns = new ArrayList<>();

        try {
            addOns = FileReader.readTextFile(addOnFilepath);
        }
        catch (FileNotFoundException f) {
            // print the issue
            System.out.println(f);
            f.printStackTrace();
        }

        // make a list for the random numbers that will be chosen
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        String str;
        int randomNum;
        int addOnCount = addOns.size();

        // generate numAddOns number of random numbers
        for (int i = 0; i < numAddOns; i++) {
            // if the number has already been chosen, do NOT add it (all selected add-ons should be unique)
            // otherwise, add it
            do {
                randomNum = random.nextInt(addOnCount + 1);
                // if the max index was selected, that represents none
                if (randomNum == addOnCount) break; // don't repeat; this can be selected more than once
            }
            while (randomNumbers.contains(randomNum));

            // print out the add-on that was selected
            if (randomNum == addOnCount) {
                // if none was selected, don't add the number to the list of random numbers
                // set str manually since the index isn't valid (out of bounds)
                str = "(None)";
            }
            else {
                randomNumbers.add(randomNum);
                str = addOns.get(randomNum);
            }
            System.out.println("Random Add-On #" + (i+1) + ": " + str);
        }
    }

    /**
     * generateSurvivorOffering() - randomly generates a survivor offering from the list of offerings present
     * in survivor_offerings.txt.
     */
    public void generateSurvivorOffering() {
        // generate a random number from [0, numSurvivorOfferings]
        int randOfferingNum = random.nextInt(numSurvivorOfferings + 1);
        String str;

        // print out the offering that corresponds with the random number
        if (randOfferingNum == numSurvivorOfferings) str = "(None)";
        else str = survivorOfferings.get(randOfferingNum);

        System.out.println("Random Survivor Offering: " + str);
    }

    //// wrapper functions - used when individual components are selected, and gives the user more options

    /**
     * survivorPerkWrapper() - A wrapper function for generateSurvivorPerks() which allows for
     * the user to manually select the number of perks to be generated.
     */
    public void survivorPerkWrapper() {
        // setup
        boolean subResponse = false;
        int numPerks;

        // get the number of perks the user wants to generate
        while (!subResponse) {
            System.out.print("Enter the Number of Survivor Perks to Generate: ");
            numPerks = commandLineInputSurvivor.nextInt();

            if (numPerks > numSurvivorPerks) {
                System.out.println("ERROR: Not enough unique survivor perks exist. Please try again with a lower number (<= " + numSurvivorPerks + ").\n");
            }
            else if (numPerks <= 0) {
                System.out.println("ERROR: Input must be greater than zero. Please try again.\n");
            }
            else {
                // stop the while-loop and run generateKillerPerks() w/ the given number
                subResponse = true;
                generateSurvivorPerks(numPerks);
            }
        }
    }

    /**
     * ItemAddOnWrapper() - A wrapper function for generateItemAddOns() which allows for the user to
     * manually select the item to generate add-ons for, along with the number of add-ons.
     */
    public void itemAddOnWrapper() {
        // setup
        int itemSelection;
        int numAddOns;
        String itemType = null;

        // print out the item options
        String capitalized; // for better presentation; .csv for item types is more than necessary so this is done instead
        String current;

        for (int i = 1; i <= numItemTypes; i++) {
            current = itemTypes.get(i-1);
            capitalized = current.substring(0,1).toUpperCase() + current.substring(1);

            // print out the number (index + 1) used to select each item type
            System.out.println("(" + i + ") " + capitalized);
        }

        // get the item the user wants to generate add-ons for
        boolean subResponse = false;
        while (!subResponse) {
            System.out.print("\nSelect the Item Type to Generate Add-Ons For: ");
            itemSelection = commandLineInputSurvivor.nextInt();

            if ((itemSelection > numItemTypes) || (itemSelection <= 0)) {
                System.out.println("ERROR: Invalid Input. Please Try Again.\n");
            }
            else {
                // stop the while-loop and set the killer string to the correct key
                subResponse = true;
                itemType = itemTypes.get(itemSelection-1);
                // System.out.println("Item Type Selected: " + killer); for debugging
            }
        }

        // get the number of add-ons the user wants
        subResponse = false;
        while (!subResponse) {
            System.out.print("Enter the Number of Add-Ons to Generate: ");
            numAddOns = commandLineInputSurvivor.nextInt();

            if (numAddOns > 2) {
                System.out.println("ERROR: The value entered is too large. Please try again with a lower number (1 or 2).\n");
            }
            else if (numAddOns <= 0) {
                System.out.println("ERROR: Input must be greater than zero. Please try again.\n");
            }
            else {
                // stop the while-loop and run generateAddOns() w/ the given number
                subResponse = true;
                generateItemAddOns(itemType, numAddOns);
            }
        }
    }

    //// full build wrapper function

    /**
     * generateFullSurvivorBuild() - uses generateSurvivor(), generateItemType(), generateItem(),
     * generateItemAddOns(), generateSurvivorPerks(), and generateSurvivorOffering() to generate a complete
     * survivor build with an item, two unique add-ons, four unique perks, and an offering.
     *
     * @param legendary indicates whether legendary cosmetics can be chosen as a survivor
     */
    public void generateFullSurvivorBuild(boolean legendary) {
        // step one: generate the survivor
        String survivorKey;

        if (legendary) {
            // if legendary is true, use the legendary function
            survivorKey = generateSurvivorLegendary();
        }
        else {
            // otherwise, use the normal one
            survivorKey = generateSurvivor();
        }

        // step two: generate the item + add-ons
        String itemType = generateItemType();
        generateItem(itemType);
        generateItemAddOns(itemType, 2);

        // step three: generate the perks
        generateSurvivorPerks(4);

        // step four: generate the offering
        generateSurvivorOffering();
    }
}
