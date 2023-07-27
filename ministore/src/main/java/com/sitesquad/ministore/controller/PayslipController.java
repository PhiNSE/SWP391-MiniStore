package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.dto.PayslipDTO;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.model.Payslip;
import com.sitesquad.ministore.service.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author admin
 */
@RestController
public class PayslipController {

    @Autowired
    PayslipService payslipService;

    @GetMapping("/payslip")
    public ResponseEntity<ResponseObject> getAllPayslips() {
        List<Payslip> payslipList = payslipService.findAll();
        List<Payslip> filteredPayslipList = new ArrayList<>();
        List<PayslipDTO> payslipDTOList = new ArrayList<>();

        if (payslipList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Not found Payslips", "")
            );
        }

        Integer shiftCount = new Integer(0);
        Integer totalHour = new Integer(0);
        Double salary = new Double(0);
        for (Payslip payslip : payslipList) {
            Set<Long> seenPayslip = new HashSet<>();
            if (seenPayslip.contains(payslip.getPayslipId())) {
                seenPayslip.add(payslip.getPayslipId());
                shiftCount += payslip.getShiftCount();
                totalHour += payslip.getShiftCount();
                salary += payslip.getShiftCount();
            }
        }

        for (Payslip payslip : filteredPayslipList) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            PayslipDTO payslipDTO = new PayslipDTO();


            payslipDTO.setPayslipId(payslip.getPayslipId());
            payslipDTO.setUserId(payslip.getUserId());
            payslipDTO.setName(payslip.getUser().getName());
            payslipDTO.setRoleName(payslip.getUser().getRole().getName());
            payslipDTO.setStartDate(dateFormat.format(payslip.getStartDate()));
            payslipDTO.setEndDate(dateFormat.format(payslip.getEndDate()));
            payslipDTO.setShiftCount(payslip.getShiftCount());
            payslipDTO.setSalary(payslip.getSalary());
            payslipDTO.setTotalHour(payslip.getTotalHours());
            payslipDTO.setDate(dateFormat.format(payslip.getDate()));
            payslipDTO.setIsPaid(payslip.getIsPaid());
            payslipDTOList.add(payslipDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Found Payslips", payslipDTOList)
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
