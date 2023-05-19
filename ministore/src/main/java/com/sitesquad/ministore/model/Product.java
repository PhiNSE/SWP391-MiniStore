package com.sitesquad.ministore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
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
@Table(name = "product")
public class Product implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "quantity")
    private Long quantity;
    
    @Column(name = "product_type_id", insertable = false, updatable = false)
    private Long productTypeId;
    
    @Column(name = "price")
    private Double price;
    
    @Column(name = "product_img")
    private String productImg;
    
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "product_type_id", referencedColumnName = "product_type_id")
    @JsonBackReference
    @ToString.Exclude
    private ProductType productTypes;
    
    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    @ToString.Exclude
    private Collection<ProductVoucher> productVouchers;
    
}
