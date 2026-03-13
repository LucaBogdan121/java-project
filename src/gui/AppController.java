package gui;
import domain.Car;
import domain.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.CarService;
import service.ReservationService;

import java.util.List;

public class AppController {
    private CarService carService;
    private ReservationService reservationService;

    public AppController(CarService carService){
        this.carService=carService;
    }

    public AppController(CarService carService, ReservationService reservationService){
        this.carService=carService;
        this.reservationService=reservationService;
    }

    @FXML
    private TableView<Car> carTableView;

    @FXML
    private TableColumn<Car, String> carIdColumn;

    @FXML
    private TableColumn<Car,String> carMakeColumn;

    @FXML
    private TableColumn<Car, String> carModelColumn;

    @FXML
    private TableColumn<Car, Double> rentPriceColumn;

    @FXML
    private TextField carIdTextField;

    @FXML
    private TextField carMakeTextFiled;

    @FXML
    private TextField carModelTextField;

    @FXML
    private TextField rentPriceTextFiled;

    @FXML
    private ChoiceBox<String> carActionChoiceBox;

    @FXML
    private Button carExecuteButton;

    @FXML
    private TableView<Reservation> reservationTableView;

    @FXML
    private TableColumn<Reservation, String> reservationIdColumn;

    @FXML
    private TableColumn<Reservation, String> reservationCarIdColumn;

    @FXML
    private TableColumn<Reservation, String> reservationCustomerIdColumn;

    @FXML
    private TableColumn<Reservation, String> reservationStartDateColumn;

    @FXML
    private TableColumn<Reservation, String> reservationEndDateColumn;

    @FXML
    private TextField reservationIdTextField;

    @FXML
    private TextField reservationCarIdTextField;

    @FXML
    private TextField reservationCustomerIdTextField;

    @FXML
    private TextField reservationStartDateTextField;

    @FXML
    private TextField reservationEndDateTextField;

    @FXML
    private ChoiceBox<String> reservationActionChoiceBox;

    @FXML
    private Button reservationExecuteButton;

    public void initialize(){
        this.setCarColumns();
        this.populateCarTable();
        this.setupCarActionChoiceBox();

        if (reservationService != null) {
            this.setReservationColumns();
            this.populateReservationTable();
            this.setupReservationActionChoiceBox();
        }
    }

    private void setCarColumns() {
        carIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        carMakeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        carModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        rentPriceColumn.setCellValueFactory(new PropertyValueFactory<>("rentPrice"));
    }

    private void populateCarTable(){
        List<Car> cars= this.carService.displayAllCars();
        carTableView.getItems().setAll(cars);
    }

    private void setupCarActionChoiceBox() {
        carActionChoiceBox.getItems().addAll(
                "Update Car",
                "Find Car by ID",
                "Display All Cars",
                "Cars Sorted by Price",
                "Cars by Make (Desc by ID)"
        );
    }

