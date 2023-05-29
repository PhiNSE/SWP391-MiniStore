package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.Product;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ADMIN
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByIsDeletedIsNull();

    List<Product> findByProductTypeId(Long productTypeId);

    List<Product> findByProductIdOrNameContainingIgnoreCaseOrProductTypes_NameContainingIgnoreCaseOrProductTypeIdOrProductCodeAndIsDeletedIsNull(
            Long productId, String name,String productTypeName, Long productTypeId, String productCode, Sort sort);
  
}
