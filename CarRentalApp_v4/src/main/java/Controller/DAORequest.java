package Controller;

import Model.Request;
import java.util.List;

/**
 * Interface for DAO level for Request entity
 */
public interface DAORequest extends DAO  {
    List<Request> getAllRequestsByModel(String model);
    List<Request> getAllRequests();
}