    private void setReservationColumns() {
        reservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        reservationCarIdColumn.setCellValueFactory(new PropertyValueFactory<>("carId"));
        reservationCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        reservationStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("reservationStartDate"));
        reservationEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("reservationEndDate"));
    }

    private void populateReservationTable(){
        if (reservationService != null) {
            List<Reservation> reservations = this.reservationService.displayAllReservations();
            reservationTableView.getItems().setAll(reservations);
        }
    }

    private void setupReservationActionChoiceBox() {
        reservationActionChoiceBox.getItems().addAll(
                "Update Reservation",
                "Find Reservation by ID",
                "Display All Reservations",
                "Reservations by Customer",
                "Reservations by Car",
                "Most Popular Car"
        );
    }


    @FXML
    void addCarButtonHandler(ActionEvent event) {
        String carId = carIdTextField.getText();
        String carMake = carMakeTextFiled.getText();
        String carModel = carModelTextField.getText();

        try {
            double rentPrice = Double.parseDouble(rentPriceTextFiled.getText());
            this.carService.addCar(carId, carMake, carModel, rentPrice);
            this.populateCarTable();
            clearCarFields();
            showInfo("Success", "Car added successfully!");
        } catch (NumberFormatException e) {
            showError("Invalid price format!");
        } catch (Exception exception) {
            showError(exception.getMessage());
        }
    }

    @FXML
    void deleteCarButtonHandler(ActionEvent event) {
        Car carToBeDeleted = this.carTableView.getSelectionModel().getSelectedItem();
        if (carToBeDeleted == null) {
            showError("Please select a car to delete!");
            return;
        }
        try {
            this.carService.removeCar(carToBeDeleted.getId());
            this.populateCarTable();
            showInfo("Success", "Car deleted successfully!");
        } catch (Exception exception) {
            showError(exception.getMessage());
        }
    }

    @FXML
    void executeCarActionHandler(ActionEvent event) {
        String selectedAction = carActionChoiceBox.getValue();
        if (selectedAction == null) {
            showError("Please select an action!");
            return;
        }

        try {
            switch (selectedAction) {
                case "Update Car":
                    updateCar();
                    break;
                case "Find Car by ID":
                    findCarById();
                    break;
                case "Display All Cars":
                    populateCarTable();
                    showInfo("Success", "All cars displayed!");
                    break;
                case "Cars Sorted by Price":
                    displayCarsSortedByPrice();
                    break;
                case "Cars by Make (Desc by ID)":
                    displayCarsByMake();
                    break;
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void updateCar() {
        String carId = carIdTextField.getText();
        String carMake = carMakeTextFiled.getText();
        String carModel = carModelTextField.getText();
        double rentPrice = Double.parseDouble(rentPriceTextFiled.getText());

        this.carService.updateCar(carId, carMake, carModel, rentPrice);
        this.populateCarTable();
        clearCarFields();
        showInfo("Success", "Car updated successfully!");
    }

    private void findCarById() {
        String carId = carIdTextField.getText();
        if (carId.isEmpty()) {
            showError("Please enter a car ID!");
            return;
        }

        Car car = this.carService.findCar(carId);
        if (car == null) {
            showError("Car not found!");
        } else {
            carTableView.getItems().setAll(car);
            showInfo("Found", "Car found: " + car.getMake() + " " + car.getModel());
        }
    }

    private void displayCarsSortedByPrice() {
        List<Car> sortedCars = this.carService.getCarsSortedByPrice();
        carTableView.getItems().setAll(sortedCars);
        showInfo("Success", "Cars sorted by price!");
    }

    private void displayCarsByMake() {
        String make = carMakeTextFiled.getText();
        if (make.isEmpty()) {
            showError("Please enter a car make!");
            return;
        }

        List<Car> filteredCars = this.carService.getCarsByMakeSortedDescendingById(make);
        carTableView.getItems().setAll(filteredCars);
        showInfo("Success", "Cars filtered by make: " + make);
    }

    private void clearCarFields() {
        carIdTextField.clear();
        carMakeTextFiled.clear();
        carModelTextField.clear();
        rentPriceTextFiled.clear();
    }

    @FXML
    void addReservationButtonHandler(ActionEvent event) {
        if (reservationService == null) {
            showError("Reservation service not available!");
            return;
        }

        String reservationId = reservationIdTextField.getText();
        String carId = reservationCarIdTextField.getText();
        String customerId = reservationCustomerIdTextField.getText();
        String startDate = reservationStartDateTextField.getText();
        String endDate = reservationEndDateTextField.getText();

        try {
            this.reservationService.addReservation(reservationId, carId, customerId, startDate, endDate);
            this.populateReservationTable();
            clearReservationFields();
            showInfo("Success", "Reservation added successfully!");
        } catch (Exception exception) {
            showError(exception.getMessage());
        }
    }

    @FXML
    void deleteReservationButtonHandler(ActionEvent event) {
        if (reservationService == null) {
            showError("Reservation service not available!");
            return;
        }

        Reservation reservationToBeDeleted = this.reservationTableView.getSelectionModel().getSelectedItem();
        if (reservationToBeDeleted == null) {
            showError("Please select a reservation to delete!");
            return;
        }

        try {
            this.reservationService.removeReservationById(reservationToBeDeleted.getReservationId());
            this.populateReservationTable();
            showInfo("Success", "Reservation deleted successfully!");
        } catch (Exception exception) {
            showError(exception.getMessage());
        }
    }

    @FXML
    void executeReservationActionHandler(ActionEvent event) {
        if (reservationService == null) {
            showError("Reservation service not available!");
            return;
        }

        String selectedAction = reservationActionChoiceBox.getValue();
        if (selectedAction == null) {
            showError("Please select an action!");
            return;
        }

        try {
            switch (selectedAction) {
                case "Update Reservation":
                    updateReservation();
                    break;
                case "Find Reservation by ID":
                    findReservationById();
                    break;
                case "Display All Reservations":
                    populateReservationTable();
                    showInfo("Success", "All reservations displayed!");
                    break;
                case "Reservations by Customer":
                    displayReservationsByCustomer();
                    break;
                case "Reservations by Car":
                    displayReservationsByCar();
                    break;
                case "Most Popular Car":
                    displayMostPopularCar();
                    break;
            }
        } catch (Exception exception) {
            showError(exception.getMessage());
        }
    }

    private void updateReservation() {
        String reservationId = reservationIdTextField.getText();
        String carId = reservationCarIdTextField.getText();
        String customerId = reservationCustomerIdTextField.getText();
        String startDate = reservationStartDateTextField.getText();
        String endDate = reservationEndDateColumn.getText();

        this.reservationService.updateReservation(reservationId, carId, customerId, startDate, endDate);
        this.populateReservationTable();
        clearReservationFields();
        showInfo("Success", "Reservation updated successfully!");
    }

    private void findReservationById() {
        String resId = reservationIdTextField.getText();
        if (resId.isEmpty()) {
            showError("Please enter a reservation ID!");
            return;
        }

        Reservation reservation = this.reservationService.findReservationById(resId);
        if (reservation == null) {
            showError("Reservation not found!");
        } else {
            reservationTableView.getItems().setAll(reservation);
            showInfo("Found", "Reservation found!");
        }
    }

    private void displayReservationsByCustomer() {
        String customerId = reservationCustomerIdTextField.getText();
        if (customerId.isEmpty()) {
            showError("Please enter a customer ID!");
            return;
        }

        List<Reservation> reservations = this.reservationService.displayReservationsByCustomer(customerId);
        reservationTableView.getItems().setAll(reservations);
        showInfo("Success", "Reservations filtered by customer: " + customerId);
    }

    private void displayReservationsByCar() {
        String carId = reservationCarIdTextField.getText();
        if (carId.isEmpty()) {
            showError("Please enter a car ID!");
            return;
        }

        List<Reservation> reservations = this.reservationService.displayReservationsByCar(carId);
        reservationTableView.getItems().setAll(reservations);
        showInfo("Success", "Reservations filtered by car: " + carId);
    }

    private void displayMostPopularCar() {
        String mostPopularCarId = this.reservationService.getMostPopularCarId();
        showInfo("Most Popular Car", "Car ID: " + mostPopularCarId);
    }

    private void clearReservationFields() {
        reservationIdTextField.clear();
        reservationCarIdTextField.clear();
        reservationCustomerIdTextField.clear();
        reservationStartDateTextField.clear();
        reservationEndDateTextField.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}