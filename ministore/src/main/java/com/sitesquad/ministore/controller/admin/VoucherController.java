package com.sitesquad.ministore.controller.admin;

import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.model.Voucher;
import com.sitesquad.ministore.repository.VoucherRepository;
import com.sitesquad.ministore.service.VoucherService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RestController

public class VoucherController {

    @Autowired
    VoucherService voucherService;

    @GetMapping("/voucher")
    public List<Voucher> getAllVoucher() {
        return voucherService.findAll();
    }

    @GetMapping("/voucher/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Voucher foundVoucher = voucherService.findById(id);
        if (foundVoucher != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found Voucher id = " + id, foundVoucher)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Cant find Voucher id = " + id, "")
            );
        }
    }

    @GetMapping("voucher/search")
    public ResponseEntity<ResponseObject> search(@RequestParam(required = false) String keyword) {
        String description = keyword;
        String name = keyword;
        List<Voucher> foundVouchers = voucherService.findByDescriptionOrName(description, name);
        if(foundVouchers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Not found!", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Found!", foundVouchers)
        );
    }

    @PostMapping("/voucher")
    public ResponseEntity<ResponseObject> createVoucher(@RequestBody Voucher voucher) {
//        order.setOrderUser(userRepository.findById(order.getUserId()).get());
        Voucher addVoucher = voucherService.add(voucher);
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

    @DeleteMapping("/voucher/delete/{id}")
    public ResponseEntity<ResponseObject> deleteVoucher(@PathVariable Long id) {
        Boolean isDeleted = voucherService.delete(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Delete sucessfully ", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "Cant delete voucher", "")
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
