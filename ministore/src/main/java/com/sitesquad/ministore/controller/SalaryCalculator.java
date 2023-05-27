package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.Payslip;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.model.UserShift;
import com.sitesquad.ministore.service.PayslipService;
import com.sitesquad.ministore.service.UserService;
import com.sitesquad.ministore.service.UserShiftService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
public class SalaryCalculator {
    @Autowired
    PayslipService payslipService;
    
    @Autowired
    UserShiftService userShiftService;
    
    @Autowired
    UserService userService;
    
    @GetMapping("/salary")
    public ResponseEntity<ResponseObject> calculateSalary() {
        List<User> userList = userService.findAll();
        List<Payslip> payslipList = new ArrayList<>();
        for(User u: userList) {
            //create payslip
            Payslip payslip = new Payslip();
            UserShift userShift = new UserShift();
            payslip.setUserId(u.getId());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(200, "Successfull", payslipList)
        );
    }
}
