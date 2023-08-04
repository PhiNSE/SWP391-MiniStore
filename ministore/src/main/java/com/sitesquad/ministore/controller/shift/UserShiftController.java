/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.controller.shift;

import com.sitesquad.ministore.constant.RoleConstant;
import com.sitesquad.ministore.constant.ShiftConstant;
import com.sitesquad.ministore.constant.SystemConstant;
import com.sitesquad.ministore.controller.SalaryCalculator;
import com.sitesquad.ministore.dto.RequestMeta;
import com.sitesquad.ministore.dto.UserShiftDTO;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.model.ShiftRequest;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.model.UserShift;
import com.sitesquad.ministore.repository.UserRepository;
import com.sitesquad.ministore.repository.UserShiftRepository;
import com.sitesquad.ministore.service.UserNotificationService;
import com.sitesquad.ministore.service.UserService;
import com.sitesquad.ministore.service.shift.HolidayService;
import com.sitesquad.ministore.service.shift.ShiftRequestService;
import com.sitesquad.ministore.service.shift.UserShiftService;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sitesquad.ministore.utils.ZonedDateTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.Notification;

/**
 * @author ACER
 */
@RestController
public class UserShiftController {

    @Autowired
    UserShiftService userShiftService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserShiftRepository userShiftRepository;

    @Autowired
    RequestMeta requestMeta;

    @Autowired
    ShiftRequestService shiftRequestService;

    @Autowired
    UserNotificationService userNotificationService;

    @Autowired
    HolidayService holidayService;

    @Autowired
    SalaryCalculator salaryCalculator;


