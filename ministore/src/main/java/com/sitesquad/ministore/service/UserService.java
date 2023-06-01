/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.repository.RoleRepository;
import com.sitesquad.ministore.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleRepository roleRepository;
    
    public List<User> findAll(){
        return userRepository.findAll();
    }
    
    public User findById(Long id){
        return userRepository.findById(id).get();
    }
    
    public boolean delete(Long id){
        userRepository.deleteById(id);
        return userRepository.findById(id)==null;
    }
    
    public User add (User user){
        user.setRoles(roleRepository.findById(user.getRoleId()).get());
        return userRepository.save(user);
    }
    
    public User edit (User newUser){
        return null;
    }


}
