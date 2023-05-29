package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.Product;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ADMIN
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByIsDeletedFalseOrIsDeletedIsNull();

    List<Product> findByProductTypeId(Long productTypeId);

    List<Product> findByIdOrNameContainingIgnoreCaseOrProductTypes_NameContainingIgnoreCaseOrProductTypeIdOrProductCodeAndIsDeletedFalse(
            Long id, String name, String productTypeName, Long productTypeId, String productCode, Sort sort);

    @Query("SELECT p FROM Product p WHERE (:id IS NULL OR p.id = :id) " +
           "AND (:name IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(:name), '%')) " +
           "AND (:productTypeName IS NULL OR LOWER(p.productTypes.name) LIKE CONCAT('%', LOWER(:productTypeName), '%')) " +
           "AND (:productTypeId IS NULL OR p.productTypeId = :productTypeId) " +
           "AND (:productCode IS NULL OR p.productCode = :productCode) " +
           "AND (p.isDeleted = false OR p.isDeleted IS NULL)")
    List<Product> findByCustomQuery(@Param("id") Long id, @Param("name") String name,
                                    @Param("productTypeName") String productTypeName, @Param("productTypeId") Long productTypeId,
                                    @Param("productCode") String productCode, Sort sort);
}



