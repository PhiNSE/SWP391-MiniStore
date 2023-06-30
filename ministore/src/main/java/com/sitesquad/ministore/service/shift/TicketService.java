package com.sitesquad.ministore.service.shift;

import com.sitesquad.ministore.model.ShiftRequest;
import com.sitesquad.ministore.model.Ticket;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.repository.TicketRepository;
import com.sitesquad.ministore.repository.TicketTypeRepository;
import com.sitesquad.ministore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    public List<Ticket> getAll(){
        return ticketRepository.findAll();
    }

    public Ticket getByTicketId(Long ticketId){
        return ticketRepository.findById(ticketId).get();
    }

    public Ticket add(Ticket ticket){
        ticket.setIsApproved(null);
        ticket.setUser(userRepository.findByUserIdAndIsDeletedFalse(ticket.getUserId()));
        ticket.setTicketType(ticketTypeRepository.findById(ticket.getTicketTypeId()).get());
        return ticketRepository.save(ticket);
    }

    public Ticket edit(Ticket ticket){
        if(ticketRepository.findById(ticket.getTicketId()) == null){
            return null;
        } else{
            return add(ticket);
        }
    }

    public void delete(Long ticketId){
        if(ticketRepository.findById(ticketId) != null){
            return;
        } else{
            ticketRepository.deleteById(ticketId);
        }
    }

    public boolean generateShiftRequestByTicket(Ticket ticket) {
        if(ticket==null||ticket.getStartTime()==null||ticket.getEndTime()==null||ticket.getUserId()==null){
            return false;
        }
        List<ShiftRequest> shiftRequest = new ArrayList<>();
        ZonedDateTime start = ticket.getStartTime();
        ZonedDateTime end = ticket.getEndTime();
        //doing

        return true;

    }

}
