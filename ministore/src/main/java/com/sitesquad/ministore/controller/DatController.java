package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.repository.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author ADMIN
 */
@Controller
public class DatController {

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/dat")
    public JSONArray getProducts() {
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            System.out.println(order.toString());
        }

        return new JSONArray(orders);
    }
}
