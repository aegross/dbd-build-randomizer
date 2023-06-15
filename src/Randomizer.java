import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Interface: Randomizer
 * Includes methods and imports necessary in all three Randomizer classes.
 */

public interface Randomizer {
    /**
     * setup() - sets up each randomizer to receive user input after an object using this interface
     * (AllRandomizer, KillerRandomizer, SurvivorRandomizer) is created.
     */
    void setup();
}
