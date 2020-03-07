package Controller;
import Model.Car;
import Model.Client;
import Model.Exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

/**
 * DAO level for requests to Client entity
 */
public class JpaDAOClient extends JpaDAO implements DAOClient {
    static final Logger DAOClientLogger = LogManager.getLogger(JpaDAOClient.class);

    /**
     * Returns client by given id
     * @param idPassport
     * @return boolean - is client exists
     */
    public Client getClientById(String idPassport) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Client client = new Client();
//        try {
//            client = (Client) entityManager.createNamedQuery("getClientByIdSQL")
//                    .setParameter("id_passport", idPassport)
//                    .getSingleResult();
//
//        } catch (NoResultException e) {
//            client = null;
//            throw new DAOException("getClientById exception " + e.getMessage());
//        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> q = cb.createQuery(Client.class);
        Root<Client> c = q.from(Client.class);
        ParameterExpression<String> pId = cb.parameter(String.class);
        q.select(c).where(cb.equal(c.get("idPassport"), pId));
        TypedQuery<Client> query = entityManager.createQuery(q);
        query.setParameter(pId, idPassport);
        client = query.getSingleResult();

        DAOClientLogger.info("Returned client by id successfully.");
        return  client;
    }

}
