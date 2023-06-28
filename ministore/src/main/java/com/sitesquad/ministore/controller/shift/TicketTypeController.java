package com.sitesquad.ministore.controller.shift;

import com.sitesquad.ministore.dto.ResponseObject;

import com.sitesquad.ministore.repository.TicketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketTypeController {
    @Autowired
    TicketTypeRepository ticketTypeRepository;

    @GetMapping("/ticketType")
    public ResponseEntity<ResponseObject> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200, "Ticket type list found", ticketTypeRepository.findAll()));

    }
}