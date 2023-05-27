package com.sitesquad.ministore.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ {

	public static volatile CollectionAttribute<Product, OrderDetails> orderDetails;
	public static volatile SingularAttribute<Product, Long> quantity;
	public static volatile SingularAttribute<Product, Double> cost;
	public static volatile SingularAttribute<Product, String> productCode;
	public static volatile SingularAttribute<Product, Boolean> isDeleted;
	public static volatile SingularAttribute<Product, Double> price;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, String> productImg;
	public static volatile SingularAttribute<Product, ProductType> productTypes;
	public static volatile SingularAttribute<Product, Long> id;
	public static volatile CollectionAttribute<Product, ProductVoucher> productVouchers;
	public static volatile SingularAttribute<Product, Long> productTypeId;

	public static final String ORDER_DETAILS = "orderDetails";
	public static final String QUANTITY = "quantity";
	public static final String COST = "cost";
	public static final String PRODUCT_CODE = "productCode";
	public static final String IS_DELETED = "isDeleted";
	public static final String PRICE = "price";
	public static final String NAME = "name";
	public static final String PRODUCT_IMG = "productImg";
	public static final String PRODUCT_TYPES = "productTypes";
	public static final String ID = "id";
	public static final String PRODUCT_VOUCHERS = "productVouchers";
	public static final String PRODUCT_TYPE_ID = "productTypeId";

}

