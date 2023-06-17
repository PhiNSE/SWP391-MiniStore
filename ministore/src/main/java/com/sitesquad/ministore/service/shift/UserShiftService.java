/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.service.shift;

import com.sitesquad.ministore.constant.RoleConstant;
import com.sitesquad.ministore.constant.ShiftConstant;
import com.sitesquad.ministore.constant.SystemConstant;
import com.sitesquad.ministore.dto.UserShiftDTO;
import com.sitesquad.ministore.model.Shift;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.model.UserShift;
import com.sitesquad.ministore.repository.HolidayRepository;
import com.sitesquad.ministore.repository.ShiftRepository;
import com.sitesquad.ministore.repository.UserRepository;
import com.sitesquad.ministore.repository.UserShiftRepository;
import com.sitesquad.ministore.service.UserService;
import com.sitesquad.ministore.utils.DateUtil;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class UserShiftService {

    @Autowired
    UserShiftRepository userShiftRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShiftRepository shiftRepository;

    @Autowired
    HolidayRepository holidayRepository;

    @Autowired
    UserService userService;

    public List<UserShift> findAll() {
        return userShiftRepository.findAll();
    }
    
    public List<UserShift> findAllByIsPaidAndUserId(Long id) {
        List<UserShift> userShiftList = new ArrayList<>();
        List<UserShift> userShifts = userShiftRepository.findByIsPaidFalseOrIsPaidNull();
        for(UserShift userShift: userShifts) {
            if(userShift.getUserId() == id) {
                userShiftList.add(userShift);
            }
        }
        return userShiftList;
    }

    public List<UserShift> findOffset(Integer offset) {
        List<UserShift> userShifts = userShiftRepository.findAll();
        if (userShifts == null || userShifts.isEmpty()) {
            return null;
        }
        ZonedDateTime period = SystemConstant.ZONE_DATE_TIME_NOW;
        if (offset == null) {
            offset = 0;
        }
        List<UserShift> viewShifts = new ArrayList<>();
        for (UserShift userShift : userShifts) {
            if (userShift.getStartTime().getMonthValue() == period.getMonthValue() + offset) {
                viewShifts.add(userShift);
            }
        }
        return viewShifts;
    }

    public UserShift findById(Long id) {
        UserShift foundUserShift = userShiftRepository.findByUserShiftId(id);
        return foundUserShift;
    }

    public UserShift add(UserShift userShift) {
        if (userShift.getUserId() != null) {
            userShift.setUser(userRepository.findById(userShift.getUserId()).get());
        }
        userShift.setShift(shiftRepository.findById(userShift.getShiftId()).get());
        return userShiftRepository.save(userShift);
    }

    public UserShift edit(UserShift newUserShift) {
        if (newUserShift.getUserId() != null) {
            newUserShift.setUser(userRepository.findById(newUserShift.getUserId()).get());
        }
        return userShiftRepository.save(newUserShift);
    }

    @Scheduled(cron = "0 0 0 */7 * *")
    public void generateUserShifts() throws NullPointerException {
        System.out.print("Generating shifts...");
        UserShift lastShift = userShiftRepository.findTop1ByOrderByEndTimeDesc();
        ZonedDateTime startDate = SystemConstant.ZONE_DATE_TIME_NOW.withHour(0).withMinute(0).withSecond(0);
        if (lastShift != null) {
            startDate = lastShift.getEndTime().withHour(0).withMinute(0).withSecond(0);
        }
        Shift salerMorningShift = shiftRepository.findByType(ShiftConstant.SALER_MORNING_SHIFT).orElse(null);
        Shift salerAfternoonShift = shiftRepository.findByType(ShiftConstant.SALER_AFTERNOON_SHIFT).orElse(null);
        Shift salerEveningShift = shiftRepository.findByType(ShiftConstant.SALER_NIGHT_SHIFT).orElse(null);

        Shift guardDayShift = shiftRepository.findByType(ShiftConstant.GUARD_DAY_SHIFT).orElse(null);
        Shift guardNightShift = shiftRepository.findByType(ShiftConstant.GUARD_NIGHT_SHIFT).orElse(null);
        ZonedDateTime shiftDate = startDate;
        for (int i = 0; i < 7; i++) {
            add(getUserShift(salerMorningShift, shiftDate, false));
            add(getUserShift(salerAfternoonShift, shiftDate, false));
            add(getUserShift(salerEveningShift, shiftDate, true));

            add(getUserShift(guardDayShift, shiftDate, false));
            add(getUserShift(guardNightShift, shiftDate, true));
            shiftDate = shiftDate.plusDays(1);
        }
    }

    public UserShift getUserShift(Shift shift, ZonedDateTime shiftDate, Boolean isEndNextDay) throws NullPointerException {
        UserShift userShift = new UserShift();
        userShift.setShiftId(shift.getShiftId());
        userShift.setStartTime(shiftDate.withHour(shift.getStartWorkHour().intValue()));
        if (isEndNextDay) {
            userShift.setEndTime(shiftDate.withHour(shift.getEndWorkHour().intValue()).plusDays(1));
        } else {
            userShift.setEndTime(shiftDate.withHour(shift.getEndWorkHour().intValue()));
        }
        userShift.getStartTime();
        userShift.setIsWeekend(DateUtil.isWeekend(shiftDate));
        if (holidayRepository.findByDate(userShift.getStartTime().toLocalDate()) != null) {
            userShift.setIsHoliday(true);
        } else {
            userShift.setIsHoliday(false);
        }

        return userShift;
    }
// @Scheduled(cron = "* * * ? * *")
//    public void test(){
//        System.out.println(SystemConstant.ZONE_DATE_TIME_NOW);
//    }

    public List<UserShiftDTO> mapDTO(List<UserShift> userShifts) {
        return userShifts.stream()
                .map(userShift -> {
                    UserShiftDTO userShiftDTO = new UserShiftDTO();
                    userShiftDTO.setUserShiftId(userShift.getUserShiftId());
                    userShiftDTO.setUserId(userShift.getUserId());
                    userShiftDTO.setUser(userShift.getUser());
                    userShiftDTO.setShiftId(userShift.getShiftId());
                    userShiftDTO.setShift(userShift.getShift());
                    userShiftDTO.setStartTime(userShift.getStartTime());
                    userShiftDTO.setEndTime(userShift.getEndTime());
                    userShiftDTO.setIsHoliday(userShift.getIsHoliday());
                    userShiftDTO.setIsWeekend(userShift.getIsWeekend());
                    userShiftDTO.setIsCheckedIn(userShift.getIsCheckedIn());
                    userShiftDTO.setIsCheckedOut(userShift.getIsCheckedOut());
                    userShiftDTO.setIsPaid(userShift.getIsPaid());
                    String role = "";
                    if (userShift.getShift().getType().contains(RoleConstant.SALER_ROLE_NAME)) {
                        role = RoleConstant.SALER_ROLE_NAME;
                    } else if (userShift.getShift().getType().contains(RoleConstant.GUARD_ROLE_NAME)) {
                        role = RoleConstant.GUARD_ROLE_NAME;
                    }
                    List<User> availableEmployees = userService.findUserByRoleName(role);
                    userShiftDTO.setAvailableEmployees(availableEmployees);

                    return userShiftDTO;
                })
                .collect(Collectors.toList());
    }


    public List<UserShift> findByUserId(Long userId) {
        return userShiftRepository.findByUserId(userId);
    }
}
