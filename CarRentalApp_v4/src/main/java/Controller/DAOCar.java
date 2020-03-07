package Controller;

import Model.Car;
import Model.Exception.DAOException;

import java.util.List;

/**
 * Interface for DAO level for Car entity
 */
public interface DAOCar extends DAO {
    int getAmountByCarModel(String carModel) throws DAOException;
    List<Car> getCarByCarStateAndModel(boolean carState, String carModel);
    boolean getCarStateByCarId(int carId) throws DAOException;
    void getCarByIdUpdate(int carId, boolean state) throws DAOException;
    List<Car> getAllCars();
}
