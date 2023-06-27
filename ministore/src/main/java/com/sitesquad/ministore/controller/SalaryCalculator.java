package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.constant.SystemConstant;
import com.sitesquad.ministore.dto.PayslipDTO;
import com.sitesquad.ministore.dto.RequestMeta;
import com.sitesquad.ministore.model.Payslip;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.model.UserShift;
import com.sitesquad.ministore.service.PayslipService;
import com.sitesquad.ministore.service.UserService;
import com.sitesquad.ministore.service.shift.UserShiftService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

/**
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

    @Autowired
    RequestMeta requestMeta;

    private Payslip addPayslip(List<UserShift> userShiftList) {
        Payslip payslip = new Payslip();

        payslip.setUserId(userShiftList.get(0).getUserId());
        Integer shiftCount = new Integer(0);
        Double salary = new Double(0.0);
        Integer totalHour = new Integer(0);

        for (UserShift userShift : userShiftList) {
            Long workingPeriod = ChronoUnit.HOURS.between(userShift.getStartTime(), userShift.getEndTime());
            Double salaryInADay = userShift.getUser().getRole().getBaseSalary() * workingPeriod * userShift.getShift().getCoefficient();
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
            totalHour += workingPeriod.intValue();
//            userShift.setIsPaid(true);
            userShiftService.edit(userShift);
        }
        payslip.setShiftCount(shiftCount);

        payslip.setStartDate(Timestamp.from(userShiftList.get(0).getStartTime().toInstant()));
        payslip.setEndDate(Timestamp.from(userShiftList.get(userShiftList.size() - 1).getEndTime().toInstant()));
        payslip.setDate(Timestamp.from(SystemConstant.LOCAL_DATE_TIME_NOW.atZone(ZoneId.systemDefault()).toInstant()));
        payslip.setSalary(salary);
        payslip.setTotalHours(totalHour);
        payslipService.add(payslip);
        return payslip;
    }

    @Scheduled(cron = "0 0 0 30 * *")
    @PostMapping("/salary")
    public ResponseEntity<ResponseObject> calculateSalary() {
        if (!requestMeta.getRole().equals("admin")) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "You don't have permission", "")
            );
        }

        List<User> userList = userService.findAllExceptAdmin();

        for (User user : userList) {
            List<UserShift> userShiftList = userShiftService.findAllByIsPaidAndUserId(user.getUserId());
//            System.out.println(userShiftList);
            if (userShiftList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(404, "Not Found UserShift", "")
                );
            }
            addPayslip(userShiftList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Successfull", "")
        );
    }

    @PostMapping("/salary/user")
    public ResponseEntity<ResponseObject> calculateSalaryForAUser(@RequestParam Long userId) {
        if (!requestMeta.getRole().equals("admin")) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "You don't have permission", "")
            );
        }
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Not found user", "")
            );
        } else {
            List<UserShift> userShiftList = userShiftService.findAllByIsPaidAndUserId(userId);
            System.out.println(userShiftList);
            if (userShiftList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(404, "Not Found UserShift", "")
                );
            }
            addPayslip(userShiftList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Successful", "")
        );
    }

    @GetMapping("/salary/pay")
    public ResponseEntity<ResponseObject> paySalary(@RequestParam(required = false) Long payslipId) {
        if (!requestMeta.getRole().equals("admin")) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "You don't have permission", "")
            );
        }
        Payslip foundPayslip = new Payslip();
        foundPayslip = payslipService.findById(payslipId);
        if (foundPayslip == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Not found payslip", "")
            );
        }
        if (foundPayslip.getIsPaid() == null || foundPayslip.getIsPaid() == false) {
            foundPayslip.setIsPaid(true);
            payslipService.edit(foundPayslip);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Successfull", foundPayslip)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(404, "Payslip is paid", "")
        );
    }

    @GetMapping("/salary/viewByUser")
    public ResponseEntity<ResponseObject> viewSalaryByUser() {
        List<Payslip> payslipList = payslipService.findByUserId(requestMeta.getUserId());
        PayslipDTO payslipDTO = new PayslipDTO();
        for (Payslip payslip : payslipList) {
            if (payslip.getIsPaid() == null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                payslipDTO.setUserId(payslip.getUserId());
                payslipDTO.setStartDate(dateFormat.format(payslip.getStartDate()));
                payslipDTO.setEndDate(dateFormat.format(payslip.getEndDate()));
                payslipDTO.setShiftCount(payslip.getShiftCount());
                payslipDTO.setSalary(payslip.getSalary());
                payslipDTO.setTotalHour(payslip.getTotalHours());
                payslipDTO.setDate(dateFormat.format(payslip.getDate()));
            }
        }
        if (payslipDTO.getUserId() == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Not found payslip!", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Found", payslipDTO)
        );
    }
}
