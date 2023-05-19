package com.sitesquad.ministore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
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
    @JsonBackReference
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

    public Product() {
    }

    public Product(Long id, String name, Long quantity, Double price, String productImg, Boolean isDeleted, ProductType productType) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.productImg = productImg;
        this.isDeleted = isDeleted;
        this.productType = productType;
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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", productImg='" + productImg + '\'' +
                ", isDeleted=" + isDeleted +
                ", productType=" + productType +
                '}';
    }
}
