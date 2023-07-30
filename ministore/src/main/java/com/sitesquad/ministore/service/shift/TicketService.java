package com.sitesquad.ministore.service.shift;

import com.sitesquad.ministore.constant.ShiftConstant;
import com.sitesquad.ministore.model.ShiftRequest;
import com.sitesquad.ministore.model.Ticket;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.model.UserShift;
import com.sitesquad.ministore.repository.TicketRepository;
import com.sitesquad.ministore.repository.TicketTypeRepository;
import com.sitesquad.ministore.repository.UserRepository;
import com.sitesquad.ministore.repository.UserShiftRepository;
import com.sitesquad.ministore.utils.UserShiftUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private UserShiftRepository userShiftRepository;

    @Autowired
    private UserShiftService userShiftService;

    @Autowired
    private ShiftRequestService shiftRequestService;

    public List<Ticket> getAll(){
        return ticketRepository.findAll();
    }

    public Ticket getByTicketId(Long ticketId){
        return ticketRepository.findById(ticketId).orElse(null);
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
            ticket.setUser(userRepository.findByUserIdAndIsDeletedFalse(ticket.getUserId()));
            ticket.setTicketType(ticketTypeRepository.findById(ticket.getTicketTypeId()).get());
            return ticketRepository.save(ticket);
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
        User user = userRepository.findById(ticket.getUserId()).orElse(null);
        ZonedDateTime start = ticket.getStartTime().withHour(0).plusMinutes(0).withSecond(0).withNano(0);
        ZonedDateTime end = ticket.getEndTime().withHour(23).plusMinutes(0).withSecond(0).withNano(0);
        //doing
        List<UserShift> userShifts = userShiftRepository.findByAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(start.minusDays(1),end.plusDays(2));
        System.out.println(userShifts);
        System.out.println("Generating leave requests for user "+ticket.getUserId());
        for (UserShift userShift: userShifts) {
            if((userShift.getStartTime().isAfter(start))&&(userShift.getStartTime().isBefore(end))){
                if(UserShiftUtil.compareUserShiftRole(userShift,ticket.getUser().getRole().getName())){
                ShiftRequest shiftRequest = new ShiftRequest();
                shiftRequest.setUserShiftId(userShift.getUserShiftId());
                shiftRequest.setUserId(ticket.getUserId());
                shiftRequest.setType(ShiftConstant.SHIFT_LEAVE_TYPE);
                System.out.println(shiftRequest);
                shiftRequestService.add(shiftRequest);
                //remove people from old shift
                    if(ticket.getUserId() != null && userShift.getUserId()!=null && ticket.getUserId() == userShift.getUserId()){
                        userShiftService.removeUserFromShift(userShift);
                    }

                }
            }
        }
        return true;
    }
}
