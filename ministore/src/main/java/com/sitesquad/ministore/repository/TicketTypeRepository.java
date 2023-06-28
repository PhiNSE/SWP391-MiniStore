package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long>, JpaSpecificationExecutor<TicketType> {

}
