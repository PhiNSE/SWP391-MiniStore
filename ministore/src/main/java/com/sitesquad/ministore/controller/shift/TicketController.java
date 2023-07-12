package com.sitesquad.ministore.controller.shift;

import com.sitesquad.ministore.constant.RoleConstant;
import com.sitesquad.ministore.dto.RequestMeta;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.model.Ticket;
import com.sitesquad.ministore.repository.TicketRepository;
import com.sitesquad.ministore.service.UserNotificationService;
import com.sitesquad.ministore.service.shift.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TicketController {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketService ticketService;

    @Autowired
    UserNotificationService userNotificationService;

    @Autowired
    RequestMeta requestMeta;

    @GetMapping("/ticket")
    public ResponseEntity<ResponseObject> getAll(){
        System.out.println(requestMeta);
        if(requestMeta.getRole() == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500,"User role not found",""));
        }
        if(requestMeta.getRole().equals(RoleConstant.ADMIN_ROLE_NAME)){
            List<Ticket> unprocessedTickets = ticketRepository.findByIsApprovedNull();
            List<Ticket> processedTickets = ticketRepository.findByIsApprovedNotNull();
            Map<String,List<Ticket> > data = new HashMap<>();
            data.put("unprocessedTickets",unprocessedTickets);
            data.put("processedTickets",processedTickets);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"Ticket list found for "+requestMeta.getRole(),data));
        } else{
            List<Ticket> unprocessedTickets = ticketRepository.findByUserIdAndIsApprovedNull(requestMeta.getUserId());
            List<Ticket> processedTickets = ticketRepository.findByUserIdAndIsApprovedNotNull(requestMeta.getUserId());
            Map<String,List<Ticket> > data = new HashMap<>();
            data.put("unprocessedTickets",unprocessedTickets);
            data.put("processedTickets",processedTickets);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"Ticket list found for "+requestMeta.getRole(),data));
        }
    }

    @GetMapping("/ticket/{id}")
    public ResponseEntity<ResponseObject> getDetail(@PathVariable(required = false) Long id) {
        if (requestMeta.getRole() == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500, "User role not found", ""));
        }
        if (id == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500, "id parameter not found", ""));
        }
        if (requestMeta.getRole().equals(RoleConstant.ADMIN_ROLE_NAME)) {
            Ticket ticket = ticketRepository.findById(id).orElse(null);
            if(ticket == null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(500, "There's no such ticket", ""));
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200, "Ticket detail found ", ticket));
        }else{
            Ticket ticket = ticketRepository.findById(id).orElse(null);
            if(ticket == null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(500, "Ticket detail found ", ""));
            }
            if(!ticket.getUserId().equals(requestMeta.getUserId())){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(500, "You cant view ticket detail of other user", ""));
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200, "Ticket detail found ", ticket));
        }
    }

    @PostMapping("/ticket")
    public ResponseEntity<ResponseObject> add(@RequestBody(required = false) Ticket ticket){
        if(ticket == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500, "ticket parameter not found", ""));
        }
        if(ticket.getTitle()==null||ticket.getTicketTypeId()==null||ticket.getStartTime()==null||ticket.getEndTime()==null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500, "some fields of the ticket aren't inputted", ""));
        }
        if(requestMeta.getUserId()==null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500, "user id not found", ""));
        }
        ticket.setUserId(requestMeta.getUserId());
        Ticket ticketAdd = ticketService.add(ticket);
        //noti
        userNotificationService.sendNotiAndMailToAllAdmins("New ticket is waiting to be processed!"
                ,"Employee: "+ ticket.getUser().getName() + " has submitted a new ticket: "+ticket.getTitle());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(200,"Submit ticket successfully, WAIT FOR ADMIN TO APPROVE!",ticketAdd));

    }

    @PutMapping("/ticket")
    public ResponseEntity<ResponseObject> edit(@RequestBody Ticket ticket){
        if(ticket == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500, "ticket parameter not found", ""));
        }
        if(ticket.getTicketId() == null || ticketRepository.findById(ticket.getTicketId()) == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500,"Ticket id is missing or not in database ",""));
        }
        if(ticket.getTitle()==null||ticket.getTicketTypeId()==null||ticket.getStartTime()==null||ticket.getEndTime()==null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500, "some fields of the ticket aren't inputted", ""));
        }
        if(ticketRepository.findById(ticket.getTicketId()).get().getIsApproved() != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500,"Cant edit Ticket because admin approved or rejected",""));
        }
        if(!ticketRepository.findById(ticket.getTicketId()).get().getUserId().equals(requestMeta.getUserId())){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500,"you cant edit other user's ticket",""));
        }
        ticket.setUserId(requestMeta.getUserId());
        Ticket ticketEdit = ticketService.edit(ticket);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(200,"Edit ticket successfully, WAIT FOR ADMIN TO APPROVE!",ticketEdit));

    }

    @DeleteMapping("/ticket")
    public ResponseEntity<ResponseObject> delete(@RequestParam Long ticketId){
        if(ticketId == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500,"ticketId parameter not found",""));
        }
        Ticket ticket = ticketService.getByTicketId(ticketId);
        if(ticket == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500,"ticket not found in database",""));
        }
        if(!ticket.getUserId().equals(requestMeta.getUserId())){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500,"you cant delete other user's ticket",""));
        }
        ticketService.delete(ticketId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(200,"Delete ticket successfully",""));

    }

    @GetMapping("/ticket/approve")
    public ResponseEntity<ResponseObject> approve(@RequestParam Long ticketId, @RequestParam Boolean isApproved){
        if(ticketId==null || isApproved==null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500,"ticketId,isApproved parameter not found",""));
        }
        if(requestMeta.getRole() == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500,"User role not found",""));
        }
        if(!requestMeta.getRole().equals(RoleConstant.ADMIN_ROLE_NAME)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500, "Only admin can approve/reject ticket " + requestMeta.getRole(), ""));
        }
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if(ticket == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"Ticket not found",""));
        }
        if(ticket.getIsApproved() != null){
            String status = ticket.getIsApproved()==true?"approved":"rejected";
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500,"Ticket was "+ status + " already!",""));
        }
        ticket.setIsApproved(isApproved);
        ticket = ticketService.edit(ticket);
        //noti
        List<Long> sendIds = new ArrayList<>();
        sendIds.add(ticket.getUserId());
        userNotificationService.
                customCreateUserNotification("Your ticket has been approved!"
                        ,"Your ticket: \"" + ticket.getTitle() +"\" has been approved by admin"
                        +"\n You will on leave from: "
                                + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                .format(ticket.getStartTime())
                                + " to: " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                .format(ticket.getEndTime())
                        ,sendIds
                        );
        if(ticket.getIsApproved() == true){
            System.out.println(ticket);
            ticketService.generateShiftRequestByTicket(ticket);
        }
        String status = isApproved?"Approved":"Rejected";
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(200,"Ticket "+status+" successfully",ticket));
    }


}
