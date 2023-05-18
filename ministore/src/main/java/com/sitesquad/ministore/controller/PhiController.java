package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController  
public class PhiController {
//    
//    @Autowired
//    ProductRepository productRepository;
//        
//    @GetMapping("/phi")
//    public JSONArray getProducts() {
//                List<Product> products = productRepository.findAll();
//        for (Product product : products) {
//            System.out.println(product.toString());
//        }
//        
//        return new JSONArray(products);
//    }
}
