package com.sitesquad.ministore.controller.admin;

import com.sitesquad.ministore.dto.ProductDTO;
import com.sitesquad.ministore.dto.RequestMeta;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.model.Order;
import com.sitesquad.ministore.model.OrderDetails;
import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.service.OrderDetailsService;
import com.sitesquad.ministore.service.OrderService;
import com.sitesquad.ministore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

/**
 *
 * @author ADMIN
 */
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    RequestMeta requestMeta;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailsService orderDetailsService;

    @GetMapping("/product/all")
    public ResponseEntity<ResponseObject> searchAllProducts(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long productTypeId
    ) {
        List<ProductDTO> products = productService.searchAll(id, keyword, productTypeId, keyword);
        if (products != null && !products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Product list", products)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Empty product list", "")
            );
        }
    }

    @GetMapping("/product")
    public ResponseEntity<ResponseObject> getProducts(@RequestParam(required = false) Integer offset) {

        if (offset == null) {
            offset = 0;
        }
        Page<ProductDTO> productList = productService.findAll(offset);
        if (!productList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found Product List", productList)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Cant find product list", "")
            );
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Product foundProduct = productService.findById(id);
        if (foundProduct != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found Product id = " + id, foundProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Cant find Product id = " + id, "")
            );
        }
    }

    @GetMapping("/product/search")
    public ResponseEntity<ResponseObject> search(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long productTypeId,
//            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortType,
            @RequestParam(required = false) Integer offset
    ) {
        if (offset == null) {
            offset = 0;
        }
        Page<ProductDTO> foundProducts;
        foundProducts = productService.search(id, keyword, productTypeId, keyword, sortBy, sortType, offset);
        if (foundProducts != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found Products ", foundProducts)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Cant find any Products matched", "")
            );
        }
    }

    @PostMapping("/product")
    public ResponseEntity<ResponseObject> addProductList(@RequestBody(required = false) List<Product> productlist) throws NoSuchFieldException{
        if(!requestMeta.getRole().equals("admin")) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "You don't have permission", "")
            );
        }
        if (productlist == null || productlist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Product list parameter not found ", "")
            );
        }

        // check duplicate productList
        Set<String> seenProductCodes = new HashSet<>();
        List<Product> filteredProductList = new ArrayList<>();
        for (Product product : productlist) {
            String productCode = product.getProductCode();
            if (!seenProductCodes.contains(productCode) && product.getPrice()> product.getCost()) {
                seenProductCodes.add(productCode);
                filteredProductList.add(product);
            }
        }

        // add product into db
        for (Product product : filteredProductList) {
            productService.add(product);
        }

        // create order import product
        Order order = createOrder();
        List<OrderDetails> orderDetailList = orderDetailsService.importOrderDetail(filteredProductList, order);

        Double totalOrder = 0.0;
        for (OrderDetails orderDetail : orderDetailList) {
            totalOrder += orderDetail.getTotal();
        }
        order.setTotal(totalOrder);
        order = orderService.edit(order);

        if (productlist != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Add sucessfully ", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "Cant add product list", "")
            );
        }
    }

    public Order createOrder() {
        Order order = new Order();
        order.setType(true);
        order.setUserId(requestMeta.getUserId());
//        order.setUserId(new Long(1));
        Date date = new Date();
        order.setDate(new Timestamp(date.getTime()));
        return orderService.add(order);
    }

    @PutMapping("/product")
    public ResponseEntity<ResponseObject> editProduct(@RequestBody Product product) {

//        if(requestMeta.getRole().trim().equalsIgnoreCase("Admin")) {
        Product editedProduct = productService.edit(product);
        if (editedProduct != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Edit successfully ", editedProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Cant edit product", product)
            );
        }
//        }else{
//            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
//                    new ResponseObject(406, "Access denied", "")
//            );
//        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {

//        if(requestMeta.getRole().trim().equalsIgnoreCase("Admin")) {
        Boolean isDeleted = productService.delete(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Delete successfully ", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Cant delete product", "")
            );
        }
//        }else{
//            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
//                    new ResponseObject(406, "Access denied", "")
//            );
//        }
    }
}
