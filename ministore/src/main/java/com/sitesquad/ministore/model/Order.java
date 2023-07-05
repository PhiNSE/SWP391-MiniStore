package com.sitesquad.ministore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.sql.Timestamp;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author admin
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "tbl_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "type")
    private Boolean type;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;
    
    @Column(name = "voucher_id",nullable = true, insertable = false, updatable = false)
    private Long voucherId;

    @Column(name = "total")
    private Double total;
    
    @Column(name = "date")
    private Timestamp date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
//    @JsonIgnore
    @ToString.Exclude
    private User orderUser;
    
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "voucher_id", referencedColumnName = "voucher_id")
    @JsonIgnore
    @ToString.Exclude
    private Voucher voucher;

    @OneToMany(mappedBy = "orderDet")
    @JsonIgnore
    @ToString.Exclude
    private Collection<OrderDetails> orderDetails;
}
