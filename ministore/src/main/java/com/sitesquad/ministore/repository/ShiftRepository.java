/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author ACER
 */
public interface ShiftRepository extends JpaRepository<Shift, Long>, JpaSpecificationExecutor<Shift>{
    
}
