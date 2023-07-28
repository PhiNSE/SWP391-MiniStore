package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.dto.PayslipDTO;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.model.Payslip;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.service.PayslipService;
import com.sitesquad.ministore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author admin
 */
@RestController
public class PayslipController {

    @Autowired
    PayslipService payslipService;
    @Autowired
    UserService userService;

    @GetMapping("/payslip")
    public ResponseEntity<ResponseObject> getAllPayslips() {
        List<Map<String, Object>> payslipList = payslipService.findPayslipCustom();
        List<Map<String, Object>> payslipMapList = new ArrayList<>();

        if (payslipList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Not found Payslips", "")
            );
        }

        for (Map<String, Object> payslip : payslipList) {
            Map<String, Object> payslipMap = new HashMap<>();
            Integer userId = (Integer) payslip.get("user_id");
            Integer sumShiftCount = (Integer) payslip.get("sum_shift_count");
            BigDecimal sumSalary = (BigDecimal) payslip.get("sum_salary");
            Integer sumTotalHours = (Integer) payslip.get("sum_total_hours");
            User user = userService.find(userId.longValue());
            payslipMap.put("userId", userId);
            payslipMap.put("name", user.getName());
            payslipMap.put("role", user.getRole().getName());
            if (sumShiftCount != null && sumSalary != null && sumTotalHours != null) {
                payslipMap.put("salary", Double.valueOf(sumSalary.doubleValue()));
                payslipMap.put("shiftCount", sumShiftCount);
                payslipMap.put("totalHour", sumTotalHours);
            } else {
                payslipMap.put("salary", 0);
                payslipMap.put("shiftCount", 0);
                payslipMap.put("totalHour", 0);
            }
            payslipMapList.add(payslipMap);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Found Payslips", payslipMapList)
        );
    }

    @GetMapping("/payslip/viewHistory")
    public ResponseEntity<ResponseObject> viewSalaryHistory(@RequestParam Long userId) {
        List<Payslip> payslipList = payslipService.findByUserId(userId);
        List<PayslipDTO> payslipDTOList = new ArrayList<>();

        if (payslipList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Not found payslip", "")
            );
        }
        for (Payslip payslip : payslipList) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            PayslipDTO payslipDTO = new PayslipDTO();

            payslipDTO.setPayslipId(payslip.getPayslipId());
            payslipDTO.setUserId(payslip.getUserId());
            payslipDTO.setName(payslip.getUser().getName());
            payslipDTO.setRoleName(payslip.getUser().getRole().getName());
            payslipDTO.setEmail(payslip.getUser().getEmail());
            payslipDTO.setStartDate(dateFormat.format(payslip.getStartDate()));
            payslipDTO.setEndDate(dateFormat.format(payslip.getEndDate()));
            payslipDTO.setShiftCount(payslip.getShiftCount());
            payslipDTO.setSalary(payslip.getSalary());
            payslipDTO.setTotalHour(payslip.getTotalHours());
            payslipDTO.setDate(dateFormat.format(payslip.getDate()));
            payslipDTO.setIsPaid(payslip.getIsPaid());
            payslipDTOList.add(payslipDTO);
        }
        System.out.println(payslipDTOList);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Found", payslipDTOList)
        );
    }

    @GetMapping("/payslip/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Payslip foundPayslip = payslipService.findById(id);
        if (foundPayslip != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found Payslip id = " + id, foundPayslip)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Cant find Payslip id = " + id, "")
            );
        }
    }

    @GetMapping("/payslip/search")
    public ResponseEntity<ResponseObject> findByUserId(@RequestParam Long id) {
        List<Payslip> foundPayslips = payslipService.findByUserId(id);
        if (!foundPayslips.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found Payslips ", foundPayslips)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Cant find any Payslips matched", "")
            );
        }
    }

    @PostMapping("/payslip")
    public ResponseEntity<ResponseObject> createPayslip(@RequestBody Payslip payslip) {
        Payslip addPayslip = payslipService.add(payslip);
        if (addPayslip != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Add sucessfully ", addPayslip)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Cant add payslip", addPayslip)
            );
        }
    }

    @DeleteMapping("/payslip/delete/{id}")
    public ResponseEntity<ResponseObject> deletePayslip(@PathVariable Long id) {
        Boolean isDeleted = payslipService.delete(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Delete sucessfully ", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Cant delete payslip", "")
            );
        }
    }
}
