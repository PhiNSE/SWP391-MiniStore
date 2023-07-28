package com.sitesquad.ministore.model;

import com.sitesquad.ministore.utils.ZonedDateTimeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ticket_id")
    private Long ticketId;

    @Column(name = "user_id", nullable = true, insertable = false, updatable = false)
    private Long userId;

    @ToString.Exclude
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "start_time")
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime endTime;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "title",columnDefinition = "nvarchar(255)")
    private String title;

    @Column(name = "description",columnDefinition = "nvarchar(255)")
    private String description;

    @Column(name = "ticket_type_id", insertable = false, updatable = false)
    private Long ticketTypeId;

    @ToString.Exclude
    @ManyToOne(targetEntity = TicketType.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_type_id", referencedColumnName = "ticket_type_id")
    private TicketType TicketType;

    @Column(name = "user_shift_id")
    private Long userShiftId;


}
