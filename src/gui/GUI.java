package gui;

import domain.Car;
import domain.Reservation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.*;
import service.CarService;
import service.ReservationService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GUI extends Application {
    public static IRepository<Car, String> createCarRepository() {
        IRepository<Car, String> carRepository = null;

        Properties property = new Properties();
        try {
            property.load(new FileReader("data/settings.properties"));

            String repoType = property.getProperty("RepositoryType");
            if (repoType.equals("memory")) {
                carRepository = new MemoryRepository<>();
                Car mockCarOne = new Car("30","Audi","Q7",350.5);
                Car mockCarTwo = new Car("31","BMW","X5", 150);
                Car mockCarThree = new Car("32","Citroen","1", 60);
                Car mockCarFour = new Car("33","Leon","2", 70);
                Car mockCarFive = new Car("34","VW","Passat", 80);
                carRepository.addNewEntity(mockCarOne);
                carRepository.addNewEntity(mockCarTwo);
                carRepository.addNewEntity(mockCarThree);
                carRepository.addNewEntity(mockCarFour);
                carRepository.addNewEntity(mockCarFive);
            }
            if (repoType.equals("csv")) {
                String repositoryPathForCsv = property.getProperty("Car");
                carRepository = new CarTextFileRepository(repositoryPathForCsv);
            }
            if (repoType.equals("binary")) {
                String repositoryPathForBinary = property.getProperty("Car");
                carRepository = new CarBinaryFileRepository(repositoryPathForBinary);
            }
            if (repoType.equals("database")){
                String databaseUrl= property.getProperty("databaseURL");
                carRepository=new CarDatabaseRepository(databaseUrl);
            }
            if(repoType.equals("json")){
                String repositoryPathForJson = property.getProperty("Car");
                carRepository = new CarJsonRepository(repositoryPathForJson);
            }

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return carRepository;
    }

    public static IRepository<Reservation, String> createReservationRepository() {
        IRepository<Reservation, String> reservationRepository = null;

        Properties property = new Properties();
        try {
            property.load(new FileReader("data/settings.properties"));

            String repoType = property.getProperty("RepositoryType");
            if (repoType.equals("memory")) {
                reservationRepository = new MemoryRepository<>();
            }
            if (repoType.equals("csv")) {
                String repositoryPathForCsv = property.getProperty("Reservation");
                reservationRepository = new ReservationTextFileRepository(repositoryPathForCsv);
            }
            if (repoType.equals("binary")) {
                String repositoryPathForBinary = property.getProperty("Reservation");
                reservationRepository = new ReservationBinaryFileRepository(repositoryPathForBinary);
            }
            if (repoType.equals("database")) {
                String databaseUrl = property.getProperty("databaseURL");
                reservationRepository = new ReservationDatabaseRepository(databaseUrl);
            }
            if (repoType.equals("json")) {
                String repositoryPathForJson = property.getProperty("Reservation");
                reservationRepository = new ReservationJsonRepository(repositoryPathForJson);
            }

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return reservationRepository;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("gui.fxml"));

        IRepository<Car, String> carRepository = createCarRepository();
        IRepository<Reservation, String> reservationRepository = createReservationRepository();
        CarService serviceOfCars= new CarService(carRepository);
        ReservationService serviceOfReservations= new ReservationService(reservationRepository, carRepository);
        AppController appController = new AppController(serviceOfCars, serviceOfReservations);
        fxmlLoader.setController(appController);
        Scene scene= new Scene(fxmlLoader.load());
        stage.setTitle("Car Reservation System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] passedArguments) {
        launch(passedArguments);
    }
}
