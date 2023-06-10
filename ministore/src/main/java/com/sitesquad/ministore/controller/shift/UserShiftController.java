/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.controller.shift;

import com.sitesquad.ministore.constant.RoleConstant;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.model.UserShift;
import com.sitesquad.ministore.repository.UserRepository;
import com.sitesquad.ministore.service.UserService;
import com.sitesquad.ministore.service.shift.UserShiftService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
        if (userShifts != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found User Shift list", userShifts)
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
    public ResponseEntity<ResponseObject> assignUserShift(@RequestParam Long userId, @RequestParam Long userShiftId) {
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
        if (user.getRoles().getName().equalsIgnoreCase(RoleConstant.ADMIN_ROLE_NAME)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "CAN NOT ASSIGN ADMIN TO A SHIFT", "")
            );
        } else if (!user.getRoles().getName().equalsIgnoreCase(userShift.getUser().getRoles().getName())) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "CAN NOT ASSIGN EMPLOYEE/GUARD TO A DIFFERENT ROLE SHIFT", "")
            );
        }
        userShift.setUserId(user.getUserId());
        UserShift userShiftassigned = userShiftService.edit(userShift);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Assign succesfully ", userShiftassigned.getUser().getName())
        );
    }
    @GetMapping("/userShift/check")    
    public ResponseEntity<ResponseObject> checkAttendance(@RequestParam String type){
        
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Check"+type+" succesfully ", ""));
    }
}
