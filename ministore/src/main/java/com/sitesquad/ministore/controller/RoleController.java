/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.model.Role;
import com.sitesquad.ministore.repository.RoleRepository;
import com.sitesquad.ministore.service.RoleService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ACER
 */
@RestController
@RequestMapping(path = "/role")
@EntityScan(basePackages = "com.sitesquad.ministore.service.RoleService")
public class RoleController {
    @Autowired
    RoleService roleService;
    

    @GetMapping()
    public ResponseEntity<ResponseObject> getAllRole(){
        List<Role> roleList = roleService.findAll();
        if(!roleList.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Role List found", roleList)
            );
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Role List not found", roleList)
            );
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getRoleById(Long id){
        Role foundRole = roleService.findById(id);
        if(foundRole != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Role found", foundRole)
            );
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Role not found", foundRole)
            );
        }
    }
    
    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addRole(@RequestBody Role role){
        Role addRole = roleService.add(role);
        if(addRole != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Add sucessfully ", addRole)
            );
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(405, "Method not allowed", addRole)
            );
        }
    }
    
    
}
