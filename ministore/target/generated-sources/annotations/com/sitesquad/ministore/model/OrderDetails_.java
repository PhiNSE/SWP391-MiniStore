package com.sitesquad.ministore.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderDetails.class)
public abstract class OrderDetails_ {

	public static volatile SingularAttribute<OrderDetails, Long> productVoucherId;
	public static volatile SingularAttribute<OrderDetails, ProductVoucher> productVoucher;
	public static volatile SingularAttribute<OrderDetails, Double> total;
	public static volatile SingularAttribute<OrderDetails, Product> product;
	public static volatile SingularAttribute<OrderDetails, Long> quantity;
	public static volatile SingularAttribute<OrderDetails, Long> productId;
	public static volatile SingularAttribute<OrderDetails, Order> orderDet;
	public static volatile SingularAttribute<OrderDetails, Long> orderId;
	public static volatile SingularAttribute<OrderDetails, Double> price;
	public static volatile SingularAttribute<OrderDetails, Long> id;

	public static final String PRODUCT_VOUCHER_ID = "productVoucherId";
	public static final String PRODUCT_VOUCHER = "productVoucher";
	public static final String TOTAL = "total";
	public static final String PRODUCT = "product";
	public static final String QUANTITY = "quantity";
	public static final String PRODUCT_ID = "productId";
	public static final String ORDER_DET = "orderDet";
	public static final String ORDER_ID = "orderId";
	public static final String PRICE = "price";
	public static final String ID = "id";

}

