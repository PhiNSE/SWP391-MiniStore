package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.model.OrderDetails;
import com.sitesquad.ministore.model.Payslip;
import com.sitesquad.ministore.model.Voucher;
import com.sitesquad.ministore.repository.OrderDetailsRepository;
import com.sitesquad.ministore.repository.OrderRepository;
import com.sitesquad.ministore.repository.PayslipRepository;
import com.sitesquad.ministore.repository.VoucherRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
public class DatController {

    @Autowired
    OrderRepository orderRepository;
    
    @Autowired
    OrderDetailsRepository orderDetailRepository;
    
    @Autowired
    VoucherRepository voucherRepository;
    
    @Autowired
    PayslipRepository payslipRepository;

//    test Order
//    @GetMapping("/dat")
//    public List<Order> getAllOrder() {
//        List<Order> orders = orderRepository.findAll();
//        return orders;
//    }
    
//    test OrderDetail
//    @GetMapping("/dat")
//    public List<OrderDetails> getAllOrderDetail() {
//        List<OrderDetails> orderDets = orderDetailRepository.findAll();
//        return orderDets;
//    }
    
//    test Voucher
//    @GetMapping("/dat")
//    public List<Voucher> getAllVouchers() {
//        List<Voucher> vouchers = voucherRepository.findAll();
//        return vouchers;
//    }
    
//    test Payslip
//    @GetMapping("/dat")
//    public List<Payslip> getPayslip() {
//        List<Payslip> payslips = payslipRepository.findAll();
//        return payslips;
//    }
}
