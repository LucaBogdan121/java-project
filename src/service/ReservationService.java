package service;

import domain.Car;
import repository.CarRepository;
import domain.Reservation;
import filters.AbstractFilter;
import filters.FilteredRepository;
import repository.IRepository;
import repository.MemoryRepository;
import repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationService {
    private IRepository<Reservation,String> reservationRepository;
    private IRepository<Car,String> carRepository;
    public ReservationService(IRepository<Reservation,String> reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationService(IRepository<Reservation,String> reservationRepository, IRepository<Car,String> carRepository) {
        this.reservationRepository = reservationRepository;
        this.carRepository=carRepository;
    }

    public void addReservation(String reservationId, String carId, String customerId, String startDate, String endDate) {
        if ((reservationId == null) || (carId == null) || (customerId == null) || (startDate == null) || (endDate == null)) {
            throw new IllegalArgumentException("Reservation Id, Car Id, Customer Id, Start date or End Date can't be null");
        }
        try {
            Reservation newReservation = new Reservation(reservationId, carId, customerId, startDate, endDate);
            reservationRepository.addNewEntity(newReservation);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Reservation with ID " + reservationId + " already exists!");
        }
    }

    public void updateReservation(String reservationId, String carId, String customerId, String startDate, String endDate){
        if ((reservationId == null) || (carId == null) || (customerId == null) || (startDate == null) || (endDate == null)) {
            throw new IllegalArgumentException("Reservation Id, Car Id, Customer Id, Start date or End Date can't be null");
        }
        try {
            Reservation newReservation = new Reservation(reservationId, carId, customerId, startDate, endDate);
            reservationRepository.updateEntity(newReservation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void removeReservationById(String reservationId){
        if (reservationId == null ) {
            throw new IllegalArgumentException("Reservation ID cannot be null or empty!");
        }
        try{
            reservationRepository.deleteEntityById(reservationId);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Reservation findReservationById(String reservationId){
        if (reservationId == null) {
            throw new IllegalArgumentException("Reservation ID cannot be null or empty!");
        }
        Reservation foundReservation= null;
        try{
            foundReservation=reservationRepository.findById(reservationId);
            if(foundReservation==null){
                throw new IllegalStateException("Reservation with ID " + reservationId + " does not exist!");
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return foundReservation;
    }

    public List<Reservation> displayAllReservations(){
        return reservationRepository.findAll();
    }

    public List<Reservation> displayReservationsByCustomer(String customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty!");
        }

        return reservationRepository.findAll().stream().filter(reservation -> reservation.getCustomerId().equals(customerId)).toList();
    }

    public List<Reservation> displayReservationsByCar(String carId) {
        if (carId == null) {
            throw new IllegalArgumentException("Car ID cannot be null or empty!");
        }

        return reservationRepository.findAll().stream().filter(reservation -> reservation.getCarId().equals(carId)).toList();

    }

    public List<Reservation> displayFilteredReservations(AbstractFilter<Reservation> filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Filter cannot be null!");
        }
        FilteredRepository<String, Reservation> filteredRepo = new FilteredRepository<>(reservationRepository, filter);
        return filteredRepo.findAll();

    }

    /// Report no. 3
    public List<String> getCustomerIdByCar(String carId){
        return reservationRepository.findAll().stream()
                .filter(reservationToFilter -> reservationToFilter.getCarId().equals(carId))
                .map(Reservation::getCustomerId)
                .distinct()
                .collect(Collectors.toList());
    }

    /// Report no. 4
    public List<Car> getCarRentedByCustomers (String customerId){
        List<String> carIds = reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getCustomerId().equals(customerId))
                .map(Reservation::getCarId)
                .distinct()
                .collect(Collectors.toList());

        return carIds.stream()
                .map(carRepository::findById)
                .collect(Collectors.toList());
    }
    /// Report no. 4
    public String getMostPopularCarId() {
        return reservationRepository.findAll().stream()
                .collect(Collectors.groupingBy(Reservation::getCarId, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No reservations found");
    }

    // Report no. 5
    public List<Reservation> getReservationsBetweenDates(String startDate, String endDate) {
        return reservationRepository.findAll().stream()
                .filter(reservationToBeFiltered -> reservationToBeFiltered.getReservationStartDate().compareTo(startDate) >= 0 &&
                        reservationToBeFiltered.getReservationEndDate().compareTo(endDate) <= 0)
                .collect(Collectors.toList());
    }
}
