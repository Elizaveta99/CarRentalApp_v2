package Controller;

import Model.Client;
import Model.Exception.DAOException;

/**
 * Interface for DAO level for Client entity
 */
public interface DAOClient extends DAO  {
    Client getClientById(String idPassport) throws DAOException;
}
