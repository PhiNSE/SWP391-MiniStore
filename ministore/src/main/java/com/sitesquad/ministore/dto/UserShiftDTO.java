package com.sitesquad.ministore.dto;

import com.sitesquad.ministore.model.Shift;
import com.sitesquad.ministore.model.User;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ADMIN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserShiftDTO {
    Long userShiftId;

    private Long userId;

    private User user;

    private Long shiftId;

    private Shift shift;
    
    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

    private Boolean isHoliday;

    private Boolean isWeekend;

    private Boolean isCheckedIn;

    private Boolean isCheckedOut;

    private ZonedDateTime checkInTime;

    private ZonedDateTime checkOutTime;

    private Boolean isCheckedInLate;

    private Boolean isCheckedOutLate;

    private String status;

    private Boolean isPaid;
    
    private List<User> availableEmployees;

    private List<User> requestEmployees;

    private Set<User> leaveEmployees;
 
}
