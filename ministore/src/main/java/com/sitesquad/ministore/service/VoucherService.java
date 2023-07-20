package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.Voucher;
import com.sitesquad.ministore.repository.VoucherRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class VoucherService {

    @Autowired
    VoucherRepository voucherRepository;

    public List<Voucher> findAll() {
        return voucherRepository.findByIsDeletedFalseOrIsDeletedIsNull();
    }

    public Voucher findById(Long id) {
        List<Voucher> voucherList = voucherRepository.findByIsDeletedFalseOrIsDeletedIsNull();
        Voucher foundVoucher = new Voucher();
        for(Voucher voucher: voucherList) {
            if(voucher.getVoucherId().equals(id)) {
                foundVoucher = voucher;
            }
        }
        return foundVoucher;
    }

    public List<Voucher> findByDescriptionOrName(String description, String name) {
        List<Voucher> foundVouchers = voucherRepository.searchByDescriptionContainingIgnoreCaseOrNameContainingIgnoreCase(description, name);
        return foundVouchers;
    }

    public Voucher add(Voucher voucher) {
//        voucherRepository.findById(voucher.getId()).get();
        return voucherRepository.save(voucher);
    }

    public boolean delete(Long id) {
        Voucher voucher = voucherRepository.findById(id).orElse(null);
        if (voucher == null) {
            return false;
        } else {
            voucher.setIsDeleted(true);
            voucherRepository.save(voucher);
            return true;
        }
    }

    public boolean minusQuantityOfVoucher(Long voucherId) {
        Voucher voucher = findById(voucherId);
        voucher.setQuantity(voucher.getQuantity() - 1);
        voucherRepository.save(voucher);
        return true;
    }
    public boolean plusQuantityOfVoucher(Long voucherId) {
        Voucher voucher = findById(voucherId);
        voucher.setQuantity(voucher.getQuantity() + 1);
        voucherRepository.save(voucher);
        return true;
    }
}
