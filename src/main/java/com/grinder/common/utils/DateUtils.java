package com.grinder.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class DateUtils {
    public static String parseYYYYMMDD(LocalDateTime localDateTime) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(localDateTime);
    }

    public static String parseYYYYMMDD(LocalDateTime localDateTime, String separator) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy" + separator + "MM" + separator + "dd");
        return dateFormat.format(localDateTime);
    }
}
