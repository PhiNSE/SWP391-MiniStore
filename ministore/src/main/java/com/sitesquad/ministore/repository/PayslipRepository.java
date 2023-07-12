package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.Payslip;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface PayslipRepository extends JpaRepository<Payslip, Long>, JpaSpecificationExecutor<Payslip>{
    List<Payslip> findByUserId(Long userId);
    List<Payslip> findByIsPaidNullOrIsPaidFalse();
}
