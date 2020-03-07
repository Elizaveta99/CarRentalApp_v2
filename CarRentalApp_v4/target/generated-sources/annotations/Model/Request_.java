package Model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Request.class)
public abstract class Request_ {

	public static volatile SingularAttribute<Request, Integer> rentalTime;
	public static volatile SingularAttribute<Request, Client> client;
	public static volatile SingularAttribute<Request, String> model;
	public static volatile SingularAttribute<Request, Integer> id;

	public static final String RENTAL_TIME = "rentalTime";
	public static final String CLIENT = "client";
	public static final String MODEL = "model";
	public static final String ID = "id";

}

