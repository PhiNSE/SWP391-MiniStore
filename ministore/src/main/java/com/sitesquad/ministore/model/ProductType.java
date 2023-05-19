package com.sitesquad.ministore.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 *
 * @author ADMIN
 */
@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@EqualsAndHashCode
@Table(name = "product_type")
public class ProductType{
    @Id
    @Column(name = "product_type_id")
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @OneToMany(mappedBy = "productType")
    @JsonManagedReference
    private Collection<Product> products;
    
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

    public ProductType() {
    }

    public ProductType(Long id, String name, Collection<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ProductType{" + "id=" + id + ", name=" + name +
//                ", products=" + products 
                + '}';
    }
    
}
