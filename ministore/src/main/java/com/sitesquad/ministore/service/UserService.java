/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.service;

import com.sitesquad.ministore.dto.UserDTO;
import com.sitesquad.ministore.model.Role;
import com.sitesquad.ministore.model.User;
import com.sitesquad.ministore.repository.RoleRepository;
import com.sitesquad.ministore.repository.UserRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
           userDTO.setDob(user.getDob());
           userDTO.setGender(user.getGender());
           userDTO.setUserImg(user.getUserImg());
           return userDTO;
        });
        return userDTOPage;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
    
    public List<User> findAllExceptAdmin(){
        List<User> emp = userRepository.findByRoleId(new Long(2));
        List<User> guard = userRepository.findByRoleId(new Long(3));
        return Stream.concat(emp.stream(), guard.stream())
                .collect(Collectors.toList());
    }


//    public Page<User> findAll(Integer offset){
//        Page<User> user = userRepository.findAllByIsDeletedFalse(PageRequest.of(offset,9));
//        return user;
//    }
    public Page<UserDTO> findAll(Integer offset){
        Page<User> user = userRepository.findAllByIsDeletedFalse(PageRequest.of(offset,9));
        Page<UserDTO>userDTOPage = mapDTO(user);
        return userDTOPage;
    }
    
    public UserDTO findById(Long id){
        User user = userRepository.findByUserIdAndIsDeletedFalse(id);
        if(user != null ){
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setName(user.getName());
            userDTO.setRoles(user.getRoles().getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhone(user.getPhone());
            userDTO.setDob(user.getDob());
            userDTO.setGender(user.getGender());
            userDTO.setAddress(user.getAddress());
            return userDTO;
        }else{
            return null;
        }
    }
    
    public boolean delete(Long id){
        User user = userRepository.findByUserIdAndIsDeletedFalse(id);
        if(user == null){
            return false;
        }else{
            user.setIsDeleted(true);
            userRepository.save(user);
            return true;
        }
    }

    public boolean checkUserExist(String email, String phone){
        return userRepository.findOneByEmailIgnoreCaseOrPhone(email,phone) != null;
    }

    public List<User>findUserByRoleName(String name){
        return userRepository.findUserByRoles_NameIgnoreCaseAndIsDeletedFalse(name);
    }

    public User add (String name,String email,String phone,String address,String dob, String gender ,String roleName, String userImg){
        User user = new User();

        Role roleNameAdd = roleRepository.findByNameIgnoreCaseAndIsDeletedIsFalse(roleName.trim());
        user.setRoles(roleNameAdd);
        System.out.println(roleNameAdd);
        user.setRoleId(roleNameAdd.getRoleId());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dobDate = dateFormat.parse(dob);
            user.setDob(dobDate);
        }catch (Exception e){
            System.out.println(e);
        }

        Boolean genderBoolean = null;
        if(gender.trim().toLowerCase() == "male"){
            genderBoolean = true;
        }else {
            genderBoolean = false;
        }
        user.setGender(genderBoolean);

        user.setIsDeleted(false);
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserImg(userImg);
        user.setPassword("1");
        return userRepository.save(user);
    }
    
    public User edit (User newUser){
        User oldUser = userRepository.findByUserIdAndIsDeletedFalse(newUser.getUserId());
        newUser.setUserId(null);
        User userChanged = add(newUser.getName(),newUser.getEmail(),newUser.getPhone(),newUser.getAddress(),newUser.getDob().toString(),newUser.getGender().toString(),newUser.getRoles().toString(),newUser.getUserImg());
        if(userChanged != null){
            oldUser.setIsDeleted(Boolean.TRUE);
            userRepository.save(oldUser);
        }
        return userChanged;
    }


}
