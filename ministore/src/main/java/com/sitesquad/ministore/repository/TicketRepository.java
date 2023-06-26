package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {
    public List<Ticket> findByUserId(Long userId);
}
