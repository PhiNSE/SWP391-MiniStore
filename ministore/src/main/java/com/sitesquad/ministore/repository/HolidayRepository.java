package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.Holiday;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
/**
 *
 * @author ADMIN
 */
public interface HolidayRepository extends JpaRepository<Holiday, Long>{
    public Holiday findByDate(LocalDate date);
}
