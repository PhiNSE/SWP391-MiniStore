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
    List<Order> findByIsDeletedNullOrIsDeletedFalse();

    @Query(value = "SELECT DATEPART(hour, date) AS 'hour_in_day', COUNT(order_id) AS 'number_of_order'\n" +
            "FROM tbl_order\n" +
            "WHERE type = 0 AND tbl_order.is_deleted IS NULL OR tbl_order.is_deleted = 0\n" +
            "GROUP BY DATEPART(hour, date)\n" +
            "ORDER BY number_of_order DESC", nativeQuery = true)
    List<Map<String, Object>> findMostSoldHour();

    @Query(value = "select user_id, sum(total) as 'total_money'\n" +
            "from tbl_order\n" +
            "where type = 0 and tbl_order.is_deleted is null or tbl_order.is_deleted = 0\n" +
            "group by user_id\n" +
            "order by sum(total) desc", nativeQuery = true)
    List<Map<String, Object>> findByUserRank();

//    List<Order> findByUserIdAndName(Long userId, String name);
}