    @GetMapping("/userShift")
    public ResponseEntity<ResponseObject> getUserShifts(@RequestParam(required = false) Integer offset) {
        if (offset == null) {
            offset = 0;
        }
        SystemConstant SystemConstant = new SystemConstant();
        List<UserShift> userShifts = userShiftService.findOffset(offset);
        List<UserShiftDTO> userShiftDTOs = userShiftService.mapDTO(userShifts);
        if (userShifts != null) {
            List<UserShiftDTO> inProgressUserShiftDTOs = new ArrayList<>();
            for (UserShiftDTO userShiftDTO : userShiftDTOs) {
                if (userShiftDTO.getStartTime().isBefore(SystemConstant.ZONE_DATE_TIME_NOW()) && userShiftDTO.getEndTime().isAfter(SystemConstant.ZONE_DATE_TIME_NOW())) {
                    inProgressUserShiftDTOs.add(userShiftDTO);
                }
            }

            System.out.println(SystemConstant.ZONE_DATE_TIME_NOW());
            Map<String, Object> userShiftMap = new HashMap<>();
            userShiftMap.put("userShifts", userShiftDTOs);
            userShiftMap.put("inProgressUserShifts", inProgressUserShiftDTOs);
            List<ShiftRequest> shiftRequests = new ArrayList<>();
            shiftRequests.addAll(shiftRequestService.findByUserIdAndByTypeFalse(requestMeta.getUserId()));
            userShiftMap.put("shiftRequests", shiftRequests);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found User Shift list", userShiftMap)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "Not found User Shift  list", "")
            );
        }
    }

    @GetMapping("/userShift/schedule")
    public ResponseEntity<ResponseObject> getUserShiftByUserSession(@RequestParam(required = false) Integer offset) {
        if (offset == null) {
            offset = 0;
        }
        SystemConstant SystemConstant = new SystemConstant();
        if (requestMeta.getUserId() == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "User id session not found", "")
            );
        }
        List<UserShift> userShifts = userShiftService.findOffset(offset, requestMeta.getUserId());
        List<UserShiftDTO> userShiftDTOs = userShiftService.mapDTO(userShifts);
        if (userShifts != null) {
            UserShiftDTO workingUserShiftDTO = null;
            for (UserShiftDTO userShiftDTO : userShiftDTOs) {
                if (userShiftDTO.getStartTime().isBefore(SystemConstant.ZONE_DATE_TIME_NOW()) && userShiftDTO.getEndTime().isAfter(SystemConstant.ZONE_DATE_TIME_NOW())) {
                    workingUserShiftDTO = userShiftDTO;
                }
            }
            Map<String, Object> userShiftMap = new HashMap<>();
            userShiftMap.put("userShifts", userShiftDTOs);
            userShiftMap.put("workingUserShift", workingUserShiftDTO);
            List<ShiftRequest> shiftRequests = shiftRequestService.findByUserId(requestMeta.getUserId());
            if (shiftRequests != null) {
                userShiftMap.put("shiftRequests", shiftRequests);
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found User Shift list", userShiftMap)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "Not found User Shift  list", "")
            );
        }
    }

    @GetMapping("/userShift/generate")
    public ResponseEntity<ResponseObject> generateUserShifts() {
        if(!requestMeta.getRole().equals(RoleConstant.ADMIN_ROLE_NAME)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Only admin can manually generate user shifts for next 7 days", ""));
        }
        userShiftService.generateUserShifts();
        userNotificationService.sendNotiAndMailToAllAdmins("New shift week is wait to assign!"," Go to schedule to assign new shifts");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Generate user shifts for next 7 days successfully", "")
        );
    }

    @PostMapping("/userShift/assign")
    public ResponseEntity<ResponseObject> assignUserShift(@RequestBody(required = false) List<Map<String, Long>> request) {
        List<UserShift> assignedUserShifts = new ArrayList<>();
        if (request == null || request.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "List of User shift id & user id parameter not found ", "")
            );
        }
        SystemConstant SystemConstant = new SystemConstant();
        for (Map<String, Long> item : request) {

            Long userShiftId = item.get("userShiftId");
            Long userId = item.get("userId");

            if (userShiftId == null) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(500, "Parameter user shift id is not received", "")
                );
            }
            if(userId == null){
                continue;
            }
            UserShift userShift = userShiftService.findById(userShiftId);
            User user = userRepository.findById(userId).orElse(null);
            if (user == null || userShift == null) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(500, "There's no such user or user shift", "")
                );
            }
            if (user.getRole().getName().equalsIgnoreCase(RoleConstant.ADMIN_ROLE_NAME)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(500, "CAN NOT ASSIGN ADMIN TO A SHIFT", user)
                );
            } else if (!userShift.getShift().getType().toLowerCase().contains(user.getRole().getName().toLowerCase())) {
                Map<String, Object> errorUserShift = new HashMap<>();
                errorUserShift.put("userShift", userShift);
                errorUserShift.put("user", user);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(500, "CAN NOT ASSIGN EMPLOYEE/GUARD TO A DIFFERENT ROLE SHIFT", errorUserShift)
                );
            }

        if(userShift.getUserId()!=null){
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject(500, "THIS SHIFT IS ASSIGN ALREADY", userService.findById(userId))
//            );
            //noti
            List<Long> assignEmployeeIds = new ArrayList<>();
            assignEmployeeIds.add(userShift.getUserId());
            String inThePast = "";
            if(userShift.getStartTime()!=null && userShift.getStartTime().isBefore(SystemConstant.ZONE_DATE_TIME_NOW())) {
                inThePast = " in the PAST ";
            }
            userNotificationService.
                    customCreateUserNotification("You have been removed from a shift"+inThePast+"!"
                            , " You have been removed by admin from shift"+inThePast+": "
                                    + userShift.getShift().getType()
                                    + " \n From " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                    .format(userShift.getStartTime())
                                    + " to " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                    .format(userShift.getEndTime())
                                    + "\nIf you have any problem please contact admin!"
                            , assignEmployeeIds);
        }
            userShift.setUserId(user.getUserId());
            UserShift assignedUserShift = userShiftService.edit(userShift);
            assignedUserShifts.add(assignedUserShift);
            //noti
            List<Long> assignEmployeeIds = new ArrayList<>();
            assignEmployeeIds.add(assignedUserShift.getUserId());
            String pastShift = "";
            if(userShift.getStartTime()!=null && userShift.getStartTime().isBefore(SystemConstant.ZONE_DATE_TIME_NOW())) {
                pastShift = " in the PAST ";
            }
                userNotificationService.
                        customCreateUserNotification("You have been assign to a new shift"+pastShift+"!"
                                , " You have been assign by admin to shift: "
                                        + assignedUserShift.getShift().getType()
                                        + " \n From " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                        .format(assignedUserShift.getStartTime())
                                        + " to " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                        .format(assignedUserShift.getEndTime())
                                        + "\nIf you have any problem please contact admin!"
                                , assignEmployeeIds);

        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Assign successfully ", assignedUserShifts)
        );
    }

    @GetMapping("/userShift/checkin")
    public ResponseEntity<ResponseObject> checkin(@RequestParam Long userShiftId) {
//        System.out.println(" dòng 136 set user mặc định nhớ sửa");
//        Long userId = new Long(6);
        SystemConstant SystemConstant = new SystemConstant();
        System.out.println("userid = " + requestMeta.getUserId());
        User user = userService.find(requestMeta.getUserId());
        UserShift userShift = userShiftRepository.findByUserShiftId(userShiftId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "User not found ", ""));
        }
        if (userShift == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "User shift not found ", ""));
        }
        if(userShift.getUserId() == null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "This shift have not been assigned", ""));
        }
        if (!userShift.getUserId().equals(user.getUserId())) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "This shift is not assigned to you", ""));
        }
        ZonedDateTime endCheckInTime = userShift.getStartTime();
        ZonedDateTime startCheckInTime = userShift.getStartTime().minusMinutes(ShiftConstant.LIMIT_CHECKIN_MINUTE);
        ZonedDateTime checkInTime = SystemConstant.ZONE_DATE_TIME_NOW();
        System.out.println(checkInTime);
        if (checkInTime.isBefore(startCheckInTime)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "You cant check in before " +  DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                            .format(startCheckInTime.minusMinutes(ShiftConstant.LIMIT_CHECKIN_MINUTE)), ""));
        }
        if (checkInTime.isAfter(endCheckInTime)) {
            if (checkInTime.isBefore(endCheckInTime.plusMinutes(ShiftConstant.LIMIT_CHECKIN_MINUTE_LATE))) {
                userShift.setCheckInTime(SystemConstant.ZONE_DATE_TIME_NOW());
                userShift.setIsCheckedInLate(true);
                userShift = userShiftService.edit(userShift);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "You check in late! Ended since " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                .format(endCheckInTime), userShift));
            } else{
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(500, "You cant check in after " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                .format(endCheckInTime.plusMinutes(ShiftConstant.LIMIT_CHECKIN_MINUTE_LATE)), ""));
            }
        }
        userShift.setCheckInTime(SystemConstant.ZONE_DATE_TIME_NOW());
        userShift.setIsCheckedIn(true);
        userShift = userShiftService.edit(userShift);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Check in successfully!", userShift));
    }

    @GetMapping("/userShift/checkout")
    public ResponseEntity<ResponseObject> checkout(@RequestParam Long userShiftId) {
//        System.out.println(" dòng 172 set user mặc định nhớ sửa");
//        Long userId = new Long(6);
        SystemConstant SystemConstant = new SystemConstant();
        System.out.println("userid = " + requestMeta.getUserId());
        User user = userService.find(requestMeta.getUserId());
        UserShift userShift = userShiftRepository.findByUserShiftId(userShiftId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "User not found ", ""));
        }
        if (userShift == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "User shift not found ", ""));
        }
        if(userShift.getUserId() == null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "This shift have not been assigned", ""));
        }
        if (!userShift.getUserId().equals(user.getUserId())) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "This shift was not assigned to you", ""));
        }
        ZonedDateTime startCheckOutTime = userShift.getEndTime();
        ZonedDateTime endCheckOutTime = userShift.getEndTime().plusMinutes(ShiftConstant.LIMIT_CHECKIN_MINUTE);
        ZonedDateTime checkOutTime = SystemConstant.ZONE_DATE_TIME_NOW();
        System.out.println(checkOutTime);
        if (checkOutTime.isBefore(startCheckOutTime)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(500, "You cant check out before " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                .format(startCheckOutTime), ""));
            }
            if (checkOutTime.isAfter(endCheckOutTime)) {
                if(checkOutTime.isBefore(endCheckOutTime.plusMinutes(ShiftConstant.LIMIT_CHECKIN_MINUTE_LATE))){
                    userShift.setCheckOutTime(SystemConstant.ZONE_DATE_TIME_NOW());
                    userShift.setIsCheckedOutLate(true);
                    userShift = userShiftService.edit(userShift);
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject(200, "You checked out late! Ended since " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                    .format(endCheckOutTime), userShift));
                } else{
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject(500, "You cant check out after " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                    .format(endCheckOutTime.plusMinutes(ShiftConstant.LIMIT_CHECKIN_MINUTE).plusMinutes(ShiftConstant.LIMIT_CHECKIN_MINUTE_LATE)), ""));
                }
        }
        userShift.setCheckOutTime(SystemConstant.ZONE_DATE_TIME_NOW());
        userShift.setIsCheckedOut(true);
        userShift = userShiftService.edit(userShift);

