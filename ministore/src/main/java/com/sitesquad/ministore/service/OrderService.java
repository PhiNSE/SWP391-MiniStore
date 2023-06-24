package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.repository.OrderRepository;
import com.sitesquad.ministore.repository.UserRepository;
import com.sitesquad.ministore.repository.VoucherRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
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

    public List<Order> findByDate(YearMonth yearMonth) {
        List<Order> orderList = orderRepository.findAll();
        List<Order> orderInAMonth = new ArrayList<>();
        for (Order order : orderList) {
            Long date = order.getDate().getTime();
            YearMonth dateYearMonth = YearMonth.from(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()));
            if (dateYearMonth.equals(yearMonth)) {
                orderInAMonth.add(order);
            }
        }
        return orderInAMonth;
    }

    public List<Map<String, Object>> findMostSoldHour() {
        return orderRepository.findByCustom();
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
}
