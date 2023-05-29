package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ADMIN
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, PagingAndSortingRepository<Product, Long>{

    Page<Product> findByIsDeletedIsNull(Pageable pageable);

    List<Product> findByProductTypeId(Long productTypeId);

    List<Product> findByIdOrNameContainingIgnoreCaseAndProductTypes_NameContainingIgnoreCaseAndProductTypeIdAndProductCodeAndIsDeletedIsNull(
            Long id, String name,String productTypeName, Long productTypeId, String productCode, Sort sort);
  
}
