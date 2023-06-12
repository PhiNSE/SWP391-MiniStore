/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.Role;
import com.sitesquad.ministore.repository.RoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    
    public List<Role> findAll(){
        return roleRepository.findAll();
    }
    
    public Role findById(Long id){
        return roleRepository.findByRoleIdAndIsDeletedFalse(id);
    }
    
    public Role add(Role role){
        return roleRepository.save(role);
    }
    
    public boolean delete(Long id){
        Role role = roleRepository.findById(id).orElse(null);
        if(role == null){
            return false;
        }else{
            role.setIsDeleted(Boolean.TRUE);
            roleRepository.save(role);
            return true;
        }
    }
    
    public Role edit(Role newRole){
        Role oldRole = roleRepository.findById(newRole.getRoleId()).get();
        newRole.setRoleId(null);
        Role editRole = add(newRole);
        if(editRole != null){
            oldRole.setIsDeleted(Boolean.TRUE);
            roleRepository.save(oldRole);
        }
        return editRole;
    }
}
