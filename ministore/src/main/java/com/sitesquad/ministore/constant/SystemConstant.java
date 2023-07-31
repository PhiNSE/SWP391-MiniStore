package com.sitesquad.ministore.constant;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

/**
 *
 * @author ADMIN
 */
public class SystemConstant{
    public SystemConstant(){
//        LOCAL_DATE_TIME_NOW = LocalDateTime.now();
//        ZONE_DATE_TIME_NOW =  ZonedDateTime.of(LocalDateTime.now(),ZoneId.of("Asia/Ho_Chi_Minh"));
//        MONDAY_CURRENT = LOCAL_DATE_TIME_NOW.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }
//    public LocalDateTime LOCAL_DATE_TIME_NOW;
//    public ZonedDateTime ZONE_DATE_TIME_NOW;
    public LocalDateTime MONDAY_CURRENT;
    public static String FRONT_END_ROOT_URL = "http://localhost:5173";
    public static  LocalDateTime LOCAL_DATE_TIME_NOW(){
        return  LocalDateTime.now();
    }
    public static  ZonedDateTime ZONE_DATE_TIME_NOW(){
        return  ZonedDateTime.now();
    }
    public static  LocalDateTime MONDAY_CURRENT(){
        return  LOCAL_DATE_TIME_NOW().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }
}
