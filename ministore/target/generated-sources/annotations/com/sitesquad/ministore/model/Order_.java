package com.sitesquad.ministore.model;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, Timestamp> date;
	public static volatile SingularAttribute<Order, User> orderUser;
	public static volatile CollectionAttribute<Order, OrderDetails> orderDetails;
	public static volatile SingularAttribute<Order, Long> orderId;
	public static volatile SingularAttribute<Order, Boolean> type;
	public static volatile SingularAttribute<Order, Long> userId;

	public static final String DATE = "date";
	public static final String ORDER_USER = "orderUser";
	public static final String ORDER_DETAILS = "orderDetails";
	public static final String ORDER_ID = "orderId";
	public static final String TYPE = "type";
	public static final String USER_ID = "userId";

}

