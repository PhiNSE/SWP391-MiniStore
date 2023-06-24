package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.Order;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    List<Order> findByUserId(Long userId);
    List<Order> findByTypeFalse();
    List<Order> findByTypeTrue();

    @Query(value = "SELECT DATEPART(hour, date) AS 'hour_in_day', COUNT(order_id) AS 'number_of_order' \n" +
            "FROM tbl_order \n" +
            "WHERE type = 0\n" +
            "GROUP BY DATEPART(hour, date)\n" +
            "ORDER BY number_of_order DESC", nativeQuery = true)
    List<Map<String, Object>> findByCustom();

//    List<Order> findByUserIdAndName(Long userId, String name);
}
