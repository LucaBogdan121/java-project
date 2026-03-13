package UI;
import service.CarService;
import service.ReservationService;
import filters.*;
import domain.Car;
import domain.Reservation;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class ui {
    final int EXIT=0;
    final int ADD_CAR=1;
    final int UPDATE_CAR=2;
    final int DELETE_CAR=3;
    final int FIND_CAR=4;
    final int DISPLAY_ALL_CARS=5;
    final int FILTER_CARS_BY_MAKE=6;
    final int FILTER_CARS_BY_MAX_PRICE=7;
    final int ADD_RESERVATION=8;
    final int UPDATE_RESERVATION=9;
    final int DELETE_RESERVATION=10;
    final int FIND_RESERVATION=11;
    final int DISPLAY_ALL_RESERVATIONS=12;
    final int FILTER_RESERVATIONS_BY_CUSTOMER=13;
    final int FILTER_RESERVATIONS_BY_CAR=14;

    final int REPORT_CUSTOMERS_BY_CAR = 15;
    final int REPORT_CARS_BY_CUSTOMER = 16;
    final int REPORT_CARS_SORTED_BY_PRICE = 17;
    final int REPORT_MOST_POPULAR_CAR = 18;
    final int REPORT_RESERVATIONS_DATE_RANGE = 19;

    private CarService carService;
    private ReservationService reservationService;
    private Scanner scanner;

    public ui(CarService carService, ReservationService reservationService) {
        this.carService = carService;
        this.reservationService = reservationService;
        this.scanner = new Scanner(System.in);
    }

    private void printMenu() {
        System.out.println("\n=== Car Rental System Menu ===");
        System.out.println("CAR OPERATIONS:");
        System.out.println( ADD_CAR + " Add a new car");
        System.out.println( UPDATE_CAR + " Update a car by id");
        System.out.println( DELETE_CAR + " Delete a car by id");
        System.out.println( FIND_CAR+ " Find car by id");
        System.out.println(DISPLAY_ALL_CARS+ " View all cars");
        System.out.println(FILTER_CARS_BY_MAKE + " Filter cars by make");
        System.out.println(FILTER_CARS_BY_MAX_PRICE + " Filter cars by max price");
        System.out.println("\nRESERVATION OPERATIONS:");
        System.out.println(ADD_RESERVATION + " Add a new reservation");
        System.out.println( UPDATE_RESERVATION + " Update a reservation");
        System.out.println(DELETE_RESERVATION + " Delete a reservation by id");
        System.out.println(FIND_RESERVATION + " Find reservation by id");
        System.out.println(DISPLAY_ALL_RESERVATIONS + " View all reservations");
        System.out.println(FILTER_RESERVATIONS_BY_CUSTOMER + " Filter reservations by customer");
        System.out.println(FILTER_RESERVATIONS_BY_CAR + " Filter reservations by car");
        System.out.println(REPORT_CUSTOMERS_BY_CAR + " Report: Customers who booked a specific car");
        System.out.println(REPORT_CARS_BY_CUSTOMER + " Report: Cars rented by a specific customer");
        System.out.println(REPORT_CARS_SORTED_BY_PRICE + " Report: All cars sorted by price");
        System.out.println(REPORT_MOST_POPULAR_CAR + " Report: Most popular car");
        System.out.println(REPORT_RESERVATIONS_DATE_RANGE + " Report: Reservations within date range");
        System.out.println(EXIT + " Exit");
    }

    private void addCar() {
        try {
            System.out.println("ID: ");
            String id = scanner.nextLine();
            System.out.println("Make: ");
            String make = scanner.nextLine();
            System.out.println("Model: ");
            String model = scanner.nextLine();
            System.out.println("Renting price: ");
            double rentPrice = scanner.nextDouble();
            scanner.nextLine();
            this.carService.addCar(id, make, model, rentPrice);
            System.out.println("Car added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding car: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void updateCar() {
        try {
            System.out.println("ID: ");
            String id = scanner.nextLine();
            System.out.println("Make: ");
            String make = scanner.nextLine();
            System.out.println("Model: ");
            String model = scanner.nextLine();
            System.out.println("Renting price: ");
            double rentPrice = scanner.nextDouble();
            scanner.nextLine();
            this.carService.updateCar(id, make, model, rentPrice);
            System.out.println("Car updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating car: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void deleteCar() {
        try {
            System.out.println("ID:");
            String id = scanner.nextLine();
            this.carService.removeCar(id);
            System.out.println("Car deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting car: " + e.getMessage());
        }
    }

    private void findCar() {
        try {
            System.out.println("ID:");
            String id = scanner.nextLine();
            var car = this.carService.findCar(id);
            if (car == null) {
                System.out.println("Car not found!");
            } else {
                System.out.println(car);
            }
        } catch (Exception e) {
            System.out.println("Error finding car: " + e.getMessage());
        }
    }

    private void displayAllCars() {
        try {
            List<Car> cars = this.carService.displayAllCars();
            if (cars.isEmpty()) {
                System.out.println("No cars available.");
            } else {
                System.out.println("\n=== All Cars ===");
                for (Car car : cars) {
                    System.out.println(car);
                }
            }
        } catch (Exception e) {
            System.out.println("Error displaying cars: " + e.getMessage());
        }
    }

    private void filterCarsByMake() {
        try {
            System.out.println("Enter car make: ");
            String make = scanner.nextLine();
            CarMakeFilter filter = new CarMakeFilter(make);
            System.out.println("\n=== Cars with make: " + make + " ===");
            this.carService.displayFilteredCars(filter);
        } catch (Exception e) {
            System.out.println("Error filtering cars: " + e.getMessage());
        }
    }

    private void filterCarsByMaxPrice() {
        try {
            System.out.println("Enter maximum price: ");
            double maxPrice = scanner.nextDouble();
            scanner.nextLine();
            CarMaxPriceFilter filter = new CarMaxPriceFilter(maxPrice);
            System.out.println("\n=== Cars with price <= " + maxPrice + " ===");
            this.carService.displayFilteredCars(filter);
        } catch (Exception e) {
            System.out.println("Error filtering cars: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void addReservation() {
        try {
            System.out.println("Reservation ID: ");
            String reservationId = scanner.nextLine();
            System.out.println("Car ID: ");
            String carId = scanner.nextLine();
            System.out.println("Customer ID: ");
            String customerId = scanner.nextLine();
            System.out.println("Start Date (YYYY-MM-DD): ");
            String startDate = scanner.nextLine();
            System.out.println("End Date (YYYY-MM-DD): ");
            String endDate = scanner.nextLine();
            this.reservationService.addReservation(reservationId, carId, customerId, startDate, endDate);
            System.out.println("Reservation added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding reservation: " + e.getMessage());
        }
    }

    private void updateReservation() {
        try {
            System.out.println("Reservation ID: ");
            String reservationId = scanner.nextLine();
            System.out.println("Car ID: ");
            String carId = scanner.nextLine();
            System.out.println("Customer ID: ");
            String customerId = scanner.nextLine();
            System.out.println("Start Date (YYYY-MM-DD): ");
            String startDate = scanner.nextLine();
            System.out.println("End Date (YYYY-MM-DD): ");
            String endDate = scanner.nextLine();
            this.reservationService.updateReservation(reservationId, carId, customerId, startDate, endDate);
            System.out.println("Reservation updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating reservation: " + e.getMessage());
        }
    }

    private void deleteReservation() {
        try {
            System.out.println("Reservation ID:");
            String reservationId = scanner.nextLine();
            this.reservationService.removeReservationById(reservationId);
            System.out.println("Reservation deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting reservation: " + e.getMessage());
        }
    }

    private void findReservation() {
        try {
            System.out.println("Reservation ID:");
            String reservationId = scanner.nextLine();
            this.reservationService.findReservationById(reservationId);
        } catch (Exception e) {
            System.out.println("Error finding reservation: " + e.getMessage());
        }
    }

    private void displayAllReservations() {
        try {
            List<Reservation> reservations = this.reservationService.displayAllReservations();
            if (reservations.isEmpty()) {
                System.out.println("No reservations available.");
            } else {
                System.out.println("\n=== All Reservations ===");
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }
            }
        } catch (Exception e) {
            System.out.println("Error displaying reservations: " + e.getMessage());
        }
    }


    private void filterReservationsByCustomer() {
        try {
            System.out.println("Enter customer ID: ");
            String customerId = scanner.nextLine();
            ReservationByCustomerFilter filter = new ReservationByCustomerFilter(customerId);
            System.out.println("\n=== Reservations for customer: " + customerId + " ===");
            this.reservationService.displayFilteredReservations(filter);
        } catch (Exception e) {
            System.out.println("Error filtering reservations: " + e.getMessage());
        }
    }

    private void filterReservationsByCar() {
        try {
            System.out.println("Enter car ID: ");
            String carId = scanner.nextLine();
            ReservationByCarFilter filter = new ReservationByCarFilter(carId);
            System.out.println("\n=== Reservations for car: " + carId + " ===");
            this.reservationService.displayFilteredReservations(filter);
        } catch (Exception e) {
            System.out.println("Error filtering reservations: " + e.getMessage());
        }
    }

    private void reportCustomersByCar() {
        try {
            System.out.println("Enter Car ID to view customers: ");
            String carId = scanner.nextLine();
            List<String> customers = this.reservationService.getCustomerIdByCar(carId);
            if (customers.isEmpty()) {
                System.out.println("No customers found for this car.");
            } else {
                System.out.println("\n=== Customers who booked Car " + carId + " ===");
                customers.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }

    private void reportCarsByCustomer() {
        try {
            System.out.println("Enter Customer ID to view rented cars: ");
            String customerId = scanner.nextLine();
            List<Car> cars = this.reservationService.getCarRentedByCustomers(customerId);
            if (cars.isEmpty()) {
                System.out.println("No cars found for this customer.");
            } else {
                System.out.println("\n=== Cars rented by Customer " + customerId + " ===");
                cars.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }

    private void reportCarsSortedByPrice() {
        try {
            List<Car> cars = this.carService.getCarsSortedByPrice();
            if (cars.isEmpty()) {
                System.out.println("No cars available.");
            } else {
                System.out.println("\n=== Cars Sorted by Price ===");
                cars.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }

    private void reportMostPopularCar() {
        try {
            String carId = this.reservationService.getMostPopularCarId();
            System.out.println("\n=== Most Popular Car ===");
            System.out.println("Car ID with most reservations: " + carId);
        } catch (Exception e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }

    private void reportReservationsDateRange() {
        try {
            System.out.println("Enter Start Date (YYYY-MM-DD): ");
            String startDate = scanner.nextLine();
            System.out.println("Enter End Date (YYYY-MM-DD): ");
            String endDate = scanner.nextLine();

            List<Reservation> reservations = this.reservationService.getReservationsBetweenDates(startDate, endDate);
            if (reservations.isEmpty()) {
                System.out.println("No reservations found in this date range.");
            } else {
                System.out.println("\n=== Reservations between " + startDate + " and " + endDate + " ===");
                reservations.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }

    public void run() {
        System.out.println("Welcome to Car Rental System!");
        while (true) {
            this.printMenu();
            try {
                System.out.print("Input option: ");
                int option = scanner.nextInt();
                scanner.nextLine();

                if (option == 0) {
                    System.out.println("Goodbye!");
                    scanner.close();
                    break;
                }

                switch (option) {
                    case ADD_CAR->addCar();
                    case UPDATE_CAR->updateCar();
                    case DELETE_CAR->deleteCar();
                    case FIND_CAR->findCar();
                    case DISPLAY_ALL_CARS->displayAllCars();
                    case FILTER_CARS_BY_MAKE->filterCarsByMake();
                    case FILTER_CARS_BY_MAX_PRICE->filterCarsByMaxPrice();
                    case ADD_RESERVATION->addReservation();
                    case UPDATE_RESERVATION->updateReservation();
                    case DELETE_RESERVATION->deleteReservation();
                    case FIND_RESERVATION->findReservation();
                    case DISPLAY_ALL_RESERVATIONS->displayAllReservations();
                    case FILTER_RESERVATIONS_BY_CUSTOMER->filterReservationsByCustomer();
                    case FILTER_RESERVATIONS_BY_CAR->filterReservationsByCar();
                    case REPORT_CUSTOMERS_BY_CAR -> reportCustomersByCar();
                    case REPORT_CARS_BY_CUSTOMER -> reportCarsByCustomer();
                    case REPORT_CARS_SORTED_BY_PRICE -> reportCarsSortedByPrice();
                    case REPORT_MOST_POPULAR_CAR -> reportMostPopularCar();
                    case REPORT_RESERVATIONS_DATE_RANGE -> reportReservationsDateRange();
                    default->System.out.println("Invalid option! Please choose 0-14.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}