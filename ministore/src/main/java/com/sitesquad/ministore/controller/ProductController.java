package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.repository.ProductRepository;
import com.sitesquad.ministore.repository.ProductTypeRepository;
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
 * @author ADMIN
 */
@RestController
public class ProductController {
    
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @GetMapping("/product")
    public List<Product> getProducts() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            System.out.println(product);
        }

        return products;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("200", "Found Product id = " + id, foundProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("404", "Cant find Product id = " + id, "")
            );
        }
    }
    @GetMapping("/product/search")
    public ResponseEntity<ResponseObject> findByProductTypeId(@RequestParam Long id,@RequestParam String name) {
//        List<Product> foundProducts = productRepository.findByProductTypeId(id);
        List<Product> foundProducts = productRepository.findByProductTypeIdAndName(id,name);
        if (!foundProducts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("200", "Found Products ", foundProducts)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("404", "Cant find any Products matched" , "")
            );
        }
    }

    @PostMapping("/product")
    public ResponseEntity<ResponseObject> addProduct(@RequestBody Product product) {
        product.setProductTypes(productTypeRepository.findById(product.getProductTypeId()).get());
        Product addProduct = productRepository.save(product);
        if (addProduct != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("200", "Add sucessfully ", addProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("500", "Cant add product", product)
            );
        }

    }

    @PutMapping("/product")
    public ResponseEntity<ResponseObject> editProduct(@RequestBody Product product) {
        product.setProductTypes(productTypeRepository.findById(product.getProductTypeId()).get());
        Product addProduct = productRepository.save(product);
                if (addProduct != null) {
                    productRepository.findById(product.getId()).get().setIsDeleted(true);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("200", "Edit sucessfully ", addProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("500", "Cant edit product", product)
            );
        }
        
    }
}
