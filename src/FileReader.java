import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class FileReader {

    /**
     * readTextFile() - given a .txt file with one element on each line (plus spacing for readability),
     * read the file using Scanner and return an ArrayList of its contents.
     * This function assumes there is only one relevant piece of information on each line, or the lines
     * are empty.
     *
     * @param filepath the path to the .txt file to read
     * @return an ArrayList containing each non-empty line of the given .txt file as an element
     * @throws FileNotFoundException
     */
    static ArrayList<String> readTextFile(String filepath) throws FileNotFoundException {
        // create a scanner to read the given .txt file
        Scanner fileReader = new Scanner(new File(filepath));

        // an ArrayList to hold the elements of the lines in the file
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
        int numElements = fileElements.size();
        System.out.println("Number of Elements in " + filepath + ": " + numElements);

        // return the filled list
        return fileElements;
    }

    // static ??? readCSVFile(String filepath) throws FileNotFoundException {}
}
