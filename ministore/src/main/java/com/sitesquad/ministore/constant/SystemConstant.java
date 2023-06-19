package com.sitesquad.ministore.constant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 *
 * @author ADMIN
 */
public class SystemConstant{
    private SystemConstant(){}
    public static final LocalDateTime LOCAL_DATE_TIME_NOW = LocalDateTime.now();
    public static ZonedDateTime ZONE_DATE_TIME_NOW = ZonedDateTime.of(LocalDateTime.now(),ZoneId.of("Asia/Ho_Chi_Minh"));
    
    
}
