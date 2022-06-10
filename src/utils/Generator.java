package utils;

import java.util.Random;
import static java.util.UUID.randomUUID;

public class Generator {
    private int currNumber = 1;

    public int yield() {
        int temp = currNumber;
        next();
        return currNumber;
    }

    public void next() { currNumber++; }

    public static String generateRandomUUID() { return randomUUID().toString(); }

    public static String generateRandomString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
