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
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, PagingAndSortingRepository<Product, Long>{

    List<Product> findByIsDeletedFalseOrIsDeletedIsNull();
    
    Page<Product> findByIsDeletedFalseOrIsDeletedIsNull(Pageable pageable);

    List<Product> findByProductTypeId(Long productTypeId);

//    List<Product> findProductByIdOrNameContainingIgnoreCaseOrProductTypes_NameContainingIgnoreCaseOrProductTypeIdOrProductCodeAndIsDeletedFalse(
//            Long id, String name, String productTypeName, Long productTypeId, String productCode, Sort sort);

//    @Query(value = "SELECT p FROM Product p WHERE (:productId IS NULL OR p.id = :productId) " +
//           "AND (:name IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(:name), '%')) " +
//           "AND (:productTypeName IS NULL OR LOWER(p.productTypes.name) LIKE CONCAT('%', LOWER(:productTypeName), '%')) " +
//           "AND (:productTypeId IS NULL OR p.productTypeId = :productTypeId) " +
//           "AND (:productCode IS NULL OR p.productCode = :productCode) " +
//           "AND (p.isDeleted = false OR p.isDeleted IS NULL)", nativeQuery = true)
    @Query(value="SELECT p.product_id, p.name, p.quantity, p.product_type_id, p.price, p.cost, p.product_img,p.product_code, pt.name, p.is_deleted FROM product p " +
            "JOIN product_type pt on p.product_type_id = pt.product_type_id " +
            "WHERE p.name like %:keyword% or p.product_id like %:keyword% or " +
            "p.quantity like %:keyword% or p.price like %:keyword% or " +
            "pt.name like %:keyword% or " +
            "p.product_code like %:keyword% and p.name like %:keyword% ",
            nativeQuery = true)
    Page<Product> findByCustomQuery(@Param("keyword") String keyword , Pageable pageable );

//    Page<Product> findByIsDeletedIsNull(Pageable pageable);
//
//
    Page<Product> findByProductIdOrNameContainingIgnoreCaseOrProductTypes_NameContainingIgnoreCaseOrProductTypeIdOrProductCodeAndIsDeletedIsNull(
            Long productId, String name,String productTypeName, Long productTypeId, String productCode,Pageable pageable);

  
}



