package com.sitesquad.ministore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
import org.springframework.lang.Nullable;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @Column(name = "product_id", insertable = false, updatable = false)
    private Long productId;

    @Column(name = "order_id", insertable = false, updatable = false)
    private Long orderId;

    @Column(name = "quantity", nullable = true)
    private Long quantity;

    @Column(name = "price", nullable = true)
    private Double price;

    @Column(name = "total", nullable = true)
    private Double total;

    @Column(name = "product_voucher_id", nullable = true, insertable = false, updatable = false)
    private Long productVoucherId;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @JsonBackReference
    @ToString.Exclude
    private Order orderDet;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "product_voucher_id", referencedColumnName = "product_voucher_id", nullable = true)
    @JsonIgnore
    @ToString.Exclude
    private ProductVoucher productVoucher;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
//    @JsonIgnore
    @ToString.Exclude
    private Product product;

}
