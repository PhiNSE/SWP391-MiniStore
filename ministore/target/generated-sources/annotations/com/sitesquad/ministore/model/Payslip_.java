package com.sitesquad.ministore.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Payslip.class)
public abstract class Payslip_ {

	public static volatile SingularAttribute<Payslip, Date> endDate;
	public static volatile SingularAttribute<Payslip, Integer> shiftCount;
	public static volatile SingularAttribute<Payslip, Long> id;
	public static volatile SingularAttribute<Payslip, Double> salary;
	public static volatile SingularAttribute<Payslip, Long> userId;
	public static volatile SingularAttribute<Payslip, User> user;
	public static volatile SingularAttribute<Payslip, Date> startDate;

	public static final String END_DATE = "endDate";
	public static final String SHIFT_COUNT = "shiftCount";
	public static final String ID = "id";
	public static final String SALARY = "salary";
	public static final String USER_ID = "userId";
	public static final String USER = "user";
	public static final String START_DATE = "startDate";

}

