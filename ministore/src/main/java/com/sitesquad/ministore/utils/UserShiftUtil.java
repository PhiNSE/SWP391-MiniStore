package com.sitesquad.ministore.utils;

import com.sitesquad.ministore.constant.RoleConstant;
import com.sitesquad.ministore.model.UserShift;

public class UserShiftUtil {
    public static boolean compareUserShiftRole(UserShift userShift, String constantRoleName){
        if(constantRoleName.equals(RoleConstant.SALER_ROLE_NAME)){
            if(userShift.getShift().getType().contains(RoleConstant.SALER_ROLE_NAME)) {
                return true;
            }
        }else if(constantRoleName.equals(RoleConstant.GUARD_ROLE_NAME)){
            if(userShift.getShift().getType().contains(RoleConstant.GUARD_ROLE_NAME)) {
                return true;
            }
        }
        return false;
    }
}
