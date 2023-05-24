package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.ProductType;
import com.sitesquad.ministore.repository.ProductTypeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    public List<ProductType> findAll() {
        List<ProductType> productTypes = productTypeRepository.findAll();
        return productTypes;
    }

    public ProductType findById(Long id) {
        Optional<ProductType> foundProductType = productTypeRepository.findById(id);
        return foundProductType.get();
    }

    public ProductType add(ProductType productType) {
        return productTypeRepository.save(productType);
    }

    public ProductType edit(ProductType newProductType) {
        ProductType editedProductType = add(newProductType);
        return editedProductType;
    }

    public boolean delete(Long id) {
        productTypeRepository.deleteById(id);
        return !productTypeRepository.findById(id).isPresent();
    }
}
