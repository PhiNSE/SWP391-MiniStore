package com.sitesquad.ministore.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String productTypeName;

    private Double price;

    private Double cost;

    private String productImg;

    private String productCode;

    private Boolean isDeleted;
}
