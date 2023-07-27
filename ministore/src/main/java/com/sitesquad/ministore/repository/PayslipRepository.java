package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.Payslip;
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
public interface PayslipRepository extends JpaRepository<Payslip, Long>, JpaSpecificationExecutor<Payslip>{
    List<Payslip> findByUserId(Long userId);
    List<Payslip> findByIsPaidNullOrIsPaidFalse();
    @Query(value = "SELECT \n" +
            "    tbl_user.user_id,\n" +
            "    SUM(shift_count) AS 'sum_shift_count', \n" +
            "    SUM(salary) AS 'sum_salary', \n" +
            "    SUM(total_hours) AS 'sum_total_hours'\n" +
            "FROM \n" +
            "    tbl_user\n" +
            "LEFT JOIN \n" +
            "    payslip ON payslip.user_id = tbl_user.user_id AND (is_paid = 0 OR is_paid IS NULL)\n" +
            "GROUP BY \n" +
            "    tbl_user.user_id;", nativeQuery = true)
    List<Map<String, Object>> findByCustom();
}
