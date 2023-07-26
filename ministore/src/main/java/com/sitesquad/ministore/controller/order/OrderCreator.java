package com.sitesquad.ministore.controller.order;

import com.sitesquad.ministore.dto.RequestMeta;
import com.sitesquad.ministore.dto.VoucherRequest;
import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.model.OrderDetails;
import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.model.ProductVoucher;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.model.Voucher;
import com.sitesquad.ministore.repository.OrderDetailsRepository;
import com.sitesquad.ministore.service.OrderDetailsService;
import com.sitesquad.ministore.service.OrderService;
import com.sitesquad.ministore.service.ProductService;
import com.sitesquad.ministore.service.ProductVoucherService;
import com.sitesquad.ministore.service.UserService;
import com.sitesquad.ministore.service.VoucherService;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author admin
 */
@RestController
public class OrderCreator {

    @Autowired
    OrderDetailsService orderDetailsService;


    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    VoucherService voucherService;

    @Autowired
    ProductVoucherService productVoucherService;

    @Autowired
    RequestMeta requestMeta;

    public Order createOrder(Voucher voucher) {
        Order order = new Order();
        order.setType(false);
        order.setUserId(requestMeta.getUserId());
//        order.setUserId(new Long(1));
        Date date = new Date();
        order.setDate(new Timestamp(date.getTime()));
        if (voucher != null) {
            order.setVoucherId(voucher.getVoucherId());
        }
        return orderService.add(order);
    }

    @PostMapping("/orderDetail/create")
    public ResponseEntity<ResponseObject> createOrderDetail(@RequestBody Map<String, Object> request) {
        List<Map<String, Object>> orderDetails = (List<Map<String, Object>>) request.get("data");
        Set<Object> seenIds = new HashSet<>();
        List<Map<String, Object>> filteredOrderDetail = new ArrayList<>();

        for (Map<String, Object> detail : orderDetails) {
            Object id = detail.get("productId");
            if (!seenIds.contains(id)) {
                seenIds.add(id);
                filteredOrderDetail.add(detail);
            }
        }

        // Extract the voucher ID from the "voucherId" field
        Voucher voucher = new Voucher();
        Long voucherIdApplyOrder = null;
        if (request.get("voucherId") != null) {
            voucherIdApplyOrder = Long.parseLong(request.get("voucherId").toString());
            voucher = voucherService.findById(voucherIdApplyOrder);
        }

        Order order = createOrder(voucher);

        Double totalOrder = 0.0;
        // Process each object in the list
        for (Map<String, Object> object : filteredOrderDetail) {
            Long productId = Long.parseLong(object.get("productId").toString());
            Double price = Double.parseDouble(object.get("price").toString());
            Long quantity = Long.parseLong(object.get("quantity").toString());
            Long voucherId = null;
            if (object.get("voucherId") != null) {
                voucherId = Long.parseLong(object.get("voucherId").toString());
            }

            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setOrderId(order.getOrderId());
            orderDetail.setProductId(productId);
            orderDetail.setPrice(price);

            if (quantity > productService.findById(productId).getQuantity()) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(500, "Quantity is not enough", orderDetail.getProductId())
                );
            } else {
                productService.minusQuantityOfProduct(quantity, productId);
                orderDetail.setQuantity(quantity);
                if (voucherId != null) {
                    Long productVoucherId = productVoucherService.findByVoucherIdAndProductId(voucherId, productId).getProductVoucherId();
                    if (productVoucherId == null) {
                        return ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject(500, "Can't find voucher for order detail", orderDetail.getProductId())
                        );
                    }
                    orderDetail.setProductVoucherId(productVoucherId);
                    orderDetail = orderDetailsService.add(orderDetail);

                    orderDetail.setTotal(orderDetail.getPrice() * orderDetail.getQuantity() * (1 - orderDetail.getProductVoucher().getVoucher().getPercentDiscount()));
                    orderDetail = orderDetailsService.edit(orderDetail);

                    voucherService.minusQuantityOfVoucher(voucherId);
                } else {
                    orderDetail.setTotal(orderDetail.getPrice() * orderDetail.getQuantity());
                    orderDetail = orderDetailsService.add(orderDetail);
                }

