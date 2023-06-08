package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.repository.OrderRepository;
import com.sitesquad.ministore.repository.UserRepository;
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
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VoucherRepository voucherRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        Optional<Order> foundOrder = orderRepository.findById(id);
        return foundOrder.get();
    }

    public Order add(Order order) {
        order.setOrderUser(userRepository.findById(order.getUserId()).get());
        if (order.getVoucherId() != null) {
            order.setVoucher(voucherRepository.findById(order.getVoucherId()).get());
        }
        return orderRepository.save(order);
    }

    public Order edit(Order newOrder) {
        return orderRepository.save(newOrder);
    }

    public Order importOrder(List<Product> products) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
