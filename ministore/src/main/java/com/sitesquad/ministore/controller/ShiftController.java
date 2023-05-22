/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.model.ResponseObject;
import com.sitesquad.ministore.model.Shift;
import com.sitesquad.ministore.repository.ShiftRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ACER
 */
@RestController  
@RequestMapping(path = "/test/products")
public class ShiftController {
    @Autowired
    ShiftRepository shiftRepository;
    
    @GetMapping()
    public ResponseEntity<ResponseObject> getAllShiftList(){
        List<Shift> shiftList = shiftRepository.findAll();
        if(!shiftList.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Shift List found", shiftList)
            );
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(404, "Shift List not found", shiftList)
            );
        }
    }
    
    
}
