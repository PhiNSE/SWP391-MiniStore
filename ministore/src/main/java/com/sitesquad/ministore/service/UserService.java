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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ACER
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    public Page<UserDTO> mapDTO(Page<User> userPage) {
        Page<UserDTO> userDTOPage = userPage.map(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setRoles(user.getRole().getName());
            userDTO.setAddress(user.getAddress());
            userDTO.setPhone(user.getPhone());
            userDTO.setDob(user.getDob());
            userDTO.setGender(user.getGender());
            userDTO.setUserImg(user.getUserImg());
            return userDTO;
        });
        return userDTOPage;
    }


    public User checkLogIn(String email, String password){
        return userRepository.findOneByEmailIgnoreCaseAndPasswordIgnoreCaseAndIsDeletedFalse(email,password);
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
    public Page<UserDTO> findAll(Integer offset) {
        Page<User> user = userRepository.findAllByIsDeletedFalse(PageRequest.of(offset, 9));
        Page<UserDTO> userDTOPage = mapDTO(user);
        return userDTOPage;
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findByUserIdAndIsDeletedFalse(id);
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setName(user.getName());
            userDTO.setRoles(user.getRole().getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhone(user.getPhone());
            userDTO.setDob(user.getDob());
            userDTO.setGender(user.getGender());
            userDTO.setAddress(user.getAddress());
            return userDTO;
        } else {
            return null;
        }
    }

    public boolean delete(Long id) {
        User user = userRepository.findByUserIdAndIsDeletedFalse(id);
        if (user == null) {
            return false;
        } else {
            user.setIsDeleted(true);
            userRepository.save(user);
            return true;
        }
    }

    public boolean checkUserExist(String email, String phone) {
        return userRepository.findOneByEmailIgnoreCaseOrPhone(email, phone) != null;
    }

    public List<User> findUserByRoleName(String name) {
        return userRepository.findUserByRole_NameIgnoreCaseAndIsDeletedFalse(name);
    }


    public User add (String name,String email,String phone,String address,String dob, String gender ,Long roleId, String userImg){
        User user = new User();


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dobDate = dateFormat.parse(dob);
            user.setDob(dobDate);
        } catch (Exception e) {
            System.out.println(e);
        }

        Boolean genderBoolean = null;
        if (gender.trim().toLowerCase() == "male") {
            genderBoolean = true;
        } else {
            genderBoolean = false;
        }
        user.setGender(genderBoolean);

        user.setIsDeleted(false);
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserImg(userImg);
        user.setRole(roleRepository.findByRoleIdAndIsDeletedFalse(roleId));
        user.setPassword("1");
        return userRepository.save(user);
    }

    public User addUser(User user){
        user.setIsDeleted(Boolean.FALSE);
        user.setRole(roleRepository.findByRoleIdAndIsDeletedFalse(user.getRoleId()));
        user.setPassword("1");
        return userRepository.save(user);
    }

    public User edit (User newUser){
        User oldUser = userRepository.findOneByEmailIgnoreCaseAndIsDeletedFalse(newUser.getEmail());
        if(oldUser == null)
            return null;
        newUser.setUserId(null);

        User userChanged =addUser(newUser);
        if(userChanged != null){
            oldUser.setIsDeleted(Boolean.TRUE);
            userRepository.save(oldUser);
        }
        return userChanged;
    }

    public User findUserByUsername(String email){
        return userRepository.findOneByEmailIgnoreCaseAndIsDeletedFalse(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findOneByEmailIgnoreCaseAndIsDeletedFalse(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Role role = roleService.findById(user.getRoleId());
        if (role == null) {
            throw new IllegalStateException("Role not found for the user");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRole())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role roles){
        return Collections.singletonList(new SimpleGrantedAuthority(roles.getName()));
    }
}
