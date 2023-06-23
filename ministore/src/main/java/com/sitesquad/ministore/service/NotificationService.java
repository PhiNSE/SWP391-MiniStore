package com.sitesquad.ministore.service;


import com.sitesquad.ministore.model.Notification;
import com.sitesquad.ministore.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    public List<Notification> findAll() {
        List<Notification> notificationList = notificationRepository.findAll();
        return notificationList;
    }

    public Notification findById(Long id) {
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        return notificationOptional.get();
    }

    public Notification add (Notification notification){
        return notificationRepository.save(notification);
    }

    public void delete (Notification notification){
        notificationRepository.delete(notification);
    }

    public Notification edit (Notification notification){
        return notificationRepository.save(notification);
    }


}
