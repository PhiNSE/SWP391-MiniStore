package com.sitesquad.ministore.constant;

import com.sitesquad.ministore.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ADMIN
 */
public class ShiftConstant{
    @Autowired
    ShiftRepository shiftRepository;
    private ShiftConstant(){}
    public static int NEW_DAY_START_HOUR = 6;
    public static String SALER_MORNING_SHIFT =  "saler-morning";
    public static String SALER_AFTERNOON_SHIFT = "saler-afternoon";
    public static String SALER_EVENING_SHIFT = "saler-night";
    
    public static String GUARD_MORNING_SHIFT = "guard-day";
    public static String GUARD_EVENING_SHIFT = "guard-night";
}
