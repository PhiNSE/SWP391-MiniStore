package com.sitesquad.ministore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "user_notification")
public class UserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_notification_id")
    private Long userNotificationId;

    @Column(name = "user_id", insertable=false, updatable=false)
    private Long userId;

    @ManyToOne(targetEntity = User.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = true)
    @ToString.Exclude
    @JsonIgnore
    private User user;

    @Column(name = "title", columnDefinition = "nvarchar(255)")
    private String title;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "description", columnDefinition = "nvarchar(max)")
    private String description;

}
