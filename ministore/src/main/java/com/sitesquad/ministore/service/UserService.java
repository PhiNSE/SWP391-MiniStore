/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.service;

import com.sitesquad.ministore.dto.UserDTO;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.repository.RoleRepository;
import com.sitesquad.ministore.repository.UserRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<UserDTO> mapDTO(Page<User> userPage){
        Page<UserDTO> userDTOPage = userPage.map(user -> {
           UserDTO userDTO = new UserDTO();
           userDTO.setUserId(user.getUserId());
           userDTO.setName(user.getName());
           userDTO.setEmail(user.getEmail());
           userDTO.setRoles(user.getRoles().getName());
           userDTO.setAddress(user.getAddress());
           userDTO.setPhone(user.getPhone());
           return userDTO;
        });
        return userDTOPage;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Page<UserDTO> findAll(Integer offset){
        Page<User> user = userRepository.findAll(PageRequest.of(offset,9));
        Page<UserDTO>userDTOPage = mapDTO(user);
        return userDTOPage;
    }
    
    public UserDTO findById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();

            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setName(user.getName());
            userDTO.setRoles(user.getRoles().getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhone(user.getPhone());
            userDTO.setAddress(user.getAddress());
            return userDTO;
        }else{
            return null;
        }
    }
    
    public boolean delete(Long id){
        userRepository.deleteById(id);
        return userRepository.findById(id)==null;
    }

    public boolean checkUserExist(String email, String phone){
        return userRepository.findOneByEmailIgnoreCaseOrPhone(email,phone) != null;
    }
    public User add (User user){
        user.setRoles(roleRepository.findById(user.getRoleId()).get());
        user.setDeleted(false);
        return userRepository.save(user);
    }
    
    public User edit (User newUser){
        return null;
    }


}
