package com.sitesquad.ministore.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.*;

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
@Table(name = "holiday")
public class Holiday implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holiday_id")
    private Long holidayId;
    
    @Column(name = "holiday_date")
    private LocalDate date;
    
    @Column(name = "holiday_name")
    private String name;
    
}
