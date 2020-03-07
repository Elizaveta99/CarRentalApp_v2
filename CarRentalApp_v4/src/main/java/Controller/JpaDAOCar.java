package Controller;

import Model.Car;
import Model.Exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.lang.reflect.Type;
import java.util.List;

/**
 * DAO level for requests to Car entity
 */
public class JpaDAOCar extends JpaDAO implements DAOCar {
    static final Logger DAOCarLogger = LogManager.getLogger(JpaDAOCar.class);

    /**
     * Returns amount of cars of given model
     * @param carModel - model of the car
     * @return amount
     */
    public int getAmountByCarModel(String carModel) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String ansStr = "";
        int ans = 0;

//        try {
//            ansStr = entityManager.createNamedQuery("getAmountByCarModelSQL")
//                    .setParameter("car_model", carModel)
//                    .setParameter("car_state", true)
//                    .getSingleResult().toString();
//        } catch (NoResultException e) {
//            throw new DAOException("getAmountByCarModel exception" + e.getMessage());
//        }
//        ans = Integer.parseInt(ansStr);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Car> c = q.from(Car.class);

        ParameterExpression<Boolean> pState = cb.parameter(Boolean.class);
        ParameterExpression<String> pModel = cb.parameter(String.class);
        q.select(cb.count(c)).where(cb.equal(c.get("carState"), pState), (cb.equal(c.get("carModel"), pModel)));
        TypedQuery<Long> query = entityManager.createQuery(q);
        query.setParameter(pState, true).setParameter(pModel, carModel);
        ansStr = query.getSingleResult().toString();
        ans = Integer.parseInt(ansStr);

        DAOCarLogger.info("Returned amount by carModel successfully.");
        entityManager.close();
        return ans;
    }

    /**
     * Returns car's state by id
     * @param carId = id of given car
     * @return state
     */
    public boolean getCarStateByCarId(int carId) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        boolean ans = false;

//        try {
//            ans = Boolean.parseBoolean(entityManager.createNamedQuery("getCarStateByCarIdSQL")
//                    .setParameter("car_id", carId)
//                    .getSingleResult().toString());
//        } catch (NoResultException e) {
//            throw new DAOException("getCarStateByCarId exception" + e.getMessage());
//        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> q = cb.createQuery(Car.class);
        Root<Car> c = q.from(Car.class);
        ParameterExpression<Integer> pId = cb.parameter(Integer.class);
        q.select(c).where(cb.equal(c.get("carId"), pId));
        TypedQuery<Car> query = entityManager.createQuery(q);
        query.setParameter(pId, carId);
        Car car = query.getSingleResult();

        ans = car.isCarState();

        DAOCarLogger.info("Returned carState by carId successfully.");
        entityManager.close();
        return ans;
    }

    /**
     * Returns car object by car's state and model
     * @param carState - given state
     * @param carModel - given model
     * @return car objects
     */
    public List<Car> getCarByCarStateAndModel(boolean carState, String carModel) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> q = cb.createQuery(Car.class);
        Root<Car> c = q.from(Car.class);
        ParameterExpression<Boolean> pState = cb.parameter(Boolean.class);
        ParameterExpression<String> pModel = cb.parameter(String.class);
        q.select(c).where(cb.equal(c.get("carState"), pState), (cb.equal(c.get("carModel"), pModel)));
        TypedQuery<Car> query = entityManager.createQuery(q);
        query.setParameter(pState, carState).setParameter(pModel, carModel);
        List<Car> cars = query.getResultList();

        DAOCarLogger.info("Returned car by carState and carModel successfully.");
        entityManager.close();
        return cars;
    }

    /**
     * Updates car's availability
     * @param carId - id
     */
    public void getCarByIdUpdate(int carId, boolean state) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//        Car car = (Car)entityManager.createNamedQuery("getCarByIdSQL")
//                .setParameter("car_id", carId)
//                .getSingleResult();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> q = cb.createQuery(Car.class);
        Root<Car> c = q.from(Car.class);
        ParameterExpression<Integer> pId = cb.parameter(Integer.class);
        q.select(c).where(cb.equal(c.get("carId"), pId));
        TypedQuery<Car> query = entityManager.createQuery(q);
        query.setParameter(pId, carId);
        Car car = query.getSingleResult();

        car.setCarState(state);

        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            throw new DAOException("getCarByIdUpdate exception " + e.getMessage());
        }

        System.out.println("Car state updated successfully.");
        DAOCarLogger.info("Car state updated successfully.");
        entityManager.close();
    }

    /**
     * Returns list of cars
     * @return list of cars
     */
    public List<Car> getAllCars() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> q = cb.createQuery(Car.class);
        Root<Car> c = q.from(Car.class);
        q.select(c);
        TypedQuery<Car> query = entityManager.createQuery(q);
        List<Car> cars = query.getResultList();
        entityManager.close();
        return cars;
    }

}
