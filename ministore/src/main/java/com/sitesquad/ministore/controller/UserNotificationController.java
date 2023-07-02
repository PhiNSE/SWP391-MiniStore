package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.dto.RequestMeta;
import com.sitesquad.ministore.dto.ResponseObject;
import com.sitesquad.ministore.model.UserNotification;
import com.sitesquad.ministore.repository.EmailService;
import com.sitesquad.ministore.service.UserNotificationService;
import com.sitesquad.ministore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/userNotify")
public class UserNotificationController {
    @Autowired
    RequestMeta requestMeta;

    @Autowired
    UserNotificationService userNotificationService;

    @Autowired
    UserService userService;


    @Autowired
    EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<ResponseObject> sendNotify(@RequestBody (required = false) List<UserNotification> userNotificationList){
        if(requestMeta != null && requestMeta.getRole().trim().equalsIgnoreCase("Admin")){




                List<UserNotification> userNotificationLists =userNotificationService.createUserNotification(userNotificationList);


            if(userNotificationLists != null){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "Add notify success", userNotificationLists)
                );
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(500, "Add failed", "")
                );
            }

        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseObject(405, "Access denied", "")
            );
        }
    }

    @GetMapping()
    public ResponseEntity<ResponseObject> getUserNotify(){
        List<UserNotification> userNotificationList = userNotificationService.findByUserId(requestMeta.getUserId());
        if (!userNotificationList.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Notification List found", userNotificationList)
            );
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "Not found", "")
            );
        }
    }
}
