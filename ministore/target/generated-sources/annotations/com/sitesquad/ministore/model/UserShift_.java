package com.sitesquad.ministore.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserShift.class)
public abstract class UserShift_ {

	public static volatile SingularAttribute<UserShift, Long> shiftId;
	public static volatile SingularAttribute<UserShift, Date> workDate;
	public static volatile SingularAttribute<UserShift, Boolean> isHoliday;
	public static volatile SingularAttribute<UserShift, Shift> shifts;
	public static volatile SingularAttribute<UserShift, Boolean> isPresent;
	public static volatile SingularAttribute<UserShift, Long> id;
	public static volatile SingularAttribute<UserShift, Boolean> isWeekend;
	public static volatile SingularAttribute<UserShift, Long> userId;
	public static volatile SingularAttribute<UserShift, User> users;

	public static final String SHIFT_ID = "shiftId";
	public static final String WORK_DATE = "workDate";
	public static final String IS_HOLIDAY = "isHoliday";
	public static final String SHIFTS = "shifts";
	public static final String IS_PRESENT = "isPresent";
	public static final String ID = "id";
	public static final String IS_WEEKEND = "isWeekend";
	public static final String USER_ID = "userId";
	public static final String USERS = "users";

}

