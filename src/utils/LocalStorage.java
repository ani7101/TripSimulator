package utils;

import simulation.models.TripModel;

import java.io.*;
import java.util.ArrayList;

/**
 * Contains methods to store and retrieve objects from local storage
 */
public class LocalStorage {

    /**
     * Writes the objects into the file. <br>
     * NOTE - It writes and doesn't append to the file
     * @param objectList List of objects to be stored locally
     * @param filename Absolute path (or relative path from the HOME DIRECTORY) to the file
     * @param <T> Class of which objects are stored
     */
    public static <T> void write(ArrayList<T> objectList, String filename) {
        try {
            File tripModelsFile = new File(filename);

            if (!tripModelsFile.exists()) {
                tripModelsFile.createNewFile();
            }

            FileOutputStream writer = new FileOutputStream(tripModelsFile);
            ObjectOutputStream writeStream = new ObjectOutputStream(writer);

            writeStream.writeObject(objectList);
            writeStream.flush();
            writeStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the list of objects from the file
     * @param filename Absolute path (or relative path from the HOME DIRECTORY) to the file
     * @return ArrayList(T): List of object
     * @param <T> Class of which objects are stored
     */
    public static <T> ArrayList<T> read(String filename) {
        ArrayList<T> tList;

        try {
            File tripModelsFile = new File(filename);

            FileInputStream readData = new FileInputStream(tripModelsFile);
            ObjectInputStream readStream = new ObjectInputStream(readData);

            tList = (ArrayList<T>) readStream.readObject();
            readStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return tList;
    }
}
