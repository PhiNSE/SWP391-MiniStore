package com.sitesquad.ministore.controller;


import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.model.Role;
import com.sitesquad.ministore.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController  
@RequestMapping(path = "/test/products")
// http://localhost:8080/Products
public class TamController {
    @Autowired
    private ProductRepository repository;
    @GetMapping()
    List<Product> getAllProduct(){
        return repository.findAll();
    }
    
    
    
    @GetMapping("/{id}")
    Product findById(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cannot find id =" +id));
    }
}
