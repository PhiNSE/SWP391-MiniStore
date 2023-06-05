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
import java.util.Map;
import java.util.Optional;

import com.sitesquad.ministore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
                        new ResponseObject(404, "User List not found", "")
                );
            }
        }else {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                    new ResponseObject(405, "Access denied", "")
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
                    new ResponseObject(404, "User not found", "")
            );
        }
    }
    
//    @GetMapping("/search")
//    public ResponseEntity<ResponseObject> getUserByRoleName()


    
    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addUser(@RequestBody Map<String, String> requestData){

        String email = requestData.get("email");
        String phone = requestData.get("phone");
        String name = requestData.get("name");
        String address = requestData.get("address");
        String roleName = requestData.get("roleName");
        System.out.println("Name: " + name);
        System.out.println("Role Name: " + roleName);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Address: " + address);

        if(requestMeta.getRole().trim().equalsIgnoreCase("Admin")) {
            boolean checkUser = userService.checkUserExist(email, phone);
            if (checkUser == true) {
                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                        new ResponseObject(405, "User email or phone is already use", "")
                );
            } else {
                User addUser = userService.add(name,email,phone,address,roleName);
                if (addUser != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject(200, "Add success", addUser)
                    );
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject(500, "Add failed", "")
                    );
                }
            }
        }else{
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                    new ResponseObject(406, "Access denied", "")
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUser (@PathVariable Long id){
        if(requestMeta.getRole().trim().equalsIgnoreCase("Admin")) {
            boolean userDelete = userService.delete(id);
            if (userDelete == true){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "Delete success", "")
                );
            }else
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(500, "Delete failed", "")
                );

        }else{
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                    new ResponseObject(406, "Access denied", "")
            );
        }
    }
}

