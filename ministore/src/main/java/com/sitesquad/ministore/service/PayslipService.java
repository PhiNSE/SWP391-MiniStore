package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.Payslip;
import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.repository.PayslipRepository;
import com.sitesquad.ministore.repository.UserRepository;

import java.util.*;

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

    public List<Payslip> findByIsPaidFalseOrNull() {
        return payslipRepository.findByIsPaidNullOrIsPaidFalse();
    }
    
    public List<Payslip> findByUserId(Long id) {
        List<Payslip> foundPayslip = payslipRepository.findByUserId(id);
        return foundPayslip;
    }

    public List<Map<String, Object>> findPayslipCustom() {
        return payslipRepository.findByCustom();
    }

    public List<Payslip> findPayslipByMonth(int month) {
        List<Payslip> payslipList = payslipRepository.findAll();
        List<Payslip> payslipInAMonth = new ArrayList<>();

        for(Payslip payslip: payslipList) {
//            extract date in timestamp to month
            long timestamp = payslip.getDate().getTime();
            Date date = new Date(timestamp);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int time = cal.get(Calendar.MONTH) + 1;
            if (time == month) {
                payslipInAMonth.add(payslip);
            }
        }
        return payslipInAMonth;
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
