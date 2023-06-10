package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.model.OrderDetails;
import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.repository.OrderDetailsRepository;
import com.sitesquad.ministore.repository.OrderRepository;
import com.sitesquad.ministore.repository.ProductRepository;
import com.sitesquad.ministore.repository.ProductVoucherRepository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class OrderDetailsService {

    @Autowired
    OrderDetailsRepository orderDetailRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVoucherRepository productVoucherRepository;

    public List<OrderDetails> findAll() {
        return orderDetailRepository.findAll();
    }

    public OrderDetails findById(Long id) {
        Optional<OrderDetails> foundOrder = orderDetailRepository.findById(id);
        return foundOrder.get();
    }

    public OrderDetails add(OrderDetails orderDetail) {
        orderDetail.setOrderDet(orderRepository.findById(orderDetail.getOrderId()).get());
        orderDetail.setProduct(productRepository.findById(orderDetail.getProductId()).get());
        if (orderDetail.getProductVoucherId() != null) {

            orderDetail.setProductVoucher(productVoucherRepository.findById(orderDetail.getProductVoucherId()).get());
        }
        return orderDetailRepository.save(orderDetail);
    }

    public OrderDetails edit(OrderDetails newOrerDetail) {
        return orderDetailRepository.save(newOrerDetail);
    }



    public List<OrderDetails> importOrderDetail(List<Product> productList, Order order) {


        List<OrderDetails> orderDetailList = new ArrayList<>();
        for (Product product : productList) {
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setOrderId(order.getOrderId());
            orderDetail.setProductId(product.getProductId());
            orderDetail.setPrice(product.getCost());
            orderDetail.setQuantity(product.getQuantity());
            orderDetail.setTotal(product.getCost() * product.getQuantity());
            orderDetailList.add(orderDetail);
            orderDetail = add(orderDetail);
        }
        return orderDetailList;
    }
}
