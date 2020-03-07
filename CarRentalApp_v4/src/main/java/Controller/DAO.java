package Controller;

import Model.Exception.DAOException;
import Model.Exception.ViewException;

/**
 * Interface for base DAO level
 * @param <E>
 */
public interface DAO<E, K> {
    void persist(E entity) throws ViewException, DAOException;
    void remove(K id) throws ViewException, DAOException;
}
