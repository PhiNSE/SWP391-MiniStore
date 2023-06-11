/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ACER
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{
    List<User> findByRoleId(Long roleId);
    List<User> findAll();
    Page<User> findAll(Pageable pageable);
    Page<User> findAllByIsDeletedFalse(Pageable pageable);
    User findByUserIdAndIsDeletedFalse(Long id);
    List<User> findUserByRole_NameIgnoreCaseAndIsDeletedFalse(String roleName);

    User findOneByEmailIgnoreCaseOrPhone(String email,String phone);
    User findOneByEmailIgnoreCaseAndPasswordIgnoreCase(String email, String password);
}
