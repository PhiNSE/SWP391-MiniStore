package com.sitesquad.ministore.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private  String description;

    @OneToMany(mappedBy = "userNotification")
    @JsonIgnore
    @ToString.Exclude
    private Collection<UserNotification> userNotifications;
}
