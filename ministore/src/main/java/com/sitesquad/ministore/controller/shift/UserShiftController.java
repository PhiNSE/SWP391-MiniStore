/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.controller.shift;

import com.sitesquad.ministore.service.shift.UserShiftService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/gen")
    public void generateUserShifts(){
        userShiftService.generateUserShifts();
    }
}
