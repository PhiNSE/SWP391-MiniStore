package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.model.Voucher;
import com.sitesquad.ministore.repository.VoucherRepository;
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
public class VoucherController {

    @Autowired
    VoucherRepository voucherRepository;

    @GetMapping("/voucher")
    public List<Voucher> getAllVoucher() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers;
    }

    @GetMapping("/voucher/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Voucher> foundVoucher = voucherRepository.findById(id);
        if (foundVoucher.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found Voucher id = " + id, foundVoucher)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Cant find Voucher id = " + id, "")
            );
        }
    }

    @PostMapping("/voucher")
    public ResponseEntity<ResponseObject> createVoucher(@RequestBody Voucher voucher) {
//        order.setOrderUser(userRepository.findById(order.getUserId()).get());
        Voucher addVoucher = voucherRepository.save(voucher);
        if (addVoucher != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Add sucessfully ", addVoucher)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Cant add voucher", addVoucher)
            );
        }
    }

//    @PutMapping("/voucher")
//    public ResponseEntity<ResponseObject> editVoucher(@RequestBody Order order) {
////        order.setOrderUser(userRepository.findById(order.getUserId()).get());
//        voucherRepository.findOne(order);
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
