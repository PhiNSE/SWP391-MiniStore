package com.sitesquad.ministore.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author ADMIN
 */


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "product")
public class Product implements Serializable{
    @Id
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "quantity")
    private Long quantity;
    
    @Column(name = "price")
    private Double price;
    
    @Column(name = "product_img")
    private String productImg;
    
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "product_type_id", referencedColumnName = "product_type_id")
    private ProductType productType;
    
        //equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    //hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
