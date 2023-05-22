package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.ProductVoucher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface ProductVoucherRepository extends JpaRepository<ProductVoucher, Long>, JpaSpecificationExecutor<ProductVoucher> {

}
