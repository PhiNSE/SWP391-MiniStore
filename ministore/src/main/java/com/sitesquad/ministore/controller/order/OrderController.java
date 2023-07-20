package com.sitesquad.ministore.controller.order;

import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.service.OrderService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 */
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/order")
    public ResponseEntity<ResponseObject> getAllOrder() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Found Order", orderService.findAll())
        );
    }

    @GetMapping("/order/findInADay")
    public ResponseEntity<ResponseObject> getOrderInADay() {
        List<Order> orderList = orderService.findAll();
        List<Order> filteredorderList = new ArrayList<>();
        for (Order order : orderList) {
            //extract date in timestamp to month
            Date date = new Date(order.getDate().getTime());
            Date currentTime = new Date(System.currentTimeMillis());
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date);
            cal2.setTime(currentTime);
            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
            if (sameDay == true) {
                filteredorderList.add(order);
            }
        }
        if (filteredorderList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Not found order", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Found Order", filteredorderList)
        );
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Order foundOrder = orderService.findById(id);
        if (foundOrder != null) {
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
    public ResponseEntity<ResponseObject> search(@RequestParam(required = false) Long id) {
//        List<Order> foundOrders = orderRepository.findByUserId(id);
        List<Order> foundOrders = orderService.findAll();
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

//    @PostMapping("/order")
//    public ResponseEntity<ResponseObject> createOrder(@RequestBody Order order) {
//        Order addOrder = orderService.add(order);
//        if (addOrder != null) {
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject(200, "Add sucessfully ", addOrder)
//            );
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    new ResponseObject(500, "Cant add order detail", addOrder)
//            );
//        }
//    }

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
