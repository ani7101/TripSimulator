package utils;

import java.util.ArrayList;
import java.util.Random;
import static java.util.UUID.randomUUID;

public class Generator {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private Generator() {
        throw new AssertionError();
    }

    public static String generateRandomUUID() { return randomUUID().toString(); }

    public static String generateRandomString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public static ArrayList<String> generateRandomUUID(int count) {
        ArrayList<String> UUIDS = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            UUIDS.add(Generator.generateRandomUUID());
        }

        return UUIDS;
    }
}
