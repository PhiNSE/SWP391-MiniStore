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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ACER 
 */
@RestController
public class UserShiftController {
    @Autowired
    UserShiftService userShiftService;
    
    @GetMapping("/userShift")
    public ResponseEntity<ResponseObject> getUserShifts(){
        List<UserShift> userShifts = userShiftService.findAll();
        if (userShifts != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found ProductType list", userShifts)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Not found ProductType list", "")
            );
        }
    }
    
    @GetMapping("/userShift/generate")
    public void generateUserShifts(){
        userShiftService.generateUserShifts();
    }
}