                totalOrder += orderDetail.getTotal();
            }
        }

        if (order.getVoucherId() == null) {
            order.setTotal(totalOrder);
        } else {
            order.setTotal(totalOrder * (1 - order.getVoucher().getPercentDiscount()));
            voucherService.minusQuantityOfVoucher(order.getVoucherId());
        }
        order = orderService.edit(order);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Successfull", "")
        );
    }

    @GetMapping("/getVoucherByProductId")
    public ResponseEntity<ResponseObject> searchVoucherByProductId(@RequestParam(required = false) Long productId,
                                                                   @RequestParam(required = false) int results) {
        List<Voucher> voucherList = new ArrayList<>();
        List<ProductVoucher> productVoucherList = productVoucherService.findByProductId(productId);
        for (int i = 0; i < results && i < productVoucherList.size(); i++) {
            Voucher voucher = voucherService.findById(productVoucherList.get(i).getVoucherId());
            if (voucher.getQuantity() > 0) {
                voucherList.add(voucher);
            }
        }
        if (voucherList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Voucher not found", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Successfull", voucherList)
            );
        }
    }

    @PostMapping("/applyVoucherToProducts")
    public ResponseEntity<ResponseObject> applyVoucherToProducts(@RequestBody VoucherRequest voucherRequest) {
        List<Product> productList = voucherRequest.getProductList();
        Voucher voucher = voucherRequest.getVoucher();
        if (voucherRequest.getVoucher().getIsApplyAll() == true) {
            voucher = voucherService.add(voucher);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Successfull", "")
            );
        }

        if (productList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Could not find any product", "")
            );
        }

        voucher = voucherService.add(voucher);
        List<ProductVoucher> productVoucherList = new ArrayList<>();

        for (Product p : productList) {
            ProductVoucher productVoucher = new ProductVoucher();
            productVoucher.setVoucherId(voucher.getVoucherId());
            productVoucher.setProductId(p.getProductId());
            productVoucher = productVoucherService.add(productVoucher);
            productVoucherList.add(productVoucher);
        }
        System.out.println(productVoucherList);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Successfull", "")
        );
    }

    @DeleteMapping("/cancelOrder/{orderId}")
    public ResponseEntity<ResponseObject> cancelOrder(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);

        if(order.getOrderId() == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Can't find order", "")
            );
        }

        //check if order date is expired or not
        Date date = new Date(order.getDate().getTime());
        Date currentTime = new Date(System.currentTimeMillis());
        long updatedTime = date.getTime() + (24 * 60 * 60 * 1000);
        Date updatedDate = new Date(updatedTime);
        // a day +24 hour < currentTime ==> expired date
        if(updatedDate.compareTo(currentTime) < 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "This order is expired", "")
            );
        }


        if (order.getVoucherId() != null) {
            Voucher voucher = voucherService.findById(order.getVoucherId());
            voucherService.plusQuantityOfVoucher(voucher.getVoucherId());
        }
        List<OrderDetails> orderDetailsList = orderDetailsService.findByOrderId(orderId);
        for (OrderDetails orderDetails : orderDetailsList) {
            productService.plusQuantityOfProduct(orderDetails.getQuantity(), orderDetails.getProductId());

            if (orderDetails.getProductVoucherId() != null) {
                ProductVoucher productVoucher = productVoucherService.findById(orderDetails.getProductVoucherId());
                productVoucher.setIsDeleted(true);
                voucherService.plusQuantityOfVoucher(productVoucher.getVoucherId());
                productVoucherService.edit(productVoucher);
            }
            orderDetails.setIsDeleted(true);
            orderDetailsService.edit(orderDetails);
        }
        order.setIsDeleted(true);
        orderService.edit(order);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Delete sucessfully ", "")
        );
    }
}
