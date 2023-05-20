package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long>, JpaSpecificationExecutor<OrderDetails> {
    
}
