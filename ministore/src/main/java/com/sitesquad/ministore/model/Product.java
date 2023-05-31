package com.sitesquad.ministore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "productId")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "product")
public class Product implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name")
    private String name;
    
    @Column(name = "quantity")
    private Long quantity;
    
    @Column(name = "product_type_id", insertable = false, updatable = false)
    private Long productTypeId;
    
    @Column(name = "price")
    private Double price;
    
    @Column(name = "cost")
    private Double cost;
    
    @Column(name = "product_img")
    private String productImg;
    
    @Column(name ="product_code")
    private String productCode;
    
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "product_type_id", referencedColumnName = "product_type_id")
//    @JsonIgnore
    @ToString.Exclude
    private ProductType productType;
    
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @ToString.Exclude
    private Collection<ProductVoucher> productVouchers;
            
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @ToString.Exclude
    private Collection<OrderDetails> orderDetails;
 
}
