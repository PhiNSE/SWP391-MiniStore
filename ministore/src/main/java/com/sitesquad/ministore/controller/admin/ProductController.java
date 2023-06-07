package com.sitesquad.ministore.controller.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.sitesquad.ministore.dto.ProductDTO;
import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.model.RequestMeta;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.repository.ProductRepository;
import com.sitesquad.ministore.repository.ProductTypeRepository;
import com.sitesquad.ministore.service.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author ADMIN
 */
@RestController
@CrossOrigin
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    RequestMeta requestMeta;

//    @GetMapping("/product")
//    public ResponseEntity<ResponseObject> getProducts() {
//        List<ProductDTO> products = productService.findAll();
//        if (products != null && !products.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject(200, "Product list", products)
//            );
//        } else {
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject(404, "Empty product list", "")
//            );
//        }
//    }
    @GetMapping("/product")
    public ResponseEntity<ResponseObject> getProducts(@RequestParam(required = false) Integer offset) {
        System.out.println("User Id: "+requestMeta.getUserId());
        System.out.println("User Name: "+requestMeta.getName());
        System.out.println("User Role: "+requestMeta.getRole());
        if (offset == null) {
            offset = 0;
        }
        Page<ProductDTO> productList = productService.findAll(offset);
        if (!productList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found Product List", productList)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Cant find Product id = " + id, "")
            );
        }
    }

    @GetMapping("/product/search")
    public ResponseEntity<ResponseObject> search(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long productTypeId,
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortType,
            @RequestParam(required = false) Integer offset
    ) {
        if (offset == null) {
            offset = 0;
        }
        Page<ProductDTO> foundProducts;
        foundProducts = productService.search(id, keyword, productTypeId, productCode, sortBy, sortType, offset);
        if (foundProducts != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Found Products ", foundProducts)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "Cant find any Products matched", "")
            );
        }
    }

    @PostMapping("/product")
    public ResponseEntity<ResponseObject> addProduct(@RequestBody Product product) {
        if(requestMeta.getRole().trim().equalsIgnoreCase("Admin")) {
            product.setIsDeleted(Boolean.FALSE);
            Product addProduct = productService.add(product);
            if (addProduct != null) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "Add sucessfully ", addProduct)
                );
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(500, "Cant add product", product)
                );
            }
        }else{
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                    new ResponseObject(406, "Access denied", "")
            );
        }

    }

    @PutMapping("/product")
    public ResponseEntity<ResponseObject> editProduct(@RequestBody Product product) {
        if(requestMeta.getRole().trim().equalsIgnoreCase("Admin")) {
            Product editedProduct = productService.edit(product);
            if (editedProduct != null) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "Edit sucessfully ", editedProduct)
                );
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(500, "Cant edit product", product)
                );
            }
        }else{
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                    new ResponseObject(406, "Access denied", "")
            );
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        if(requestMeta.getRole().trim().equalsIgnoreCase("Admin")) {
            Boolean isDeleted = productService.delete(id);
            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "Delete sucessfully ", "")
                );
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(500, "Cant delete product", "")
                );
            }
        }else{
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                    new ResponseObject(406, "Access denied", "")
            );
        }
    }
}
