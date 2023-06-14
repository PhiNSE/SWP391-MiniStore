package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.Payslip;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.service.PayslipService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    PayslipService payslipService;

    @GetMapping("/payslip")
    public ResponseEntity<ResponseObject> getAllPayslips() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Found Payslips", payslipService.findAll())
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
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

    //    @PutMapping("/order")
//    public ResponseEntity<ResponseObject> editOrder(@RequestBody Order order) {
//        order.setOrderUser(userRepository.findById(order.getUserId()).get());
//        Order addOrder = orderRepository.save(order);
//        if (addOrder != null) {
////            orderRepository.findById(order.getId()).get().setIsDeleted(true);
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject(200, "Edit sucessfully ", addOrder)
//            );
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    new ResponseObject(500, "Cant edit order", order)
//            );
//        }
//
//    }
}
