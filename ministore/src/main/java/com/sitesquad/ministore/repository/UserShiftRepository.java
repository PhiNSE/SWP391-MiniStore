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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ACER
 */
@Repository
public interface UserShiftRepository extends JpaRepository<UserShift, Long>, JpaSpecificationExecutor<UserShift> {
    public UserShift findByUserShiftId(Long userShiftId);
    public UserShift findTop1ByOrderByEndTimeDesc();
    public List<UserShift> findByIsPaidFalseOrIsPaidNull();
    public List<UserShift> findByUserId(Long id);

    @Query("SELECT us FROM UserShift us WHERE YEAR(us.startTime) = :year " +
            "AND MONTH(us.startTime) = :month " +
            "AND DAY(us.startTime) = :day " +
            "AND DATEPART(HOUR, us.startTime) = :hour " +
            "AND DATEPART(MINUTE, us.startTime) = :minute")
    List<UserShift> findUserShiftsByStartTime(@Param("year") int year, @Param("month") int month, @Param("day") int day, @Param("hour") int hour, @Param("minute") int minute);


}
