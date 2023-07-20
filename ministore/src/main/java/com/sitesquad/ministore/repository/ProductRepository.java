package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ADMIN
 */
@Repository

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, PagingAndSortingRepository<Product, Long> {

    List<Product> findByIsDeletedFalseOrIsDeletedIsNull();

    Page<Product> findByIsDeletedFalseOrIsDeletedIsNull(Pageable pageable);

    List<Product> findByProductTypeId(Long productTypeId);

//    Page<Product> findProductByProductIdOrNameContainingIgnoreCaseOrProductType_NameContainingIgnoreCaseAndProductTypeIdAndProductCodeAndIsDeletedFalse(
//            Long productId, String name, String productTypeName, Long productTypeId, String productCode,Pageable pageable);
    Page<Product> findProductByProductIdOrNameContainingIgnoreCaseOrProductType_NameContainingIgnoreCaseAndProductTypeIdAndProductCodeAndIsDeletedFalse(
            Long productId, String name, String productTypeName, Long productTypeId, String productCode, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.productType pt "
            + "WHERE (:productId IS NULL OR p.id = :productId) "
            + "AND ((:name IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(:name), '%')) "
            + "OR (:productTypeName IS NULL OR LOWER(pt.name) LIKE CONCAT('%', LOWER(:productTypeName), '%'))) "
            + "AND (:productTypeId IS NULL OR p.productTypeId = :productTypeId) "
            + "OR (:productCode IS NULL OR p.productCode = :productCode) "
            + "AND (p.isDeleted = false OR p.isDeleted IS NULL)")
    Page<Product> findByCustomQuery(@Param("productId") Long id, @Param("name") String name,
            @Param("productTypeName") String productTypeName, @Param("productTypeId") Long productTypeId,
            @Param("productCode") String productCode, Pageable pageable);
 
//    Page<Product> findByIsDeletedIsNull(Pageable pageable);
//
//
  
}
