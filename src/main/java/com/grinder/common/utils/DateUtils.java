package com.grinder.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String parseYYYYMMDD(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDateTime.format(formatter);
    }

    public static String parseForPattern(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    public static String parseYYYYMMDD(LocalDateTime localDateTime, String separator) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy" + separator + "MM" + separator + "dd");
        return localDateTime.format(formatter);
    }

    public static LocalDateTime parseYYYYMMDD(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.parse(date, formatter);
    }

    public static LocalDateTime parseForPattern(String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, formatter);
    }

    public static LocalDateTime[] parseForStartAndEndDate(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return new LocalDateTime[]{startDateTime, endDateTime};
    }
}
