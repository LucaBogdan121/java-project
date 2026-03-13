package repository;
import domain.Car;

import java.util.ArrayList;
import java.util.List;

public class CarRepository extends MemoryRepository<Car, String> {
    public CarRepository() {
        super();
        addNewEntity(new Car("1", "Audi", "A4", 100));
        addNewEntity(new Car("2", "BMW", "X5", 150));
        addNewEntity(new Car("3", "Ford", "Kuga", 135));
        addNewEntity(new Car("4", "Fiat", "Dublo", 35));
        addNewEntity(new Car("5", "Skoda", "Octavia", 70));
    }

    public void addCar(Car car) {
        if (findById(car.getId()) != null) {
            throw new RuntimeException("Car with ID " + car.getId() + " already exists!");
        }
        addNewEntity(car);
    }

    public void updateCarById(String id, String make, String model, double rentPrice) {
        Car existingCar = findById(id);
        if (existingCar == null) {
            throw new RuntimeException("Car not found with ID: " + id);
        }
        existingCar.setMake(make);
        existingCar.setModel(model);
        existingCar.setRentPrice(rentPrice);
    }

    public void removeCarById(String id) throws Exception {
        Car carToBeRemoved = findById(id);
        if (carToBeRemoved == null) {
            throw new RuntimeException("Car not found with ID: " + id);
        }
        deleteEntityById(id);
    }

    public Car getCarById(String id) {
        return findById(id);
    }

    public List<Car> getAllCars() {
        return findAll();
    }
}
