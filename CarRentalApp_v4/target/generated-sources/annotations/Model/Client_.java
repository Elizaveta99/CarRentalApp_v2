package Model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Client.class)
public abstract class Client_ {

	public static volatile SingularAttribute<Client, String> idPassport;
	public static volatile SingularAttribute<Client, String> name;
	public static volatile SingularAttribute<Client, String> birthDate;
	public static volatile SingularAttribute<Client, String> residenceAddress;

	public static final String ID_PASSPORT = "idPassport";
	public static final String NAME = "name";
	public static final String BIRTH_DATE = "birthDate";
	public static final String RESIDENCE_ADDRESS = "residenceAddress";

}

