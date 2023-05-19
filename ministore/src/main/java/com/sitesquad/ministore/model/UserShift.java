package com.sitesquad.ministore.model;

import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author ADMIN
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "user_shift")
public class UserShift {
    @Id
    @Column(name = "user_shift_id")
    Long id;
    
    @ToString.Exclude
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Long userId;

    @ToString.Exclude
    @ManyToOne(targetEntity = Shift.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "shift_id", referencedColumnName = "shift_id")
    private Long shiftId;
    
    @Column(name = "work_date")
    private Date workDate;

    @Column(name = "is_holiday")
    private boolean isHoliday;
    
    @Column(name = "is_weekend")
    private boolean isWeekend;

    @Column(name = "is_present")
    private boolean isPresent;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserShift userShift = (UserShift) o;
        return Objects.equals(id, userShift.id);
    }

    //hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
