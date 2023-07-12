package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.OrderDetails;
import java.util.List;
import java.util.Map;

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

    @Query(value = "select product_id, sum(quantity) as 'sum_quantity'\n" +
            "            from order_detail join tbl_order on order_detail.order_id = tbl_order.order_id\n" +
            "            where tbl_order.type = 0\n" +
            "            group by product_id\n" +
            "            order by sum_quantity desc", nativeQuery = true)
    List<Map<String, Object>> findByCustom();
}
