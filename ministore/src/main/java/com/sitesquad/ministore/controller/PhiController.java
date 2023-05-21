package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.model.ProductType;
import com.sitesquad.ministore.repository.ProductRepository;
import com.sitesquad.ministore.repository.ProductTypeRepository;
import com.sitesquad.ministore.service.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    ProductService productService;

    @GetMapping("/product")
    public List<Product> getProducts() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            System.out.println(product);
        }

        return products;
    }

    @GetMapping("/producttype")
    public List<ProductType> getProductTypes() {
        List<ProductType> productTypes = productTypeRepository.findAll();
        for (ProductType ProductType : productTypes) {
            System.out.println(ProductType);
        }

        return productTypes;
    }

    @GetMapping("/product/{id}")
    public Product getProductByProductId(@PathVariable Long id) {
        return productService.findByProductId(id);

    }

    @GetMapping("/product/bytypeid/{id}")
    public List<Product> getProductByProductTypeId(@PathVariable Long id) {
        List<Product> products = productRepository.findByProductTypeId(id);
        return products;
    }

//    @PostMapping("/product")
//    public Product addProduct(@RequestBody JSONObject object) throws JSONException{
//        Product product = new Product();
//        product.setName((String)object.get("name"));
//        return productService.add(product);
//    }
}
