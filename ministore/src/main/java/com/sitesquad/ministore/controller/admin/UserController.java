/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.controller.admin;


import com.sitesquad.ministore.dto.UserDTO;
import com.sitesquad.ministore.model.RequestMeta;
import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.repository.RoleRepository;
import com.sitesquad.ministore.repository.UserRepository;
import java.util.List;
import java.util.Optional;

import com.sitesquad.ministore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author ACER
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RequestMeta requestMeta;

    @GetMapping()
    public ResponseEntity<ResponseObject> getAllUser(@RequestParam(required = false)  Integer offset){
        System.out.println(requestMeta.getName());
        if(requestMeta.getRole().trim().equalsIgnoreCase("Admin")){
            if (offset == null) {
                offset = 0;
            }
            Page<UserDTO> userList = userService.findAll(offset);

            if (!userList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "User List found", userList)
                );
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(404, "User List not found", userList)
                );
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new ResponseObject(40, "Access denied", "")
            );
        }
    }
    
    @GetMapping("/details")
    public ResponseEntity<ResponseObject> getUserById(@RequestParam Long id){
        UserDTO foundUser = userService.findById(id);
        if(foundUser != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "User found", foundUser)
            );
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "User not found", foundUser)
            );
        }
    }
    
//    @GetMapping("/search")
//    public ResponseEntity<ResponseObject> getUserByRoleName()
    
    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addUser(@RequestBody User user){
        user.setRoles(roleRepository.findById(user.getRoleId()).get());
        User addUser = userRepository.save(user);
        if(addUser != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Add success", addUser)
            );
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(500, "User not found", addUser)
            );
        }
    }
}

