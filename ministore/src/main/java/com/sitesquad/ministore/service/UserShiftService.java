/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.UserShift;
import com.sitesquad.ministore.repository.ShiftRepository;
import com.sitesquad.ministore.repository.UserRepository;
import com.sitesquad.ministore.repository.UserShiftRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class UserShiftService {

    @Autowired
    UserShiftRepository userShiftRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShiftRepository shiftRepository;

    public List<UserShift> findAll() {
        return userShiftRepository.findAll();
    }

    public UserShift findById(Long id) {
        Optional<UserShift> foundOrder = userShiftRepository.findById(id);
        return foundOrder.get();
    }

    public UserShift add(UserShift userShift) {
        userShift.setUsers(userRepository.findById(userShift.getUserId()).get());
        userShift.setShifts(shiftRepository.findById(userShift.getShiftId()).get());
        return userShiftRepository.save(userShift);
    }

    public UserShift edit(UserShift newUserShift) {
        return userShiftRepository.save(newUserShift);
    }
}
