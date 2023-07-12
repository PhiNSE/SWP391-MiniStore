package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.ShiftRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ShiftRequestRepository extends JpaRepository<ShiftRequest, Long>, JpaSpecificationExecutor<ShiftRequest> {
    public List<ShiftRequest> findByUserShiftId(Long userShiftId);
    public List<ShiftRequest> findByUserId(Long userId);
    public List<ShiftRequest> findByType(Boolean type);
}
