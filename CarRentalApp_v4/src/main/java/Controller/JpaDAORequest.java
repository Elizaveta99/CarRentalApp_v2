package Controller;

import Model.Car;
import Model.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO level for requests to Request entity
 */
public class JpaDAORequest extends JpaDAO implements DAORequest {
    static final Logger DAORequestLogger = LogManager.getLogger(JpaDAORequest.class);

    /**
     * Returns all requests by given model
     * @param model
     * @return all requests by model
     */
    public List<Request> getAllRequestsByModel(String model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        List<Request> requests = (List<Request>)entityManager.createNamedQuery("getAllRequestsByModelSQL")
//                .setParameter("model", model)
//                .getResultList();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Request> q = cb.createQuery(Request.class);
        Root<Request> c = q.from(Request.class);
        ParameterExpression<String> pModel = cb.parameter(String.class);
        q.select(c).where(cb.equal(c.get("model"), pModel));
        TypedQuery<Request> query = entityManager.createQuery(q);
        query.setParameter(pModel, model);
        List<Request> requests = query.getResultList();

        DAORequestLogger.info("Returned all requests by model successfully.");
        entityManager.close();
        return requests;
    }

    /**
     * Returns all requests
     * @return list of requests
     */
    public List<Request> getAllRequests() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        List<Request> requests = new ArrayList<Request>();
//        requests = (ArrayList<Request>)entityManager.createNamedQuery("getAllRequestsSQL")
//                .getResultList();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Request> q = cb.createQuery(Request.class);
        Root<Request> c = q.from(Request.class);
        q.select(c);
        TypedQuery<Request> query = entityManager.createQuery(q);
        List<Request> requests = query.getResultList();

        DAORequestLogger.info("Returned all requests successfully.");
        entityManager.close();
        return requests;
    }
}
