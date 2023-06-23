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

    @Column(name="shift_request_id")
    private Long 

}
