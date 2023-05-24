package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.Payslip;
import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.repository.PayslipRepository;
import com.sitesquad.ministore.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class PayslipService {

    @Autowired
    PayslipRepository payslipRepository;
    
    @Autowired
    UserRepository userRepository;

    public List<Payslip> findAll() {
        List<Payslip> payslips = payslipRepository.findAll();
        return payslips;
    }

    public Payslip findById(Long id) {
        Optional<Payslip> foundPayslip = payslipRepository.findById(id);
        return foundPayslip.get();
    }

    public Payslip add(Payslip payslip) {
        payslip.setUser(userRepository.findById(payslip.getUserId()).get());
        return payslipRepository.save(payslip);
    }

//    public Product edit(Product newProduct) {
//        Product oldProduct = payslipRepository.findById(newProduct.getId()).get();
//        newProduct.setId(null);
//        Product editedProduct = add(newProduct);
//        if (editedProduct != null) {
//            oldProduct.setIsDeleted(true);
//            payslipRepository.save(oldProduct);
//        }
//        return editedProduct;
//    }

    public boolean delete(Long id) {
        payslipRepository.deleteById(id);
        return payslipRepository.findById(id) == null;
    }
}
