package View;
import Controller.JpaDAOCar;
import Controller.JpaDAOClient;
import Controller.JpaDAORequest;
import Model.Car;
import Model.Client;
import Model.Exception.DAOException;
import Model.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

/**
 * Class to work with view
 */
public class Main {

    /**
     * Logger fields
     */
    static final Logger rootLogger = LogManager.getRootLogger();
    static final Logger mainLogger = LogManager.getLogger(Main.class);

    public static void main(String[] args)  {

        System.out.println("------------Menu for Administrator------------");
        System.out.println("1 <model> <rental_time> <id_passport> - Leave request");
        System.out.println("2 <model> - Get list of requests for car of this model");
        System.out.println("3 <model> - Get amount  of available cars of this model");
        System.out.println("4 <car_id> - Get state  of car with this id");
        System.out.println("5 <request_id> <car_id> - Process request № id, client must pay the bill");
        System.out.println("6 <car_id> - Return car № id");
        System.out.println("7 <model> - Get all available cars of this model");
        System.out.println("8 - Get all cars");
        System.out.println("9 - Get all requests");
        System.out.println("10 - Finish work");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CarRental");

        JpaDAOCar jpaDAOCar = new JpaDAOCar();
        jpaDAOCar.setEntityManagerFactory(entityManagerFactory);
        JpaDAOClient jpaDAOClient = new JpaDAOClient();
        JpaDAORequest jpaDAORequest = new JpaDAORequest();

        boolean flag = true;
        while (flag)
        {
            System.out.println(">> ");
            Scanner in = new Scanner(System.in);
            String tokens[] = in.nextLine().split(" ");
            int type = Integer.parseInt(tokens[0]);
            String model = "";
            String idPassport = "";
            int rentalTime = 0;
            int id = 0;
            int requestId = 0;
            switch (type) {
                case (1):
                    try {
                        model = tokens[1];
                        rentalTime = Integer.parseInt(tokens[2]);
                        idPassport = tokens[3];

                        Request request = new Request();
                        Client client = jpaDAOClient.getClientById(idPassport);
                        if (client == null)
                        {
                            System.out.println("Put this client in database.");
                            System.out.println("Enter client's data: <name> <residence_address>, <birth_date>");
                            Scanner inClient = new Scanner(System.in);
                            String tokensClient[] = inClient.nextLine().split(" ");
                            String name = tokensClient[0];
                            String residenceAddress = tokensClient[1];
                            String birthDate = tokensClient[2];

                            client.setIdPassport(idPassport);
                            client.setName(name);
                            client.setResidenceAddress(residenceAddress);
                            client.setBirthDate(birthDate);

                            request.setClient(client);

                            try {
                                jpaDAOClient.persist(client);
                                System.out.println("Client added successfully");
                            } catch (DAOException e) {
                                mainLogger.error("Add new client exception. " + e.getMessage());
                            }
                        }
                        else
                            request.setClient(client);
                        request.setModel(model);
                        request.setRentalTime(rentalTime);

                        try {
                            jpaDAORequest.persist(request);
                            System.out.println("Request added successfully");
                        } catch (DAOException e) {
                            mainLogger.error("Leave request exception. " + e.getMessage());
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        mainLogger.error("Leave request exception. " + e.getMessage());
                    } catch (DAOException e) {
                        mainLogger.error("Leave request exception. " + e.getMessage());
                    }
                    break;
                case (2):
                    model = tokens[1];
                    List<Request> requests = jpaDAORequest.getAllRequestsByModel(model);
                    System.out.println("Requests: ");
                    for (Request req : requests) {
                        System.out.println(req.getId() + " " + req.getModel() + " " + req.getRentalTime() + " " + req.getClient().getIdPassport());
                    }
                    break;
                case (3):
                    model = tokens[1];
                    int amount = 0;
                    try {
                        amount = jpaDAOCar.getAmountByCarModel(model);
                    } catch (DAOException e) {
                        mainLogger.error("Get amount of available cars of this model exception. " + e.getMessage());
                    }
                    System.out.println("Amount: ");
                    System.out.println(amount);
                    break;
                case (4):
                    id = Integer.parseInt(tokens[1]);
                    boolean state = false;
                    try {
                        state = jpaDAOCar.getCarStateByCarId(id);
                    } catch (DAOException e) {
                        mainLogger.error("Get state  of car with this id exception. " + e.getMessage());
                    }
                    System.out.println("State: ");
                    if (state)
                        System.out.println("Available.");
                    else
                        System.out.println("Not available.");
                    break;
                case (5):
                    requestId = Integer.parseInt(tokens[1]);
                    id = Integer.parseInt(tokens[2]);
                    System.out.println("After client payed the bill, delete his request and update car's state.");
                    try {
                        jpaDAOCar.getCarByIdUpdate(id, false);
                    } catch (DAOException e) {
                        mainLogger.error("Process request № id, client must pay the bill " + e.getMessage());
                    }
                    System.out.println("Car is not available");
                    try {
                        jpaDAORequest.remove(requestId);
                    } catch (DAOException e) {
                        mainLogger.error("Process request № id, client must pay the bill " + e.getMessage());
                    }
                    break;
                case (6):
                    id = Integer.parseInt(tokens[1]);
                    try {
                        jpaDAOCar.getCarByIdUpdate(id, true);
                    } catch (DAOException e) {
                        mainLogger.error("Return car № id " + e.getMessage());
                    }
                    break;
                case (7):
                    model = tokens[1];
                    List<Car> cars = jpaDAOCar.getCarByCarStateAndModel(true, model);
                    System.out.println("Available cars: ");
                    for (Car cr : cars) {
                        System.out.println(cr.getCarId() + " " + cr.getCarModel());
                    }
                    break;
                case (8):
                    cars = jpaDAOCar.getAllCars();
                    System.out.println("All cars: ");
                    for (Car cr : cars) {
                        System.out.println(cr.getCarId() + " " + cr.getCarModel() + " " + cr.isCarState());
                    }
                    break;
                case (9):
                    requests = jpaDAORequest.getAllRequests();
                    System.out.println("All requests: ");
                    for (Request req : requests) {
                        System.out.println(req.getId() + " " + req.getModel() + " " + req.getRentalTime() + " " + req.getClient().getIdPassport());
                    }
                    break;
                case (10):
                    flag = false;
                    break;
                default:
                    System.out.println("Wrong enter.");
                    break;
            }
        }
        entityManagerFactory.close();
    }
}
