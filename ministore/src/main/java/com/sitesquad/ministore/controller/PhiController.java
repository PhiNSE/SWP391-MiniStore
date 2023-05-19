package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.model.ProductType;
import com.sitesquad.ministore.repository.ProductRepository;
import com.sitesquad.ministore.repository.ProductTypeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
public class PhiController {

    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    ProductTypeRepository productTypeRepository;

//    @GetMapping("/phi-test")
//    public String homepage() {
//        List<Product> products = productRepository.findAll();
//        for (Product product : products) {
//            System.out.println(product.toString());
//        }
//        List<ProductType> productTypes = productTypeRepository.findAll();
//        for (ProductType productType : productTypes) {
//            System.out.println(productType.toString());
//        }
//
//        return "index";  // Trả về trang index.html
//    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            System.out.println(product.toString());
        }

        return products;
    }
}
