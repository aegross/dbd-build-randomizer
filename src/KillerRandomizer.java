// imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random; // if Random doesn't work: Math RANDOM FORMULA: Math.random() * (max - min + 1) + min

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
    HashMap<String,String> killerListLegendary;
    ArrayList<String> killerPerks;
    ArrayList<String> killerOfferings;

    // collection sizes
    int numKillers;
    int numKillersLegendary;
    int numKillerPerks;
    int numKillerOfferings;

    public void setup() {
        // ...
        System.out.println("\n---------------------------------    Randomizer - Killer    -----------------------------------\n");

        // read all the "general" files
        try {
            // killer list
            String killerListFilepath = filepathKiller + "/killer_list.csv";
            killerList = FileReader.readCSVFile(killerListFilepath);
            numKillers = killerList.size();

            // killer list, including legendary or other notable cosmetics
            String killerListLegendaryFilepath = filepathKiller + "/killer_list_legendary.csv";
            killerListLegendary = FileReader.readCSVFile(killerListLegendaryFilepath);
            numKillersLegendary = killerListLegendary.size();

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
    }

    public void generateKillerPerks(int numPerks, boolean allUnique) {

    }

    // wrapper functions - used when individual components are selected, and gives the user more options
    public void killerPerkWrapper() {

    }
}
