package com.sitesquad.ministore.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ {

	public static volatile SingularAttribute<Role, Long> roleId;
	public static volatile SingularAttribute<Role, String> name;
	public static volatile CollectionAttribute<Role, User> users;

	public static final String ROLE_ID = "roleId";
	public static final String NAME = "name";
	public static final String USERS = "users";

}