//        salaryCalculator.calculateSalary();

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Check out successfully!", userShift));
    }

    @GetMapping("/userShift/all")
    public ResponseEntity<ResponseObject> getAllUserShiftByUserSession() {
        if (requestMeta.getUserId() == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "User id session not found", "")
            );
        }
        SystemConstant SystemConstant = new SystemConstant();
        List<UserShift> userShifts = userShiftRepository.findByUserId(requestMeta.getUserId());
        List<UserShiftDTO> userShiftDTOs = userShiftService.mapDTO(userShifts);
        if (userShifts != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found User Shift list", userShiftDTOs)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "Not found User Shift  list", "")
            );
        }
    }

    @GetMapping("/userShift/checkLate")
    public ResponseEntity<ResponseObject> lateUserShiftChecker(){
        List<UserShift> userShifts = userShiftService.checkInLateChecker();
        if(userShifts==null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "There's no late shift", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Late found list", userShifts)
        );
    }

    @GetMapping("/userShift/holiday")
    public ResponseEntity<ResponseObject> holiday(){
        holidayService.retrieveVietnamHolidays();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Retrieve holiday successfully", null)
        );
    }

    @DeleteMapping("/userShift")
    public ResponseEntity<ResponseObject> deleteUserShift(@RequestBody Long userShiftId){
        userShiftRepository.deleteById(userShiftId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Cancel shift request successfully", null)
        );
    }

    @PutMapping("/userShift/fix")
    public ResponseEntity<ResponseObject> fixAttendance(@RequestParam Long userShiftId,Boolean isCheckedIn,Boolean isCheckedOut){
        UserShift userShift = userShiftService.findById(userShiftId);
        if(userShift==null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "Shift attendance parameter not received", null)
            );
        }
        System.out.println("            aaaaaaaa  "+isCheckedIn);
        System.out.println("            aaaaaaaa  "+isCheckedOut);
        userShift.setIsCheckedIn(isCheckedIn);
        userShift.setIsCheckedOut(isCheckedOut);
        if(userShift.getIsCheckedIn()!=null){
            userShift.setIsCheckedInLate(null);
        } else if(userShift.getIsCheckedInLate()!=null){
            userShift.setIsCheckedIn(null);
        }
        if(userShift.getIsCheckedOut()!=null){
            userShift.setIsCheckedOutLate(null);
        } else if(userShift.getIsCheckedOutLate()!=null){
            userShift.setIsCheckedOut(null);
        }
        userShiftService.edit(userShift);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Fix shift attendance successfully", userShift)
        );
    }
}
