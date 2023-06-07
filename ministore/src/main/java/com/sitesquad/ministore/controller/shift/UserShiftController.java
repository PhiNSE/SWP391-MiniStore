/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.controller.shift;

import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.model.UserShift;
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
    
    @GetMapping("/userShift/assign")
    public ResponseEntity<ResponseObject> assignUserShift(){
                return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Generate user shifts for next 7 days successfully", "")
        );
    }

    @GetMapping("/userShift/generate")
    public ResponseEntity<ResponseObject> generateUserShifts() {
        userShiftService.generateUserShifts();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Generate user shifts for next 7 days successfully", "")
        );
    }
}
