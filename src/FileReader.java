import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class FileReader {

    /**
     * readTextFile() - given a .txt file with one element on each line (plus spacing for readability),
     * read the file using Scanner and return an ArrayList of its contents.
     * This function assumes there is only one relevant piece of information on each line, or that the
     * lines are empty.
     *
     * @param filepath the path to the .txt file to read
     * @return an ArrayList of Strings containing each non-empty line of the given .txt file as an element
     * @throws FileNotFoundException if the file at filepath is not found
     */
    public static ArrayList<String> readTextFile(String filepath) throws FileNotFoundException {
        // create a scanner to read the given .txt file
        Scanner fileReader = new Scanner(new File(filepath));

        // create an ArrayList to hold the elements of the lines in the file
        ArrayList<String> fileElements = new ArrayList<>();

        // go through the file and add every new line
        while (fileReader.hasNext()) {
            // read the element
            String element = fileReader.nextLine();

            // if it's not an empty line, add it to the list
            if (!element.equals("")) {
                fileElements.add(element);
            }
        }

        // printing for debug: print the number of elements that the list found
        ///*
        int numElements = fileElements.size();
        System.out.println("Number of Elements in " + filepath + ": " + numElements);
        //*/

        // return the filled list
        return fileElements;
    }

    /**
     * readCSVFile() - given a .csv file with two elements on each line (and spacing, if necessary), read the
     * file using Scanner and return a HashMap of its contents.
     * This function assumes there are only two relevant pieces of information on each line, or that the
     * lines are empty.
     *
     * @param filepath the path to the .csv file to read
     * @return an HashMap containing String-String key-value pairs of each line in the given .csv
     * @throws FileNotFoundException if the file at filepath is not found
     */
    public static HashMap<String,String> readCSVFile(String filepath) throws FileNotFoundException {
        // create a scanner to read the given .csv file
        Scanner fileReader = new Scanner(new File(filepath));

        // create a HashMap to hold the comma-separated **pairs** in the file
        HashMap<String,String> fileElements = new HashMap<>();

        // go through the file and add every new line
        while (fileReader.hasNext()) {
            // read the element
            String element = fileReader.nextLine();
            String[] pair;

            // if it's not an empty line, continue parsing
            if (!element.equals("")) {
                // separate the elements by the comma in the center
                // the first element is the key, the second element is the value
                pair = element.split(",");
                fileElements.put(pair[0], pair[1]);
                // System.out.println("Key: " + pair[0] + " || Value: " + pair[1]); // for debugging
            }
        }

        // printing for debug: print the number of element pairs that the list found
        ///*
        int numElements = fileElements.size();
        System.out.println("Number of Element Pairs in " + filepath + ": " + numElements);
        //*/

        // return the filled hashmap
        return fileElements;
    }
}
