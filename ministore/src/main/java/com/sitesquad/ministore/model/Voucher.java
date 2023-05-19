package com.sitesquad.ministore.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "voucher")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "min_item")
    private int minItem;

    @Column(name = "min_total")
    private int minTotal;

    @Column(name = "max_percent")
    private double maxPercent;

    @Column(name = "percent_discount")
    private double percentDiscount;

    @Column(name = "expired_date")
    private Date expiredDate;
        
    @OneToMany(mappedBy = "voucher")
    @JsonManagedReference
    @ToString.Exclude
    private Collection<ProductVoucher> productVouchers;
    
    
}
