/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.UserShift;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ACER
 */
@Repository
public interface UserShiftRepository extends JpaRepository<UserShift, Long>, JpaSpecificationExecutor<UserShift> {
    public UserShift findByUserShiftId(Long userShiftId);
    public UserShift findTop1ByOrderByEndTimeDesc();
    public List<UserShift> findByIsPaidFalseOrIsPaidNullAndUserId(Long id);
   
}
