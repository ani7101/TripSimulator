package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVParser {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private CSVParser() {
        throw new AssertionError();
    }

    public static final String COMMA_DELIMITER = ",";

    public static ArrayList<ArrayList<Double>> parse(String filePath) {
        ArrayList<ArrayList<Double>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath));) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        return records;
    }

    private static ArrayList<Double> getRecordFromLine(String line) {
        ArrayList<Double> values = new ArrayList<Double>();

        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(Double.valueOf(rowScanner.next()));
            }
        }
        return values;
    }
}
