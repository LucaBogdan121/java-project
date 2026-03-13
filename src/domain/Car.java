package domain;
import java.io.Serializable;
import java.util.Objects;
public class Car implements domain.Identifiable<String>, Serializable {
    private String id;
    private String make;
    private String model;
    private double rentPrice;

    public Car(String id, String make, String model, double rentPrice) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.rentPrice = rentPrice;
    }

    public String getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return id +','+ make+','+ model+','+ rentPrice;
    }

    @Override
    public boolean equals(Object potentialCar) {
        if ( potentialCar == null || getClass() != potentialCar .getClass()) return false;
        Car carToBeComparedWith = (Car) potentialCar;
        return Double.compare(rentPrice, carToBeComparedWith.rentPrice) == 0 && Objects.equals(id, carToBeComparedWith.id) && Objects.equals(make, carToBeComparedWith.make) && Objects.equals(model, carToBeComparedWith.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, make, model, rentPrice);
    }
}
