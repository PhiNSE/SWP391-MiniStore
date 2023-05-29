package com.sitesquad.ministore.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Shift.class)
public abstract class Shift_ {

	public static volatile SingularAttribute<Shift, Long> shiftId;
	public static volatile CollectionAttribute<Shift, UserShift> userShifts;
	public static volatile SingularAttribute<Shift, Double> startWorkHour;
	public static volatile SingularAttribute<Shift, Double> coefficient;
	public static volatile SingularAttribute<Shift, Double> endWorkHour;

	public static final String SHIFT_ID = "shiftId";
	public static final String USER_SHIFTS = "userShifts";
	public static final String START_WORK_HOUR = "startWorkHour";
	public static final String COEFFICIENT = "coefficient";
	public static final String END_WORK_HOUR = "endWorkHour";

}

