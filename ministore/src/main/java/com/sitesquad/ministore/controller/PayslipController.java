package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.model.Payslip;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.repository.PayslipRepository;
import com.sitesquad.ministore.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
public class PayslipController {

    @Autowired
    PayslipRepository payslipRepository;
    
    @Autowired
    UserRepository userRepository;

    @GetMapping("/payslip")
    public List<Payslip> getAllPayslips() {
        List<Payslip> payslips = payslipRepository.findAll();
        return payslips;
    }

    @GetMapping("/payslip/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Payslip> foundPayslip = payslipRepository.findById(id);
        if (foundPayslip.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found Payslip id = " + id, foundPayslip)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Cant find Payslip id = " + id, "")
            );
        }
    }

    @GetMapping("/payslip/search")
    public ResponseEntity<ResponseObject> findByUserId(@RequestParam Long id, @RequestParam String name) {
        List<Payslip> foundPayslips = payslipRepository.findByUserId(id);
//        List<Order> foundOrders = orderRepository.findByUserIdAndName(id, name);
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
        payslip.setUser(userRepository.findById(payslip.getUserId()).get());
        Payslip addPayslip = payslipRepository.save(payslip);
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
}
