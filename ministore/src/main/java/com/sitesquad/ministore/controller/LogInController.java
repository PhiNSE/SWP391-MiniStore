/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.RequestLogin;
import com.sitesquad.ministore.model.RequestMeta;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.repository.UserRepository;
import com.sitesquad.ministore.service.UserService;
import com.sitesquad.ministore.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ACER
 */
@RestController
@CrossOrigin
public class LogInController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RequestMeta requestMeta;
    
    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody RequestLogin login){

        ResponseObject responseObj = new ResponseObject();
        User user = userService.checkLogIn(login.getEmail(), login.getPassword());

        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "log in failed", null)
            );
        }
        String token = jwtUtils.generateJWT(user);



        Map<String , Object> data = new HashMap<>();
        data.put("access token", token);
        responseObj.setStatus(200);
        responseObj.setMessage("Log In success");
        responseObj.setData(data); 
        return ResponseEntity.status(HttpStatus.OK).body(responseObj);
    }

    public Boolean authorization(String role){
        if(!role.trim().equalsIgnoreCase("Admin"))
            return true;
        else
            return false;
    }
    
    @GetMapping("/privateApi")
    public ResponseEntity<ResponseObject> privateApi(@RequestHeader (value = "token",defaultValue = "") String authToken) throws Exception{
        ResponseObject responseObj = new ResponseObject();
        Claims data = jwtUtils.verify(authToken);
        
        
        responseObj.setStatus(200);
        responseObj.setMessage("This is private api");
        responseObj.setData(data);
        
        return ResponseEntity.status(HttpStatus.OK).body(responseObj);
    }
    
}
