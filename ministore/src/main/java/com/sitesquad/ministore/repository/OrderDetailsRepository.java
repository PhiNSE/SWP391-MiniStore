package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.OrderDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long>, JpaSpecificationExecutor<OrderDetails> {

    List<OrderDetails> findByOrderId(Long orderId);

    List<OrderDetails> findByProductId(Long productId);

    List<OrderDetails> findByProductVoucherId(Long productVoucherId);
}
