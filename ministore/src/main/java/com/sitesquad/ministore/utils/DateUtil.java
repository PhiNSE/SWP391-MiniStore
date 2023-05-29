package com.sitesquad.ministore.utils;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;

/**
 *
 * @author ADMIN
 */
public class DateUtil {

    public static boolean isWeekend(ZonedDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
