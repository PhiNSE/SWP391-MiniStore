package com.sitesquad.ministore.model;

import java.util.Objects;
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
@Table(name = "order_detail")
public class OrderDetails {

    @Id
    @Column(name = "order_detail_id")
    private Long id;

    @ToString.Exclude
    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Long productId;

    @ToString.Exclude
    @ManyToOne(targetEntity = Order.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Long orderId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @Column(name = "total")
    private double total;

    @ToString.Exclude
    @ManyToOne(targetEntity = ProductVoucher.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_voucher_id", referencedColumnName = "product_voucher_id")
    private Long productVoucherId;

}
