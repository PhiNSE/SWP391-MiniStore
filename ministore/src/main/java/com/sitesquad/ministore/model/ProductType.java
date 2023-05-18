package com.sitesquad.ministore.model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
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
@Table(name = "product_type")
public class ProductType{
    @Id
    @Column(name = "product_type_id")
    private Long id;
    
    @Column(name = "name")
    private String name;
    
//    @OneToMany(mappedBy = "productType")
//    private Set<Product> products;
    
        //equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductType productType = (ProductType) o;
        return Objects.equals(id, productType.id);
    }

    //hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
