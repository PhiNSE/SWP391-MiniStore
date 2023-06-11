/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.controller.shift;

import com.sitesquad.ministore.constant.RoleConstant;
import com.sitesquad.ministore.dto.UserDTO;
import com.sitesquad.ministore.dto.UserShiftDTO;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.model.UserShift;
import com.sitesquad.ministore.repository.UserRepository;
import com.sitesquad.ministore.service.UserService;
import com.sitesquad.ministore.service.shift.UserShiftService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ACER
 */
@RestController
@CrossOrigin
public class UserShiftController {

    @Autowired
    UserShiftService userShiftService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

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

    @GetMapping("/userShift/assign")
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

    @GetMapping("/userShift/check")
    public ResponseEntity<ResponseObject> checkAttendance(@RequestParam String type) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Check" + type + " succesfully ", ""));
    }
}
