/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.RequestLogin;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.repository.UserRepository;
import com.sitesquad.ministore.utils.JwtUtils;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ACER
 */
@RestController
public class LogInController {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody RequestLogin login){
        
        ResponseObject responseObj = new ResponseObject();
        User user = userRepository.findOneByEmailIgnoreCaseAndPasswordIgnoreCase(login.getEmail(), login.getPassword());
        
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(404, "log in failed", null)
            );
        }
        String token = jwtUtils.generateJWT(user);
        
        Map<String , Object> data = new HashMap<>();
        data.put("access token", token);
        responseObj.setData(data); 
        return ResponseEntity.status(HttpStatus.OK).body(responseObj);
    }
    
    
}
