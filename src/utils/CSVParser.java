package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVParser.class);


    //region Geolocation

    /**
     * Returns an ArrayList of the geoLocations in the format ArrayList(ArrayList(Double))
     * @param pathName Absolute path to the CSV file
     * @return ArrayList of the geolocations in the csv file
     */
    public static ArrayList<ArrayList<Double>> parseGeoLocation(String pathName) {
        ArrayList<ArrayList<Double>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(pathName))) {
            while (scanner.hasNextLine()) {
                records.add(getGeoLocationFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException fnfe) {
            LOGGER.error("CSV File does not exist:", fnfe);
            fnfe.printStackTrace();
        }

        return records;
    }


    /**
     * Converts the String containing a comma separated geolocation into an ArrayList of Doubles
     * @param line Input line in the 'comma separated' format
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

    //endregion
    //region Vehicle speeds

    public static ArrayList<Double> parseVehicleSpeeds(String pathName) {
        ArrayList<Double> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(pathName))) {
            while (scanner.hasNextLine()) {
                records.add(getVehicleSpeedFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException fnfe) {
            LOGGER.error("CSV File does not exist:", fnfe);
            fnfe.printStackTrace();
        }

        return records;
    }

    private static Double getVehicleSpeedFromLine(String line) {
        ArrayList<String> values = new ArrayList<>();

        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return Double.valueOf(values.get(1));
    }

    //endregion

}
