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

    public static String parseYYYYMMDD(LocalDateTime localDateTime, String separator) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy" + separator + "MM" + separator + "dd");
        return localDateTime.format(formatter);
    }
}
