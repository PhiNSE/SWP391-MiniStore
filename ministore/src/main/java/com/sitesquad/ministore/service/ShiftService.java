/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.service;

import com.sitesquad.ministore.model.Shift;
import com.sitesquad.ministore.repository.ShiftRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ACER
 */
public class ShiftService {
    @Autowired
    ShiftRepository shiftRepository;
    
    public List<Shift> findAll(){
        return shiftRepository.findAll();
    }
    
    public Shift findById(Long id){
        return shiftRepository.findById(id).get();
    }
    
    public Shift add(Shift shift){
        return shiftRepository.save(shift);
    }
    
    public boolean delete(Long id){
        shiftRepository.deleteById(id);
        return shiftRepository.findById(id) == null;
    }
}
