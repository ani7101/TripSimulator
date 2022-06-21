package utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTime {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private DateTime() {
        throw new AssertionError();
    }

    private static final java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneId.of("UTC"));

    public static LocalDateTime iso8601ToLocalDateTime(String date) {
        return LocalDateTime.parse(date, formatter);
    }

    public static String localDateTimeToIso8601(LocalDateTime date) {
         return date.format(formatter);
    }

}
