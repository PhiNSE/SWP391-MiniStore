package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.repository.ProductRepository;
import com.sitesquad.ministore.repository.ProductTypeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class ProductService implements UserDetailsService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    public List<Product> findAll() {
        List<Product> productList = productRepository.findByIsDeletedFalseOrIsDeletedIsNull();
        return productList;

    }

    public Page<Product> findAll(int offset) {
        Page<Product> productList = productRepository.findByIsDeletedFalseOrIsDeletedIsNull(PageRequest.of(offset, 9));
        return productList;

    }

    public Product findById(Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct.get();
    }

    public Page<Product> search(String keyword, int offset) {
        Page<Product> products = productRepository.findByCustomQuery(keyword,PageRequest.of(offset,9));
        return products;
    }

    public Product add(Product product) {
        product.setProductTypes(productTypeRepository.findById(product.getProductTypeId()).get());
        return productRepository.save(product);
    }

    public Product edit(Product newProduct) {
        Product oldProduct = productRepository.findById(newProduct.getProductId()).get();
        newProduct.setProductId(null);
        Product editedProduct = add(newProduct);
        if (editedProduct != null) {
            oldProduct.setIsDeleted(true);
            productRepository.save(oldProduct);
        }
        return editedProduct;
    }

    public boolean delete(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return false;
        } else {
            product.setIsDeleted(true);
            productRepository.save(product);
            return true;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
