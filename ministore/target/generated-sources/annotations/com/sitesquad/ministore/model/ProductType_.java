package com.sitesquad.ministore.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProductType.class)
public abstract class ProductType_ {

	public static volatile SingularAttribute<ProductType, String> name;
	public static volatile SingularAttribute<ProductType, Long> productTypeId;
	public static volatile CollectionAttribute<ProductType, Product> products;

	public static final String NAME = "name";
	public static final String PRODUCT_TYPE_ID = "productTypeId";
	public static final String PRODUCTS = "products";

}

