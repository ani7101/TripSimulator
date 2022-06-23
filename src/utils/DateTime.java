package utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Converts between LocalDateTime to ISO8601
 */
public class DateTime {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private DateTime() {
        throw new AssertionError();
    }


    private static final java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneId.of("UTC"));

    /**
     * Parses the dat from ISO8601 to LocalDateTime
     * @param date Input date in the ISO8601 format
     * @return LocalDateTime: Same date in the LocalDateTime format
     */
    public static LocalDateTime iso8601ToLocalDateTime(String date) {
        return LocalDateTime.parse(date, formatter);
    }

    /**
     * Parses the dat from LocalDateTime to ISO8601
     * @param date Input date in the LocalDateTime format
     * @return String: Same date in the ISO8601 format
     */
    public static String localDateTimeToIso8601(LocalDateTime date) {
         return date.format(formatter);
    }

}
