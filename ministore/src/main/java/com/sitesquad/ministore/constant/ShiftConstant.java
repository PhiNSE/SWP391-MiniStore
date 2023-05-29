package com.sitesquad.ministore.constant;

import com.sitesquad.ministore.model.Shift;
import com.sitesquad.ministore.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ADMIN
 */
public class ShiftConstant{
    private ShiftConstant(){}
    public static int NEW_DAY_START_HOUR = 6;
    public static Long SALER_MORNING_SHIFT = new Long(1);
    public static Long SALER_AFTERNOON_SHIFT = new Long(2);
    public static Long SALER_EVENING_SHIFT = new Long(3);
    
    public static Long GUARD_MORNING_SHIFT = new Long(4);
    public static Long GUARD_EVENING_SHIFT = new Long(5);
}
