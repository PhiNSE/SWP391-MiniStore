package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.Payslip;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.model.UserShift;
import com.sitesquad.ministore.service.PayslipService;
import com.sitesquad.ministore.service.UserService;
import com.sitesquad.ministore.service.shift.UserShiftService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
@CrossOrigin
public class SalaryCalculator {

    @Autowired
    PayslipService payslipService;

    @Autowired
    UserShiftService userShiftService;

    @Autowired
    UserService userService;

    @GetMapping("/salary")
    public ResponseEntity<ResponseObject> calculateSalary() {
        List<UserShift> userShiftList = userShiftService.findAllByIsPaid();
        userShiftList = userShiftService.findAllByUserId(new Long(1));
        if(userShiftList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(200, "Not Found User", "")
            );
        }
        System.out.println(userShiftList);

        Payslip payslip = new Payslip();
        payslip.setUserId(userShiftList.get(0).getUserId());
        Integer shiftCount = new Integer(0);
        Double salary = new Double(0.0);
        
        for (UserShift userShift : userShiftList) {
            Double salaryInADay = userShift.getUser().getBaseSalary() * userShift.getShift().getCoefficient();
            if(userShift.isWeekend() == true) { // weekend
                salaryInADay *= 2; // using coeffience const
            } else { // not weekend
                salaryInADay *= 1.5; // using coeffience const
            }
            if(userShift.isHoliday() == true) { // holiday
                salaryInADay *= 3; // using coeffience const
            }
            salary += salaryInADay;
            shiftCount++;
            userShift.setPaid(true);
            userShift = userShiftService.edit(userShift);
        }
        System.out.println(shiftCount);
        payslip.setShiftCount(shiftCount);
        System.out.println(salary);
        payslip.setSalary(salary);
        payslip = payslipService.add(payslip);
        
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Successfull", payslip)
        );
    }
}
