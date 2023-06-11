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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Scheduled(cron = "*/10 * * * * *")
    @GetMapping("/salary")
    public ResponseEntity<ResponseObject> calculateSalary() {
        List<Payslip> payslipList = new ArrayList<>();
        List<User> userList = userService.findAllExceptAdmin();
        System.out.println(userList);

        for (User user : userList) {

            System.out.println(user.getUserId());
            List<UserShift> userShiftList = userShiftService.findAllByIsPaidAndUserId(user.getUserId());
            System.out.println(userShiftList);
            if (userShiftList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(404, "Not Found UserShift", "")
                );
            }

            Payslip payslip = new Payslip();
            System.out.println(userShiftList.get(0).getUserId());
            payslip.setUserId(userShiftList.get(0).getUserId());
            Integer shiftCount = new Integer(0);
            Double salary = new Double(0.0);

            for (UserShift userShift : userShiftList) {
                Double salaryInADay = userShift.getUser().getRole().getBaseSalary() * userShift.getShift().getCoefficient();
                if (userShift.getIsWeekend() == true) { // weekend
                    salaryInADay *= 2; // using coeffience const
                } else { // not weekend
                    salaryInADay *= 1.5; // using coeffience const
                }
                if (userShift.getIsHoliday() == true) { // holiday
                    salaryInADay *= 3; // using coeffience const
                }
                salary += salaryInADay;
                shiftCount++;
                userShift.setIsPaid(true);
                userShift = userShiftService.edit(userShift);
            }
            payslip.setShiftCount(shiftCount);
            System.out.println(salary);
            payslip.setSalary(salary);
            payslip = payslipService.add(payslip);

            payslipList.add(payslip);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Successfull", payslipList)
        );
    }
}
