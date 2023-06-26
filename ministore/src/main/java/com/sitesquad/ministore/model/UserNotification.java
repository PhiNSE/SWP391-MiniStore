package com.sitesquad.ministore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = true)
    @ToString.Exclude
    @JsonIgnore
    private User userNotifications;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

}
