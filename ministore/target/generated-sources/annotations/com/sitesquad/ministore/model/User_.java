package com.sitesquad.ministore.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> address;
	public static volatile SingularAttribute<User, String> phone;
	public static volatile SingularAttribute<User, Long> roleId;
	public static volatile SingularAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, String> name;
	public static volatile CollectionAttribute<User, Order> orders;
	public static volatile CollectionAttribute<User, Payslip> payslips;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> email;

	public static final String PASSWORD = "password";
	public static final String ADDRESS = "address";
	public static final String PHONE = "phone";
	public static final String ROLE_ID = "roleId";
	public static final String ROLES = "roles";
	public static final String NAME = "name";
	public static final String ORDERS = "orders";
	public static final String PAYSLIPS = "payslips";
	public static final String ID = "id";
	public static final String EMAIL = "email";

}

