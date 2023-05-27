package com.sitesquad.ministore.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Voucher.class)
public abstract class Voucher_ {

	public static volatile SingularAttribute<Voucher, Integer> minItem;
	public static volatile SingularAttribute<Voucher, Double> maxPercent;
	public static volatile SingularAttribute<Voucher, Integer> quantity;
	public static volatile SingularAttribute<Voucher, Integer> minTotal;
	public static volatile SingularAttribute<Voucher, Date> expiredDate;
	public static volatile SingularAttribute<Voucher, String> description;
	public static volatile SingularAttribute<Voucher, Long> id;
	public static volatile CollectionAttribute<Voucher, ProductVoucher> productVouchers;
	public static volatile SingularAttribute<Voucher, Double> percentDiscount;

	public static final String MIN_ITEM = "minItem";
	public static final String MAX_PERCENT = "maxPercent";
	public static final String QUANTITY = "quantity";
	public static final String MIN_TOTAL = "minTotal";
	public static final String EXPIRED_DATE = "expiredDate";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String PRODUCT_VOUCHERS = "productVouchers";
	public static final String PERCENT_DISCOUNT = "percentDiscount";

}

