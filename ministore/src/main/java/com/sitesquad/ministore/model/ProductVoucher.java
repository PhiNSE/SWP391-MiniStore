package com.sitesquad.ministore.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "product_voucher")
public class ProductVoucher {
    @Id
    @Column(name = "product_voucher_id")
    Long id;
    
    @ToString.Exclude
    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Long productId;

    @ToString.Exclude
    @ManyToOne(targetEntity = Voucher.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "voucher_id", referencedColumnName = "voucher_id")
    private Long voucherId;
}
