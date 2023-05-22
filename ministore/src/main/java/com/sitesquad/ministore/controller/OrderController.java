package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.repository.OrderRepository;
import com.sitesquad.ministore.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/order")
    public List<Order> getAllOrder() {
        List<Order> orders = orderRepository.findAll();
        return orders;
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Order> foundOrder = orderRepository.findById(id);
        if (foundOrder.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found Order id = " + id, foundOrder)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Cant find Order id = " + id, "")
            );
        }
    }

    @GetMapping("/order/search")
    public ResponseEntity<ResponseObject> findByUserId(@RequestParam Long id, @RequestParam String name) {
        List<Order> foundOrders = orderRepository.findByUserId(id);
//        List<Order> foundOrders = orderRepository.findByUserIdAndName(id, name);
        if (!foundOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found Orders ", foundOrders)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Cant find any Orders matched", "")
            );
        }
    }

    @PostMapping("/order")
    public ResponseEntity<ResponseObject> createOrder(@RequestBody Order order) {
        order.setOrderUser(userRepository.findById(order.getUserId()).get());
        Order addOrder = orderRepository.save(order);
        if (addOrder != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Add sucessfully ", addOrder)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Cant add order", addOrder)
            );
        }
    }

//    @PutMapping("/order")
//    public ResponseEntity<ResponseObject> editOrder(@RequestBody Order order) {
//        order.setOrderUser(userRepository.findById(order.getUserId()).get());
//        Order addOrder = orderRepository.save(order);
//        if (addOrder != null) {
////            orderRepository.findById(order.getId()).get().setIsDeleted(true);
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject(200, "Edit sucessfully ", addOrder)
//            );
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    new ResponseObject(500, "Cant edit order", order)
//            );
//        }
//
//    }
}
