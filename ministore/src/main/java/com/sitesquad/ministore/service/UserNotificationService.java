package com.sitesquad.ministore.service;

import com.sitesquad.ministore.constant.RoleConstant;
import com.sitesquad.ministore.constant.SystemConstant;
import com.sitesquad.ministore.model.Role;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.model.UserNotification;
import com.sitesquad.ministore.repository.UserNotificationRepository;
import com.sitesquad.ministore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.ZonedDateTime;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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

    public UserNotification findById(Long id){
        return userNotificationRepository.findOneByUserNotificationId(id);
    }

    public List<UserNotification> findByUserId(Long id){
        return userNotificationRepository.findByUserId(id);
    }


    public List<UserNotification> findByUserEmail(String email){


        List<UserNotification> userNotificationList = userNotificationRepository.findByUser_EmailIgnoreCaseOrderByDate(email);
        List<UserNotification> userNotificationListFormat = new ArrayList<>();
        for (UserNotification userNotification: userNotificationList) {
            Long date = userNotification.getDate().getTime();
            Instant instant = Instant.ofEpochMilli(date);

            userNotificationListFormat.add(userNotification);
        }
       return userNotificationList;
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
            userNotification.setUser(userRepository.findById(userNotification.getUserId()).get());
            Date date = new Date();
            userNotification.setDate(new Timestamp(date.getTime()));
            User user = userRepository.findByUserIdAndIsDeletedFalse(userNotification.getUserId());
            mailerService.sendMailWithOutFile(user.getEmail(),new String[0],userNotification.getTitle(),userNotification.getDescription());
            userNotificationRepository.save(userNotification);
        }
        return userNotificationList;
    }


    public void customCreateUserNotification(String title, String description, List<Long> receiverIdList) {
        List<User> userList = new ArrayList<>();
        for (Long userId : receiverIdList) {
            User user = userRepository.findByUserIdAndIsDeletedFalse(userId);
            if (user != null) {
                userList.add(user);
            }


            UserNotification userNotification = new UserNotification();
            Date date = new Date();
            userNotification.setDate(new Timestamp(date.getTime()));
            userNotification.setUserId(user.getUserId());
            userNotification.setTitle(title);
            userNotification.setDescription(description);
            userNotification.setUser(user);
            userNotificationRepository.save(userNotification);
            mailerService.sendMailWithOutFile(user.getEmail(), new String[0], title, description);
        }

    }
    public void sendNotiAndMailToAllByRole(String title, String description, String role){
        List<User> users = userRepository.findUserByRole_NameIgnoreCaseAndIsDeletedFalse(role);
        if(users==null) return;
        List<Long> userIds = new ArrayList<>();
        for (User user:users
             ) {
            userIds.add(user.getUserId());
        }
        customCreateUserNotification(title,description,userIds);

    }

    public void sendNotiAndMailToAllAdmins(String title,String description){
        sendNotiAndMailToAllByRole(title, description, RoleConstant.ADMIN_ROLE_NAME);
    }

}


