package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Converts the contents of a csv file into an ArrayList
 */
public class CSVParser {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private CSVParser() {
        throw new AssertionError();
    }

    public static final String COMMA_DELIMITER = ",";

    /**
     * Returns an ArrayList of the geoLocations in the format ArrayList(ArrayList(Double))
     * @param filePath Absolute path to the CSV file
     * @return ArrayList of the geolocations in the csv file
     */
    public static ArrayList<ArrayList<Double>> parseGeoLocation(String filePath) {
        ArrayList<ArrayList<Double>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                records.add(getGeoLocationFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        return records;
    }

    /**
     * Converts the String containing a comma separated geolocation into an ArrayList of Doubles
     * @param line Input linein the 'comma separated' format
     * @return Parsed ArrayList format of geoLocation
     */
    private static ArrayList<Double> getGeoLocationFromLine(String line) {
        ArrayList<Double> values = new ArrayList<>();

        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(Double.valueOf(rowScanner.next()));
            }
        }
        return values;
    }
}
