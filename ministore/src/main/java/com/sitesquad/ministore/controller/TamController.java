package com.sitesquad.ministore.controller;


import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.model.Role;
import com.sitesquad.ministore.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.servlet.function.ServerResponse.status;


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
    
    
// find id
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id){
        Optional<Product> foundProduct = repository.findById(id);
        if(foundProduct.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject(200,"Found Product id = " +id, foundProduct)
            );
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new ResponseObject(404, "Cant find Product id = "+id, "")
            );
        }
    }
    
// insert new Product POST method

    @GetMapping("/insert ")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"Product added", repository.save(newProduct))
        );
    }

}
