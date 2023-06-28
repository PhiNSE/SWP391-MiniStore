package com.sitesquad.ministore.controller.shift;

import com.sitesquad.ministore.constant.RoleConstant;
import com.sitesquad.ministore.dto.RequestMeta;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.model.Ticket;
import com.sitesquad.ministore.repository.TicketRepository;
import com.sitesquad.ministore.service.shift.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TicketController {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketService ticketService;

    @Autowired
    RequestMeta requestMeta;

    @GetMapping("/ticket")
    public ResponseEntity<ResponseObject> getAll(){
        if(requestMeta.getRole() == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"User role not found",""));
        }
        if(requestMeta.getRole() == RoleConstant.ADMIN_ROLE_NAME){
            List<Ticket> tickets = ticketService.getAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"Ticket list found for "+requestMeta.getRole(),tickets));
        } else{
            List<Ticket> tickets = ticketRepository.findByUserId(requestMeta.getUserId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"Ticket list found for "+requestMeta.getRole(),tickets));
        }
    }

    @PostMapping("/ticket")
    public ResponseEntity<ResponseObject> add(@RequestBody Ticket ticket){
        Ticket ticketAdd = ticketService.add(ticket);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(200,"Submit ticket successfully, WAIT FOR ADMIN TO APPROVE!",ticketAdd));

    }

    @PutMapping("/ticket")
    public ResponseEntity<ResponseObject> edit(@RequestBody Ticket ticket){
        if(ticket.getTicketId() == null || ticketRepository.findById(ticket.getTicketId()) == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"Ticket parameter or in database not found",""));
        }
        if(ticketRepository.findById(ticket.getTicketId()).get().getIsApproved() != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"Cant edit Ticket because admin approved or rejected",""));
        }
        Ticket ticketEdit = ticketService.edit(ticket);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(200,"Edit ticket successfully, WAIT FOR ADMIN TO APPROVE!",ticketEdit));

    }

    @DeleteMapping("/ticket")
    public ResponseEntity<ResponseObject> delete(@RequestParam Long ticketId){
        if(ticketId == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"ticketId parameter not found",""));
        }
        ticketService.delete(ticketId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(200,"Delete ticket successfully",""));

    }

    @GetMapping("/ticket/approve")
    public ResponseEntity<ResponseObject> approve(@RequestParam Long ticketId, @RequestParam Boolean isApproved){
        if(ticketId==null || isApproved==null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"ticketId,isApproved parameter not found",""));
        }
        if(requestMeta.getRole() == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"User role not found",""));
        }
        if(requestMeta.getRole() != RoleConstant.ADMIN_ROLE_NAME) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200, "Only admin can approve/reject ticket" + requestMeta.getRole(), ""));
        }
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if(ticket == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"Ticket not found",""));
        }
        ticket.setIsApproved(isApproved);
        ticket = ticketService.edit(ticket);
        if(isApproved==true){
            ticketService.generateShiftRequestByTicket(ticket);
        }
        String status = isApproved?"Approved":"Rejected";
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(200,"Ticket "+status+" successfully",ticket));
    }


}
