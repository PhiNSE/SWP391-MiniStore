package com.sitesquad.ministore.controller.admin;

import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.model.Voucher;
import com.sitesquad.ministore.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
@RestController

public class VoucherController {

    @Autowired
    VoucherService voucherService;

    @GetMapping("/voucher")
    public List<Voucher> findAll() {
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


    @GetMapping("/getAllVouchers")
    public ResponseEntity<ResponseObject> getAllVoucher() throws NullPointerException {
        List<Voucher> voucherList = voucherService.findAll();
        List<Voucher> filteredVoucherList = new ArrayList<>();
        for (Voucher v : voucherList) {
            if (v.getIsApplyAll() != null) {
                if (v.getIsApplyAll() == true) {
                    if (v.getQuantity() > 0) {
                        filteredVoucherList.add(v);
                    }
                }
            }
        }
        if (filteredVoucherList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Voucher not found", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Sucessfull", filteredVoucherList)
        );
    }
}
