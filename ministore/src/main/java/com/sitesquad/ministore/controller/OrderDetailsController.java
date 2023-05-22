package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.model.OrderDetails;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.repository.OrderDetailsRepository;
import com.sitesquad.ministore.repository.OrderRepository;
import com.sitesquad.ministore.repository.ProductRepository;
import com.sitesquad.ministore.repository.ProductVoucherRepository;
import org.springframework.http.ResponseEntity;
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
public class OrderDetailsController {
    
    @Autowired
    OrderDetailsRepository orderDetailRepository;

    @Autowired
    OrderRepository orderRepository;
    
    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    ProductVoucherRepository productVoucherRepository;

    @GetMapping("/orderDetail")
    public List<OrderDetails> getAllOrderDetails() {
        List<OrderDetails> orders = orderDetailRepository.findAll();
        return orders;
    }

    @GetMapping("/orderDetail/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<OrderDetails> foundOrderDetail = orderDetailRepository.findById(id);
        if (foundOrderDetail.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found OrderDetail id = " + id, foundOrderDetail)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Cant find OrderDetail id = " + id, "")
            );
        }
    }

    @GetMapping("/orderDetail/search/order")
    public ResponseEntity<ResponseObject> findByOrderId(@RequestParam Long id) {
        List<OrderDetails> foundOrders = orderDetailRepository.findByOrderId(id);
//        List<Order> foundOrders = orderRepository.findByUserIdAndName(id, name);
        if (!foundOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found OrderDetails ", foundOrders)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Cant find any OrderDetails matched", "")
            );
        }
    }
        
    @GetMapping("/orderDetail/search/product")
    public ResponseEntity<ResponseObject> findByProductId(@RequestParam Long id) {
        List<OrderDetails> foundOrders = orderDetailRepository.findByProductId(id);
//        List<Order> foundOrders = orderRepository.findByUserIdAndName(id, name);
        if (!foundOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found OrderDetails ", foundOrders)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Cant find any OrderDetails matched", "")
            );
        }
    }
    
    @GetMapping("/orderDetail/search/productvoucher")
    public ResponseEntity<ResponseObject> findByProductVoucherId(@RequestParam Long id) {
        List<OrderDetails> foundOrders = orderDetailRepository.findByPrroductVoucherId(id);
//        List<Order> foundOrders = orderRepository.findByUserIdAndName(id, name);
        if (!foundOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found OrderDetails ", foundOrders)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Cant find any OrderDetails matched", "")
            );
        }
    }

    @PostMapping("/orderDetail")
    public ResponseEntity<ResponseObject> createOrder(@RequestBody OrderDetails orderDetail) {
        orderDetail.setOrderDet(orderRepository.findById(orderDetail.getOrderId()).get());
        orderDetail.setProduct(productRepository.findById(orderDetail.getProductId()).get());
        orderDetail.setProductVoucher(productVoucherRepository.findById(orderDetail.getProductVoucherId()).get());
        OrderDetails addOrderDetail = orderDetailRepository.save(orderDetail);
        if (addOrderDetail != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Add sucessfully ", addOrderDetail)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Cant add OrderDetail", addOrderDetail)
            );
        }
    }

//    @PutMapping("/orderDetail")
//    public ResponseEntity<ResponseObject> editOrder(@RequestBody OrderDetails orderDetail) {
//        orderDetail.setOrderDet(orderRepository.findById(orderDetail.getOrderId()).get());
//        orderDetail.setProduct(productRepository.findById(orderDetail.getProductId()).get());
//        orderDetail.setProductVoucher(productVoucherRepository.findById(orderDetail.getProductVoucherId()).get());
//        OrderDetails addOrderDetail = orderDetailRepository.save(orderDetail);
//        if (addOrderDetail != null) {
////            orderRepository.findById(order.getId()).get().setIsDeleted(true);
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject(200, "Edit sucessfully ", addOrderDetail)
//            );
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    new ResponseObject(500, "Cant edit OrderDetail", orderDetail)
//            );
//        }
//
//    }
}
