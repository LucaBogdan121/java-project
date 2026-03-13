package service;
import domain.Car;
import filters.AbstractFilter;
import filters.CarMakeFilter;
import filters.FilteredRepository;
import repository.CarRepository;
import repository.IRepository;
import repository.MemoryRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CarService {
    private IRepository<Car, String> carRepository;

    public CarService(IRepository<Car, String> carRepository) {
        this.carRepository = carRepository;
    }

    public void addCar(String id, String make, String model, double rentPrice) {
        Car car = new Car(id, make, model, rentPrice);
        carRepository.addNewEntity(car);
    }

    public void updateCar(String id, String make, String model, double rentPrice) {
        Car car = new Car(id, make, model, rentPrice);
        try {
            carRepository.updateEntity(car);
        } catch (Exception e) {
            throw new RuntimeException("Error updating car: " + e.getMessage());
        }
    }

    public void removeCar(String id) {
        try {
            carRepository.deleteEntityById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting car: " + e.getMessage());
        }
    }

    public Car findCar(String id) {
        return carRepository.findById(id);
    }

    public List<Car> displayAllCars() {
        return carRepository.findAll();
    }

    public List<Car> displayFilteredCars(AbstractFilter<Car> filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Filter cannot be null!");
        }
        FilteredRepository<String, Car> filteredRepo = new FilteredRepository<>(carRepository, filter);
        return filteredRepo.findAll();
    }

    /// Report no. 1
    public List<Car> getCarsSortedByPrice(){
        return carRepository.findAll().stream()
                .sorted(Comparator.comparing(Car::getRentPrice))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /// Report no. 2
    public List<Car> getCarsByMakeSortedDescendingById(String  desiredMake){
        return carRepository.findAll().stream()
                .filter(carToFilter -> {
                    if(carToFilter.getMake().equals(desiredMake))
                        return true;
                    return false;
                } )
                .sorted(Comparator.comparing(Car::getId).reversed())
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
