// imports
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

    // lists
    ArrayList<String> killerPerks;
    ArrayList<String> killerOfferings;

    // list sizes
    int numKillerPerks;
    int numKillerOfferings;

    // hashmaps
    // ...

    public void setup() {
        // ...
        System.out.println("\n---------------------------------    Randomizer - Killer    -----------------------------------\n");

        // read all the "general" files
        try {
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
