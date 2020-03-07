package Controller;

import Model.Exception.DAOException;
import Model.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;

/**
 * Base class for DAO level
 * @param <E> - type of entity
 */
public abstract class JpaDAO<E, K> implements DAO<E, K> {

    static final Logger JpaDAOLogger = LogManager.getLogger(JpaDAO.class);

    @PersistenceContext
    protected static EntityManagerFactory entityManagerFactory;

    /**
     * Sets entityManager from main
     * @param entityManagerFactory - entityManagerFactory
     */
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public JpaDAO() {
    }

    /**
     * Inserts in db
     * @param entity - entity to unsert in db
     */
    public void persist(E entity) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            throw new DAOException("Insert exception " + e.getMessage());
        }
        JpaDAOLogger.info("Insertion successful");
        entityManager.close();
    }

    /**
     * Deletes from db
     */
    public void remove(K id) throws DAOException  {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Request entityToDelete = entityManager.find(Request.class, id);
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityToDelete);
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            throw new DAOException("Delete exception " + e.getMessage());
        }
        JpaDAOLogger.info("Deletion successful");
        entityManager.close();
    }
}
