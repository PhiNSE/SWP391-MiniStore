/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.controller;


import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.repository.RoleRepository;
import com.sitesquad.ministore.repository.UserRepository;
import com.sitesquad.ministore.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ACER
 */
@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    UserService userService;

    
    @GetMapping()
    public ResponseEntity<ResponseObject> getAllUser(){
        List<User> userList = userService.findAll();
        if(!userList.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "User List found", userList)
            );
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "User List not found", userList)
            );
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUserById(Long id){
        User foundUser = userService.findById(id);
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
        User addUser = userService.add(user);
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
    
    @DeleteMapping("/delete/{id}")
     public ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id){
        boolean isDeleted = userService.delete(id);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(
                   new ResponseObject(200, "Delete sucessfully ", "")
            );
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(500, "Cant delete product", "")
            );
        }
     }
}

