package com.sitesquad.ministore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserNotificationService {

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;



}
