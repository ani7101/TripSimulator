package utils;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Properties;

public class CredentialManager {
    private static File targetFile;

    private static Properties credentials;

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

    public static String accessCredentials(String key) {
        return credentials.getProperty(key);
    }

    public static void addNewCredentials(String key, String value) {
        try {
            if (!targetFile.exists())
                targetFile.createNewFile();

            FileWriter fileWriter = new FileWriter(targetFile.getAbsolutePath(), true);
            BufferedWriter bufferedWriter = new BufferedWriter((fileWriter));

            bufferedWriter.write(System.lineSeparator() + key + ":" + value);
            bufferedWriter.close();
        } catch (IOException ioe) {
            System.err.println("Properties file does not exist in " + targetFile.getAbsolutePath());
        }
    }

    public static @NotNull Boolean checkIfKeyValuePairExists(String queryKey, String queryValue) {
        for (String key : credentials.stringPropertyNames()) {
            if (key.equals(queryKey) && credentials.getProperty(queryKey).equals(queryValue))
                return true;
        }
        return false;
    }
}
