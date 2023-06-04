package com.sitesquad.ministore.controller.order;

import com.sitesquad.ministore.dto.OrderDetailRequest;
import com.sitesquad.ministore.dto.VoucherRequest;
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
import com.sitesquad.ministore.service.VoucherService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author admin
 */
@CrossOrigin
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
    VoucherService voucherService;

    @Autowired
    ProductVoucherService productVoucherService;

    public Order createOrder(Voucher voucher) {
        Order order = new Order();
        order.setType(false);
        order.setUserId(new Long(1));
        Date date = new Date();
        order.setDate(new Timestamp(date.getTime()));
        System.out.println(voucher);
        if (voucher != null) {
            order.setVoucherId(voucher.getVoucherId());
        }
        return orderService.add(order);
    }

    @PostMapping("/orderDetail/create")
    public ResponseEntity<ResponseObject> createOrderDetail(@RequestBody OrderDetailRequest orderDetailRequest) {
        List<OrderDetails> orderDetails = orderDetailRequest.getOrderDetailList();
        System.out.println(orderDetails);
        Voucher voucher = orderDetailRequest.getVoucher();
        System.out.println(voucher);

        List<OrderDetails> orderDetailList = new ArrayList<>();
        Order order = createOrder(voucher);
        System.out.println(order);

        Double totalOrder = 0.0;
        for (OrderDetails ordDet : orderDetails) {
            //create orderDetail
            ordDet.setOrderId(order.getOrderId());
            if (ordDet.getQuantity() > productService.findById(ordDet.getProductId()).getQuantity()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(500, "Quantity is not enough", ordDet.getProductId())
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
                if (voucher != null) {
                    totalOrder += ordDet.getTotal();
                }
            }
        }
        order.setTotal(totalOrder);
        order = orderService.edit(order);
        orderDetailRequest.setOrderDetailList(orderDetailList);
        orderDetailRequest.setVoucher(voucher);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Successfull", orderDetailRequest)
        );
    }

    @GetMapping("/getVoucherByProductId")
    public ResponseEntity<ResponseObject> searchVoucherByProductId(@RequestParam(required = false) Long productId,
            @RequestParam(required = false) int results) {
        List<Voucher> voucherList = new ArrayList<>();
        List<ProductVoucher> productVoucherList = productVoucherService.findByProductId(productId);
        for (int i = 0; i < results && i < productVoucherList.size(); i++) {
            Voucher voucher = voucherService.findById(productVoucherList.get(i).getVoucherId());
            voucherList.add(voucher);
        }
        if (voucherList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
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

    @GetMapping("/getAllVouchers")
    public ResponseEntity<ResponseObject> getAllVoucher() {
        List<Voucher> voucherList = voucherService.findAll();
        List<Voucher> filteredVoucherList = new ArrayList<>();
        for (Voucher v : voucherList) {
            if (v.getIsApplyAll() == true) {
                filteredVoucherList.add(v);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Successfull", filteredVoucherList)
        );
    }

}
