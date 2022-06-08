package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CredentialsManager {
    public static void createCredentialsFile() {
        File credentials = new File("credentials.txt");
    }
    public static void writeCredentials(String username, String password) {
        createCredentialsFile();
        try {
            FileWriter writer = new FileWriter("credentials.txt");
            writer.write(username + "/n" + password);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readCredentials() {
        ArrayList<String> result = new ArrayList<String>();

        try {
            File credentials = new File("credentials.txt");
            Scanner reader = new Scanner(credentials);
            while (reader.hasNextLine()) {
                result.add(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void deleteFile() {
        File credentials = new File("credentials.txt");
        credentials.delete();
    }
}
