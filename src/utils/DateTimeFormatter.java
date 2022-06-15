package utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeFormatter {
    public static LocalDateTime iso8601ToLocalDateTime(String date) {
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withZone(ZoneId.of("UTC"));
        return LocalDateTime.parse(date, formatter);
    }

    public static String localDateTimeToIso8601(LocalDateTime date) {
        return date.format(java.time.format.DateTimeFormatter.ISO_INSTANT);
    }

}
