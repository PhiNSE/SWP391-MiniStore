package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> findAll() throws NotFoundException {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty())
            throw new NotFoundException();

        return products;
    }
    
    public Product findByProductId(Long id){
        return productRepository.findById(id).orElse(null);
    }
    
    public Product add(Product product){
        return productRepository.save(product);
    }
}
