package com.sitesquad.ministore.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProductVoucher.class)
public abstract class ProductVoucher_ {

	public static volatile CollectionAttribute<ProductVoucher, OrderDetails> orderDetails;
	public static volatile SingularAttribute<ProductVoucher, Product> product;
	public static volatile SingularAttribute<ProductVoucher, Long> productId;
	public static volatile SingularAttribute<ProductVoucher, Long> voucherId;
	public static volatile SingularAttribute<ProductVoucher, Voucher> voucher;
	public static volatile SingularAttribute<ProductVoucher, Long> id;

	public static final String ORDER_DETAILS = "orderDetails";
	public static final String PRODUCT = "product";
	public static final String PRODUCT_ID = "productId";
	public static final String VOUCHER_ID = "voucherId";
	public static final String VOUCHER = "voucher";
	public static final String ID = "id";

}

