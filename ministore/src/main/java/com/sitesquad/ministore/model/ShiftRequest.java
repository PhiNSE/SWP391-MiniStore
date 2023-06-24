package com.sitesquad.ministore.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "shift_request")
public class ShiftRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="shift_request_id")
    private Long shiftRequestId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ToString.Exclude
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "user_shift_id", insertable = false, updatable = false)
    private Long userShiftId;

    @ToString.Exclude
    @ManyToOne(targetEntity = UserShift.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_shift_id", referencedColumnName = "user_shift_id")
    private UserShift userShift;

    @Column(name = "type")
    private Boolean type;

}
