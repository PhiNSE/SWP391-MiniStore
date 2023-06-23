/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.controller.shift;

import com.sitesquad.ministore.constant.RoleConstant;
import com.sitesquad.ministore.constant.ShiftConstant;
import com.sitesquad.ministore.constant.SystemConstant;
import com.sitesquad.ministore.dto.RequestMeta;
import com.sitesquad.ministore.dto.UserShiftDTO;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.model.UserShift;
import com.sitesquad.ministore.repository.UserRepository;
import com.sitesquad.ministore.repository.UserShiftRepository;
import com.sitesquad.ministore.service.UserService;
import com.sitesquad.ministore.service.shift.UserShiftService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sitesquad.ministore.utils.ZonedDateTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/userShift")
    public ResponseEntity<ResponseObject> getUserShifts(@RequestParam(required = false) Integer offset) {
        if (offset == null) {
            offset = 0;
        }
        List<UserShift> userShifts = userShiftService.findOffset(offset);
        List<UserShiftDTO> userShiftDTOs = userShiftService.mapDTO(userShifts);
        if (userShifts != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found User Shift list", userShiftDTOs)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Not found User Shift  list", "")
            );
        }
    }

    @GetMapping("/userShift/generate")
    public ResponseEntity<ResponseObject> generateUserShifts() {
        userShiftService.generateUserShifts();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Generate user shifts for next 7 days successfully", "")
        );
    }

    @PostMapping("/userShift/assign")
    public ResponseEntity<ResponseObject> assignUserShift(@RequestBody(required = false) List<Map<String, Object>> request) {
        List<UserShift> assignedUserShifts = new ArrayList<>();
        if (request == null || request.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "List of User shift id & user id parameter not found ", "")
            );
        }
        for (Map<String, Object> item : request) {

            Long userShiftId = Long.parseLong(item.get("userShiftId").toString());
            Long userId = Long.parseLong(item.get("userId").toString());

            if (userId == null || userShiftId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(500, "Parameter id or user shift id is not recieved", "")
                );
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

//        if(userShift.getUserId()!=null){
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject(500, "THIS SHIFT IS ASSIGN ALREADY", userService.findById(userId))
//            );
//        }
            userShift.setUserId(user.getUserId());
            UserShift assignedUserShift = userShiftService.edit(userShift);
            assignedUserShifts.add(assignedUserShift);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Assign succesfully ", assignedUserShifts)
        );
    }

    @GetMapping("/userShift/checkin")
    public ResponseEntity<ResponseObject> checkin(@RequestParam Long userShiftId) {
//        System.out.println(" dòng 136 set user mặc định nhớ sửa");
//        Long userId = new Long(6);
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
        if (!userShift.getUserId().equals(user.getUserId())) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "This shift is not assigned to you", ""));
        }
        ZonedDateTime endCheckInTime = userShift.getStartTime();
        ZonedDateTime startCheckInTime = userShift.getStartTime().minusMinutes(ShiftConstant.LIMIT_CHECKIN_MINUTE);
        ZonedDateTime checkInTime = SystemConstant.ZONE_DATE_TIME_NOW;
        System.out.println(checkInTime);
        if (checkInTime.isBefore(startCheckInTime)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "You cant check in before " + startCheckInTime, ""));
        }
        if (checkInTime.isAfter(endCheckInTime)) {
            if (checkInTime.isAfter(endCheckInTime.plusMinutes(ShiftConstant.LIMIT_CHECKIN_MINUTE_LATE))) {
                userShift.setCheckInTime(SystemConstant.ZONE_DATE_TIME_NOW);
                userShift.setIsCheckedInLate(true);
                userShift = userShiftService.edit(userShift);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "You check in late! Ended since " + endCheckInTime, userShift));
            } else{
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "You cant check in after " + endCheckInTime.plusMinutes(ShiftConstant.LIMIT_CHECKIN_MINUTE_LATE), ""));
            }
        }
        userShift.setCheckInTime(SystemConstant.ZONE_DATE_TIME_NOW);
        userShift.setIsCheckedIn(true);
        userShift = userShiftService.edit(userShift);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Check in successfully!", userShift));
    }

    @GetMapping("/userShift/checkout")
    public ResponseEntity<ResponseObject> checkout(@RequestParam Long userShiftId) {
//        System.out.println(" dòng 172 set user mặc định nhớ sửa");
//        Long userId = new Long(6);
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
        if (!userShift.getUserId().equals(user.getUserId())) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "This shift is not assigned to you", ""));
        }
        ZonedDateTime startCheckOutTime = userShift.getEndTime();
        ZonedDateTime endCheckOutTime = userShift.getEndTime().plusMinutes(ShiftConstant.LIMIT_CHECKIN_MINUTE);
        ZonedDateTime checkOutTime = SystemConstant.ZONE_DATE_TIME_NOW;
        System.out.println(checkOutTime);
        if (checkOutTime.isBefore(startCheckOutTime)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "You cant check out before " + startCheckOutTime, ""));
        }
        if (checkOutTime.isAfter(endCheckOutTime)) {
            if(checkOutTime.isAfter(endCheckOutTime.plusMinutes(ShiftConstant.LIMIT_CHECKIN_MINUTE_LATE))){
                userShift.setCheckOutTime(SystemConstant.ZONE_DATE_TIME_NOW);
                userShift.setIsCheckedOutLate(true);
                userShift = userShiftService.edit(userShift);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "You checked out late! Ended since " + endCheckOutTime, userShift));
            } else{
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "You cant check in after " + endCheckOutTime.plusMinutes(ShiftConstant.LIMIT_CHECKIN_MINUTE_LATE), ""));
            }
        }
        userShift.setCheckOutTime(SystemConstant.ZONE_DATE_TIME_NOW);
        userShift.setIsCheckedOut(true);
        userShift = userShiftService.edit(userShift);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Check out successfully!", userShift));
    }

    @GetMapping("/userShift/schedule")
    public ResponseEntity<ResponseObject> getUserShiftByUserSession(@RequestParam(required = false) Integer offset) {
        if (offset == null) {
            offset = 0;
        }
        if (requestMeta.getUserId() == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "User id session not found", "")
            );
        }
        List<UserShift> userShifts = userShiftService.findOffset(offset, requestMeta.getUserId());
        List<UserShiftDTO> userShiftDTOs = userShiftService.mapDTO(userShifts);
        if (userShifts != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found User Shift list", userShiftDTOs)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Not found User Shift  list", "")
            );
        }
    }

    @GetMapping("/userShift/all")
    public ResponseEntity<ResponseObject> getAllUserShiftByUserSession() {
        if (requestMeta.getUserId() == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "User id session not found", "")
            );
        }
        List<UserShift> userShifts = userShiftRepository.findByUserId(requestMeta.getUserId());
        List<UserShiftDTO> userShiftDTOs = userShiftService.mapDTO(userShifts);
        if (userShifts != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found User Shift list", userShiftDTOs)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Not found User Shift  list", "")
            );
        }
    }
}
