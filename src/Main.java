import domain.Car;
import domain.Reservation;
import repository.*;
import service.CarService;
import UI.ui;
import service.ReservationService;
import repository.ReservationDatabaseRepository;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
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
                String path = property.getProperty("Car");
                carRepository = new CarJsonRepository(path);
            }

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return carRepository;
    }

    public static IRepository<Reservation,String> createReservationRepository() {
        IRepository<Reservation, String> reservationRepository = null;

        Properties property = new Properties();
        try {
            property.load(new FileReader("data/settings.properties"));

            String repoType = property.getProperty("RepositoryType");
            if (repoType.equals("memory")) {
                reservationRepository = new MemoryRepository<>();
                Reservation mockReservation1 = new Reservation("30","40","30","2025-11-12","2025-11-13");
                Reservation mockReservation2 = new Reservation("31","41","31","2025-06-12","2025-06-13");
                Reservation mockReservation3 = new Reservation("32","42","32","2025-07-12","2025-07-13");
                Reservation mockReservation4 = new Reservation("33","43","33","2025-08-12","2025-08-13");
                Reservation mockReservation5 = new Reservation("34","44","34","2025-09-12","2025-09-13");
                reservationRepository.addNewEntity(mockReservation1);
                reservationRepository.addNewEntity(mockReservation2);
                reservationRepository.addNewEntity(mockReservation3);
                reservationRepository.addNewEntity(mockReservation4);
                reservationRepository.addNewEntity(mockReservation5);
            }
            if (repoType.equals("csv")) {
                String repositoryPathForCsv = property.getProperty("Reservation");
                reservationRepository = new ReservationTextFileRepository(repositoryPathForCsv);
            }
            if (repoType.equals("binary")) {
                String repositoryPathForBinary = property.getProperty("Reservation");
                reservationRepository = new ReservationBinaryFileRepository(repositoryPathForBinary);
            }
            if(repoType.equals("database")){
                String databaseUrl= property.getProperty("databaseURL");
                reservationRepository= new ReservationDatabaseRepository(databaseUrl);
            }
            if(repoType.equals("json")){
                String path = property.getProperty("Reservation");
                reservationRepository = new ReservationJsonRepository(path);
            }

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return reservationRepository;
    }

    public static void main(String[] passedArguments) {
//        List<Car> listOfCars= new ArrayList<>();
        List<Reservation> listOfReservations= new ArrayList<>();
//        CarRepository repositoryOfCars= new CarRepository();
        ReservationRepository repositoryOfReservations= new ReservationRepository(listOfReservations);
        IRepository<Car, String> carRepository = createCarRepository();
        IRepository<Reservation, String> reservationRepository = createReservationRepository();
        CarService serviceOfCars= new CarService(carRepository);
        ReservationService serviceOfReservations= new ReservationService(reservationRepository);
        ui ui = new ui(serviceOfCars, serviceOfReservations);
        ui.run();
    }
}