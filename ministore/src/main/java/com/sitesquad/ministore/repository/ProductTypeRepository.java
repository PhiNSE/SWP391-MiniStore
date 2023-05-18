package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ADMIN
 */
public interface ProductTypeRepository extends JpaRepository<ProductType, Long>{
    
}
