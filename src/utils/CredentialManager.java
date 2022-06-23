package utils;

import java.io.*;

import java.util.Properties;

/**
 * Reads and writes values to the credentials.properties file in the HOME DIRECTORY of the project
 */
public class CredentialManager {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private CredentialManager() {
        throw new AssertionError();
    }

    private static final File targetFile;

    private static final Properties credentials;

    static
    {
        targetFile = new File("credentials.properties");
        credentials = new Properties();

        try {
            credentials.load(new FileInputStream(targetFile.getAbsolutePath()));
        } catch (IOException ioe) {
            System.err.println("Properties file does not exists in " + targetFile.getAbsolutePath());
        }
    }

    /**
     * Retrieves values from the credentials.properties files using the key argument
     * @param key Key string associated with the required value
     * @return String: Value associated to the key in case the value exists in the credentials file
     */
    public static String get(String key) {
        return credentials.getProperty(key);
    }

    /**
     * Adds a key value pair to the credentials
     * @param key Parameter used to access the value
     * @param value Parameter value obtained encoded using the key parameter
     */
    public static void add(String key, String value) {
        if (has(key))
            return;
        try {
            // Creates the required file if it does not exist
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }

            // Writes to the credentials.properties files
            FileWriter fileWriter = new FileWriter(targetFile.getAbsolutePath(), true);
            BufferedWriter bufferedWriter = new BufferedWriter((fileWriter));

            bufferedWriter.write(System.lineSeparator() + key + ":" + value);
            bufferedWriter.close();

            // Sets the key-value pair in the local properties storage
            credentials.setProperty(key, value);
        } catch (IOException ioe) {
            System.err.println("Properties file does not exist in " + targetFile.getAbsolutePath());
            ioe.printStackTrace();
        }
    }

    /**
     * Checks if the key-value pair exists in the credentials
     * @param queryKey Parameter used to access the required value
     * @param queryValue Parameter value
     * @return Boolean: Checks if the key-value pair exists in the properties file
     */
    public static Boolean contains(String queryKey, String queryValue) {
        for (String key : credentials.stringPropertyNames()) {
            if (key.equals(queryKey) && credentials.getProperty(queryKey).equals(queryValue))
                return true;
        }
        return false;
    }

    /**
     * Checks if the key exists in the credentials
     * @param queryKey Parameter key used to query in the credentials
     * @return Boolean: Checking whether the key exists in the properties file
     */
    public static boolean has(String queryKey) {
        for (String key : credentials.stringPropertyNames()) {
            if (key.equals(queryKey))
                return true;
        }
        return false;
    }
}
