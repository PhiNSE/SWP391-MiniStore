package com.sitesquad.ministore.controller.admin;

import com.sitesquad.ministore.service.OrderDetailsService;
import com.sitesquad.ministore.service.OrderService;
import com.sitesquad.ministore.service.PayslipService;
import com.sitesquad.ministore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RevenueDashboard {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailsService orderDetailsService;
    @Autowired
    ProductService productService;
    @Autowired
    PayslipService payslipService;


}
