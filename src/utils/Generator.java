package utils;

import java.util.ArrayList;
import java.util.Random;
import static java.util.UUID.randomUUID;

/**
 * Generates random values (Strings, numbers)
 */
public class Generator {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private Generator() {
        throw new AssertionError();
    }

    private static final String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";


    /**
     * Generates a random UUID (128 bit value) which has a very, very small probability of collision
     * @return String: Randomly generated UUID
     */
    public static String generateRandomUUID() { return randomUUID().toString().toUpperCase(); }


    /**
     * Generates a random alphanumerical String of required length
     * @param length Required number of characters in the random string to be generated
     * @return String: Randomly generated alphanumerical String
     */
    public static String generateRandomString(int length) {
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }


    /**
     * Generates a list of random UUIDs
     * @param count Required number of UUIDs
     * @return ArrayList(String): list of randomly generated UUIDs in String
     */
    public static ArrayList<String> generateRandomUUID(int count) {
        ArrayList<String> UUIDS = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            UUIDS.add(Generator.generateRandomUUID());
        }

        return UUIDS;
    }
}
