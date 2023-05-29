package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.Product;
import com.sitesquad.ministore.repository.ProductRepository;
import com.sitesquad.ministore.repository.ProductTypeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    public List<Product> findAll() {
        List<Product> products = productRepository.findByIsDeletedFalseOrIsDeletedIsNull();
        return products;
    }

    public Product findById(Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct.get();
    }

    public List<Product> search(Long id, String keyword, Long productTypeId, String productCode, String priceSort) {
        String name = keyword;
        String type = keyword;
        Sort sort = null;
        if (priceSort != null && priceSort.equals("asc")) {
            sort = Sort.by(Sort.Direction.ASC, "price");
        } else if (priceSort != null && priceSort.equals("desc")) {
            sort = Sort.by(Sort.Direction.DESC, "price");
        }
            List<Product> products =
            productRepository.findByCustomQuery(id,type,name, productTypeId, productCode, sort);
            return products;
        }

    

    public Product add(Product product) {
        product.setProductTypes(productTypeRepository.findById(product.getProductTypeId()).get());
        return productRepository.save(product);
    }

    public Product edit(Product newProduct) {
        Product oldProduct = productRepository.findById(newProduct.getId()).get();
        newProduct.setId(null);
        Product editedProduct = add(newProduct);
        if (editedProduct != null) {
            oldProduct.setIsDeleted(true);
            productRepository.save(oldProduct);
        }
        return editedProduct;
    }

    public boolean delete(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product==null){
            return false;
        }else{
            product.setIsDeleted(true);
            productRepository.save(product);
            return true;
        }      
    }
}
