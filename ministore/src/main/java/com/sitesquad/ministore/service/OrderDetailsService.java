package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.OrderDetails;
import com.sitesquad.ministore.repository.OrderDetailsRepository;
import com.sitesquad.ministore.repository.OrderRepository;
import com.sitesquad.ministore.repository.ProductRepository;
import com.sitesquad.ministore.repository.ProductVoucherRepository;
import java.util.List;
import java.util.Optional;
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
}
