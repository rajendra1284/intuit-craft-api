package com.intuit.craft.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final String DEFAULT_ZONE_ID = "America/Los_Angeles";

    private static final String DATE_TIME_FORMAT = "MM/dd/yyyy-hh:mm:ss";

    /**
     * default ZoneId: America/Los_Angeles
     * 
     * @return
     */
    public static String defaultZoneId() {
        return DEFAULT_ZONE_ID;
    }

    /**
     * format the given Instant object to MM/dd/yyyy-hh:mm:ss format.
     * 
     * @param dateTime
     * @return String
     */
    public static String formatDateTime(Instant instant) {
        ZonedDateTime dateTime = instant.atZone(ZoneId.of(DEFAULT_ZONE_ID));
        return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(dateTime);
    }

    /**
     * format the given ZoneDateTime object to MM/dd/yyyy-hh:mm:ss format.
     * 
     * @param dateTime
     * @return String
     */
    public static String formatDateTime(ZonedDateTime dateTime) {
        return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(dateTime);
    }

    /**
     * format the given LocalDateTime object to MM/dd/yyyy-hh:mm:ss format.
     * 
     * @param dateTime
     * @return String
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(dateTime);
    }
}
