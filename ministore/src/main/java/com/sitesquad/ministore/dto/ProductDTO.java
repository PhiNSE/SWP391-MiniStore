package com.sitesquad.ministore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @author ADMIN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable{
    private Long productId;
    
    private String name;
    
    private Long quantity;
    
    private Long productTypeId;

    private String productTypeName;

    
    private Double price;
    
    private Double cost;
    
    private String productImg;
    
    private String productCode;

    private Boolean isDeleted;

    private Long minimum;

}
