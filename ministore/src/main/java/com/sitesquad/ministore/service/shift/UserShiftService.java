/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.service.shift;

import com.sitesquad.ministore.constant.ShiftConstant;
import com.sitesquad.ministore.constant.SystemConstant;
import com.sitesquad.ministore.model.Shift;
import com.sitesquad.ministore.model.UserShift;
import com.sitesquad.ministore.repository.ShiftRepository;
import com.sitesquad.ministore.repository.UserRepository;
import com.sitesquad.ministore.repository.UserShiftRepository;
import com.sitesquad.ministore.utils.DateUtil;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public List<UserShift> findAll() {
        return userShiftRepository.findAll();
    }

    public UserShift findById(Long id) {
        Optional<UserShift> foundOrder = userShiftRepository.findById(id);
        return foundOrder.get();
    }

    public UserShift add(UserShift userShift) {
        if (userShift.getUserId() != null) {
            userShift.setUser(userRepository.findById(userShift.getUserId()).get());
        }
        userShift.setShift(shiftRepository.findById(userShift.getShiftId()).get());
        System.out.println(userShift);
        return userShiftRepository.save(userShift);
    }

    public UserShift edit(UserShift newUserShift) {
        return userShiftRepository.save(newUserShift);
    }

    @Scheduled(cron = "0 0 0 */7 * *")
    public void generateUserShifts() {
        System.out.print("Generating shifts...");
        List<UserShift> existShifts = userShiftRepository.findByIdOrderByEndTimeDesc(null);
        ZonedDateTime startDate = SystemConstant.ZONE_DATE_TIME_NOW.withHour(0).withMinute(0).withSecond(0);
        if (!existShifts.isEmpty()) {
            startDate = existShifts.get(0).getEndTime().withHour(0).withMinute(0).withSecond(0);
        }
        Shift salerMorningShift = shiftRepository.findById(ShiftConstant.SALER_MORNING_SHIFT).orElse(null);
        Shift salerAfternoonShift = shiftRepository.findById(ShiftConstant.SALER_AFTERNOON_SHIFT).orElse(null);
        Shift salerEveningShift = shiftRepository.findById(ShiftConstant.SALER_EVENING_SHIFT).orElse(null);
        ZonedDateTime shiftDate = startDate;
        for (int i = 0; i < 7; i++) {
            System.out.println(add(getUserShift(salerMorningShift, shiftDate, false)));
            System.out.println(add(getUserShift(salerAfternoonShift, shiftDate, false)));
            System.out.println(add(getUserShift(salerEveningShift, shiftDate, true)));
            shiftDate = shiftDate.plusDays(1);
        }

        UserShift lastUserShift = existShifts.get(0);
        if (lastUserShift.getStartTime().isAfter(SystemConstant.ZONE_DATE_TIME_NOW.plusDays(7))) {
            return;
        }

    }

    public UserShift getUserShift(Shift shift, ZonedDateTime shiftDate, Boolean isEndNextDay) {
        UserShift userShift = new UserShift();
        userShift.setShiftId(shift.getId());
        userShift.setStartTime(shiftDate.withHour(shift.getStartWorkHour().intValue()));
        if (isEndNextDay) {
            userShift.setEndTime(shiftDate.withHour(shift.getEndWorkHour().intValue()).plusDays(1));
        } else {
            userShift.setEndTime(shiftDate.withHour(shift.getEndWorkHour().intValue()));
        }
        userShift.setWeekend(DateUtil.isWeekend(shiftDate));

        System.out.println(userShift);
        return userShift;
    }
}
