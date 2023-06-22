package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.Payslip;
import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.repository.PayslipRepository;
import com.sitesquad.ministore.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Payslip foundPayslip = payslipRepository.findById(id).orElse(null);
        return foundPayslip;
    }
    
    public List<Payslip> findByUserId(Long id) {
        List<Payslip> foundPayslip = payslipRepository.findByUserId(id);
        return foundPayslip;
    }

    @Transactional
    public Payslip add(Payslip payslip) {
        payslip.setUser(userRepository.findById(payslip.getUserId()).get());
        return payslipRepository.save(payslip);
    }

    public Payslip edit(Payslip newPayslip) {
        return payslipRepository.save(newPayslip);
    }

    public boolean delete(Long id) {
        payslipRepository.deleteById(id);
        return payslipRepository.findById(id) == null;
    }
}
