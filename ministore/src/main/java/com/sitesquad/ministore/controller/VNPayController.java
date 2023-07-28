package com.sitesquad.ministore.controller;


import com.sitesquad.ministore.constant.SystemConstant;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.service.VNPayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@org.springframework.stereotype.Controller
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;

    @PostMapping("/submitOrder")
    public ResponseEntity<ResponseObject> submitOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request){
//        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String baseUrl = SystemConstant.FRONT_END_ROOT_URL;
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
        System.out.println(vnpayUrl);
//        return "redirect:" + vnpayUrl;
     return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Submit order to VN-PAY successfully", vnpayUrl));
    }

    @GetMapping("/vnpay-payment")
    public ResponseEntity<ResponseObject> GetMapping(HttpServletRequest request, Model model){
        int paymentStatus =vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        if(paymentStatus == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Order is paid by VN-PAY successfully", ""));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "Order failed to be paid by VN-PAY", ""));
        }

    }
}