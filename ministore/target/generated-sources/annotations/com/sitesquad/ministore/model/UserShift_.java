package com.sitesquad.ministore.model;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserShift.class)
public abstract class UserShift_ {

	public static volatile SingularAttribute<UserShift, Long> shiftId;
	public static volatile SingularAttribute<UserShift, Boolean> isCheckedOut;
	public static volatile SingularAttribute<UserShift, Boolean> isCheckedIn;
	public static volatile SingularAttribute<UserShift, Shift> shift;
	public static volatile SingularAttribute<UserShift, Boolean> isHoliday;
	public static volatile SingularAttribute<UserShift, ZonedDateTime> startTime;
	public static volatile SingularAttribute<UserShift, ZonedDateTime> endTime;
	public static volatile SingularAttribute<UserShift, Long> userShiftId;
	public static volatile SingularAttribute<UserShift, Boolean> isWeekend;
	public static volatile SingularAttribute<UserShift, Long> userId;
	public static volatile SingularAttribute<UserShift, User> user;

	public static final String SHIFT_ID = "shiftId";
	public static final String IS_CHECKED_OUT = "isCheckedOut";
	public static final String IS_CHECKED_IN = "isCheckedIn";
	public static final String SHIFT = "shift";
	public static final String IS_HOLIDAY = "isHoliday";
	public static final String START_TIME = "startTime";
	public static final String END_TIME = "endTime";
	public static final String USER_SHIFT_ID = "userShiftId";
	public static final String IS_WEEKEND = "isWeekend";
	public static final String USER_ID = "userId";
	public static final String USER = "user";

}

