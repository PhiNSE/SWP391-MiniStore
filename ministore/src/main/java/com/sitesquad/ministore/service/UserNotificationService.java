package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.model.UserNotification;
import com.sitesquad.ministore.repository.UserNotificationRepository;
import com.sitesquad.ministore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserNotificationService {

    @Autowired
    MailerService mailerService;


    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserNotificationRepository userNotificationRepository;

    public List<UserNotification> findByUserId(Long id){
        return userNotificationRepository.findByUserId(id);
    }

//    public UserNotification createUserNotification(String title, User user){
//        Notification notification = notificationService.findByTitle(title);
//        User userCheck = userService.findUserByEmail(user.getEmail());
//            if(userCheck == null){
//                return null;
//            }
//        UserNotification userNotification= new UserNotification();
//            userNotification.
//        }

    public List<UserNotification> createUserNotification(List<UserNotification> userNotificationList){

        for (UserNotification userNotification: userNotificationList) {
            userNotification.setUserNotifications(userRepository.findById(userNotification.getUserId()).get());

            User user = userRepository.findByUserIdAndIsDeletedFalse(userNotification.getUserId());
            mailerService.sendMailWithOutFile(user.getEmail(),new String[0],userNotification.getTitle(),userNotification.getDescription());
            userNotificationRepository.save(userNotification);
        }
        return userNotificationList;
    }


    public void customCreateUserNotification(String title, String description, List<Long> receiverIdList){
        List<User> userList = new ArrayList<>();
        for (Long userId : receiverIdList) {
            User user = userRepository.findByUserIdAndIsDeletedFalse(userId);
            if(user != null){
                userList.add(user);
            }
            mailerService.sendMailWithOutFile(user.getEmail(),new String[0],title,description);

            UserNotification userNotification = new UserNotification();
            userNotification.setUserId(user.getUserId());
            userNotification.setTitle(title);
            userNotification.setDescription(description);
            userNotificationRepository.save(userNotification);
        }
    }
    }


