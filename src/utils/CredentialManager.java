package utils;

import java.io.*;
import java.util.Properties;

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

    public static String get(String key) {
        return credentials.getProperty(key);
    }

    public static void add(String key, String value) {
        if (has(key))
            return;
        try {
            if (!targetFile.exists())
                targetFile.createNewFile();

            FileWriter fileWriter = new FileWriter(targetFile.getAbsolutePath(), true);
            BufferedWriter bufferedWriter = new BufferedWriter((fileWriter));

            bufferedWriter.write(System.lineSeparator() + key + ":" + value);
            bufferedWriter.close();
        } catch (IOException ioe) {
            System.err.println("Properties file does not exist in " + targetFile.getAbsolutePath());
            ioe.printStackTrace();
        }
    }

    public static Boolean contains(String queryKey, String queryValue) {
        for (String key : credentials.stringPropertyNames()) {
            if (key.equals(queryKey) && credentials.getProperty(queryKey).equals(queryValue))
                return true;
        }
        return false;
    }

    public static boolean has(String queryKey) {
        for (String key : credentials.stringPropertyNames()) {
            if (key.equals(queryKey))
                return true;
        }
        return false;
    }
}
