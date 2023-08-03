package com.sitesquad.ministore.controller.shift;

import com.sitesquad.ministore.constant.RoleConstant;
import com.sitesquad.ministore.constant.TicketTypeConstant;
import com.sitesquad.ministore.dto.RequestMeta;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.model.Ticket;
import com.sitesquad.ministore.model.UserShift;
import com.sitesquad.ministore.repository.TicketRepository;
import com.sitesquad.ministore.service.UserNotificationService;
import com.sitesquad.ministore.service.shift.TicketService;
import com.sitesquad.ministore.service.shift.UserShiftService;
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
    UserShiftService userShiftService;

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
            List<Map<String,Object >> unprocessedTicketsRes = new ArrayList<>();
            List<Map<String,Object >> processedTicketsRes = new ArrayList<>();

            for(Ticket ticket : unprocessedTickets){
                Map<String,Object >unprocessedTicketsMap = new HashMap<>();
                unprocessedTicketsMap.put("ticket",ticket);
                unprocessedTicketsMap.put("userShift",userShiftService.findById(ticket.getUserShiftId()));
                unprocessedTicketsRes.add(unprocessedTicketsMap);
            }
            for(Ticket ticket : processedTickets){
                Map<String,Object >processedTicketsMap = new HashMap<>();
                processedTicketsMap.put("ticket",ticket);
                processedTicketsMap.put("userShift",userShiftService.findById(ticket.getUserShiftId()));
                processedTicketsRes.add(processedTicketsMap);

            }
            Map<String,List<Map<String,Object >> > data = new HashMap<>();
            data.put("unprocessedTickets",unprocessedTicketsRes);
            data.put("processedTickets",processedTicketsRes);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200,"Ticket list found for "+requestMeta.getRole(),data));
        } else{
            List<Ticket> unprocessedTickets = ticketRepository.findByUserIdAndIsApprovedNull(requestMeta.getUserId());
            List<Ticket> processedTickets = ticketRepository.findByUserIdAndIsApprovedNotNull(requestMeta.getUserId());
            List<Map<String,Object >> unprocessedTicketsRes = new ArrayList<>();
            List<Map<String,Object >> processedTicketsRes = new ArrayList<>();

            for(Ticket ticket : unprocessedTickets){
                Map<String,Object >unprocessedTicketsMap = new HashMap<>();
                unprocessedTicketsMap.put("ticket",ticket);
                unprocessedTicketsMap.put("userShift",userShiftService.findById(ticket.getUserShiftId()));
                unprocessedTicketsRes.add(unprocessedTicketsMap);
            }
            Map<String,Object >processedTicketsMap = new HashMap<>();
            for(Ticket ticket : processedTickets){
                processedTicketsMap.put("ticket",ticket);
                processedTicketsMap.put("userShift",userShiftService.findById(ticket.getUserShiftId()));
                processedTicketsRes.add(processedTicketsMap);

            }
            Map<String,List<Map<String,Object >> > data = new HashMap<>();
            data.put("unprocessedTickets",unprocessedTicketsRes);
            data.put("processedTickets",processedTicketsRes);

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
            Map<String,Object> data = new HashMap<>();
            data.put("ticket",ticket);
            data.put("userShift",userShiftService.findById(ticket.getUserShiftId()));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200, "Ticket detail found !", data));
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
            Map<String,Object> data = new HashMap<>();
            data.put("ticket",ticket);
            data.put("userShift",userShiftService.findById(ticket.getUserShiftId()));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(200, "Ticket detail found !", data));
        }
    }

    @PostMapping("/ticket")
    public ResponseEntity<ResponseObject> add(@RequestBody(required = false) Ticket ticket){
        if(requestMeta.getUserId()==null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500, "user id not found", ""));
        }
        if(ticket == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500, "ticket parameter not found", ""));
        }
        if(ticket.getTitle()==null||ticket.getTicketTypeId()==null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500, "Ticket title or type aren't inputted", ""));
        }
        if(ticket.getTicketTypeId() == TicketTypeConstant.LEAVE_TICKET_TYPE&&(ticket.getStartTime()==null||ticket.getEndTime()==null)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(500, "Leave Ticket start/end time or type aren't inputted", ""));

        }
        //check không trùng time
        if(ticket.getTicketTypeId() == TicketTypeConstant.LEAVE_TICKET_TYPE&&ticket.getStartTime()!=null&&ticket.getEndTime()!=null) {
            ticket.setStartTime(ticket.getStartTime().withHour(0).withMinute(0).withSecond(0));
            ticket.setEndTime(ticket.getEndTime().withHour(23).withMinute(59).withSecond(0));
            List<Ticket> oldTickets = ticketRepository.findByUserId(requestMeta.getUserId());
            for (Ticket oldTicket : oldTickets) {
                if (      ( (ticket.getStartTime().isEqual(oldTicket.getStartTime())||ticket.getStartTime().isAfter(oldTicket.getStartTime()))
                        && (ticket.getStartTime().isEqual(oldTicket.getEndTime())||ticket.getStartTime().isBefore(oldTicket.getEndTime())))
                        ||( (ticket.getEndTime().isEqual(oldTicket.getStartTime())||ticket.getStartTime().isAfter(oldTicket.getStartTime()))
                        && (ticket.getEndTime().isEqual(oldTicket.getStartTime())||ticket.getStartTime().isBefore(oldTicket.getStartTime())))
                ) {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseObject(500, "Leave period is duplicated! Please change!", oldTicket));
                }
            }
        }
        if(ticket.getTicketTypeId() == TicketTypeConstant.CANCLE_SHIFT_TICKET_TYPE){
            UserShift cancleShift = userShiftService.findById(ticket.getUserShiftId());
            if(ticket.getUserShiftId()==null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(500, "Cancle-Shift Ticket id wasn't recieved", ""));
            }else if(cancleShift.getUserId()==null||!cancleShift.getUserId().equals(requestMeta.getUserId())){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(500, "You can not cancle the shift NOT ASSIGNED to you!", ""));
            }
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

        if(ticket.getIsApproved() == true && ticket.getTicketTypeId() == TicketTypeConstant.LEAVE_TICKET_TYPE){
            System.out.println(ticket);
            ticketService.generateShiftRequestByTicket(ticket);
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
        } else if(ticket.getIsApproved() == true && ticket.getTicketTypeId() == TicketTypeConstant.CANCLE_SHIFT_TICKET_TYPE){
            System.out.println(ticket);
            UserShift cancleShift = userShiftService.findById(ticket.getUserShiftId());
            userShiftService.removeUserFromShift(cancleShift);
            userNotificationService.
                    customCreateUserNotification("Your ticket has been approved!"
                            ,"Your ticket: \"" + ticket.getTitle() +"\" has been approved by admin"
                                    +"You have been removed from your shift: "
                                    + cancleShift.getShift().getType()
                                    + " \n From " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                    .format(cancleShift.getStartTime())
                                    + " to " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                    .format(cancleShift.getEndTime())
                                    + "\nIf you have any problem please contact admin!"
                            ,sendIds
                    );
        }

        String status = isApproved?"Approved":"Rejected";
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(200,"Ticket "+status+" successfully",ticket));
    }


}
