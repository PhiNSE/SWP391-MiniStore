package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.model.OrderDetails;
import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.model.ProductVoucher;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.model.Voucher;
import com.sitesquad.ministore.repository.OrderDetailsRepository;
import com.sitesquad.ministore.repository.OrderRepository;
import com.sitesquad.ministore.service.OrderDetailsService;
import com.sitesquad.ministore.service.OrderService;
import com.sitesquad.ministore.service.ProductService;
import com.sitesquad.ministore.service.ProductVoucherService;
import com.sitesquad.ministore.service.UserService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
public class OrderCreator {

    @Autowired
    OrderDetailsService orderDetailsService;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductVoucherService productVoucherService;

    public Order createOrder() {
        Order order = new Order();
        order.setType(false);
        order.setUserId(new Long(1));
        Date date = new Date();
        order.setDate(new Timestamp(date.getTime()));
        return orderService.add(order);
    }

    @GetMapping("/orderDetail/create")
    public ResponseEntity<ResponseObject> viewOrderDetail(@RequestBody List<OrderDetails> orderDetails) {
        List<OrderDetails> orderDetailList = new ArrayList<>();
        Order order = createOrder();

        for (OrderDetails ordDet : orderDetails) {
            //create orderDetail
            ordDet.setOrderId(order.getId());
            if (ordDet.getQuantity() > productService.findById(ordDet.getProductId()).getQuantity()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(500, "Quantity is not enough", "")
                );
            } else {
                ordDet = orderDetailsService.add(ordDet);
                if (ordDet.getProductVoucherId() != null) {
                    ordDet.setTotal(ordDet.getPrice() * ordDet.getQuantity() * (1 - ordDet.getProductVoucher().getVoucher().getPercentDiscount()));
                } else {
                    ordDet.setTotal(ordDet.getPrice() * ordDet.getQuantity());
                }
                ordDet = orderDetailsService.edit(ordDet);
                orderDetailList.add(ordDet);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(200, "Successfull", orderDetailList)
        );
    }
}