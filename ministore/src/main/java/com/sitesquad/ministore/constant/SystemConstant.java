package com.sitesquad.ministore.constant;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

/**
 *
 * @author ADMIN
 */
public class SystemConstant{
    private SystemConstant(){}
    public static final LocalDateTime LOCAL_DATE_TIME_NOW = LocalDateTime.now();
    public static ZonedDateTime ZONE_DATE_TIME_NOW = ZonedDateTime.of(LocalDateTime.now(),ZoneId.of("Asia/Ho_Chi_Minh"));
    public static final LocalDateTime MONDAY_CURRENT = LOCAL_DATE_TIME_NOW.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    public static final String FRONT_END_ROOT_URL = "http://localhost:5173";
}
