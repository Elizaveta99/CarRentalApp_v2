package Model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Car.class)
public abstract class Car_ {

	public static volatile SingularAttribute<Car, Boolean> carState;
	public static volatile SingularAttribute<Car, Integer> carId;
	public static volatile SingularAttribute<Car, String> carModel;

	public static final String CAR_STATE = "carState";
	public static final String CAR_ID = "carId";
	public static final String CAR_MODEL = "carModel";

}

