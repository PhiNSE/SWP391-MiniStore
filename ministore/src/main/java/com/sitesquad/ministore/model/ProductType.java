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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "product_type")
public class ProductType{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_type_id")
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @OneToMany(mappedBy = "productTypes")
    @JsonManagedReference
    @ToString.Exclude
    private Collection<Product> products;
    
}
