package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.Voucher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long>, JpaSpecificationExecutor<Voucher>{
    List<Voucher> searchByDescriptionContainingIgnoreCaseOrNameContainingIgnoreCase(String description, String name);
    List<Voucher> findByIsDeletedFalseOrIsDeletedIsNull();
}
