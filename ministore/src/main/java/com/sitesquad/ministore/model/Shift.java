package com.sitesquad.ministore.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "shift")
public class Shift implements Serializable {
    @Id
    @Column(name = "shift_id")
    Long id;
    
    @Column(name = "start_work_hour")
    private Date startWorkHour;
    
    @Column(name = "end_work_hour")
    private Date endWorkHour;
    
    @Column(name = "coefficient")
    private int coefficient;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Shift shift = (Shift) o;
        return Objects.equals(id, shift.id);
    }

    //hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
