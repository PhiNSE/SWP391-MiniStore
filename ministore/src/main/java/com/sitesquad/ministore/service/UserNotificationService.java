package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.Notification;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.model.UserNotification;
import com.sitesquad.ministore.repository.UserNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserNotificationService {

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @Autowired
    UserNotificationRepository userNotificationRepository;

    public List<UserNotification> findByUserId(Long id){
        return userNotificationRepository.findByUserId(id);
    }

    public UserNotification createUserNotification(String title, List<User> userList){
        Notification notification = notificationService.findByTitle(title);


            return null;
        }
    }


