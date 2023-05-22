package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    List<Order> findByUserId(Long userId);

//    List<Order> findByUserIdAndName(Long userId, String name);
}
