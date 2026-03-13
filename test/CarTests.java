
import domain.Car;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class CarTests {
    @Test
    void testConstructorAndGetters() {
        Car carToTest = new Car("C1", "Toyota", "Corolla", 35.5);

        assertEquals("C1", carToTest.getId());
        assertEquals("Toyota", carToTest.getMake());
        assertEquals("Corolla", carToTest.getModel());
        assertEquals(35.5, carToTest.getRentPrice());
    }

    @Test
    void testSetters() {
        Car carToTest = new Car("C1", "Toyota", "Corolla", 35.5);

        carToTest.setId("C2");
        carToTest.setMake("Honda");
        carToTest.setModel("Civic");
        carToTest.setRentPrice(40.0);

        assertEquals("C2", carToTest.getId());
        assertEquals("Honda", carToTest.getMake());
        assertEquals("Civic", carToTest.getModel());
        assertEquals(40.0, carToTest.getRentPrice());
    }

    @Test
    void testToStringFormat() {
        Car carToTest = new Car("C1", "Toyota", "Corolla", 35.5);
        assertEquals("C1,Toyota,Corolla,35.5", carToTest.toString());
    }

    @Test
    void testEqualsSameObject() {
        Car carToTest = new Car("C1", "Toyota", "Corolla", 35.5);
        assertEquals(carToTest, carToTest);
    }

    @Test
    void testEqualsIdenticalValues() {
        Car firstIdenticCar = new Car("C1", "Toyota", "Corolla", 35.5);
        Car secondIdenticCar = new Car("C1", "Toyota", "Corolla", 35.5);

        assertEquals(firstIdenticCar, secondIdenticCar);
        assertEquals(firstIdenticCar.hashCode(), secondIdenticCar.hashCode());
    }

    @Test
    void testEqualsDifferentId() {
        Car differentCarToTest = new Car("C1", "Toyota", "Corolla", 35.5);
        Car secondDifferentCar = new Car("C2", "Toyota", "Corolla", 35.5);

        assertNotEquals(differentCarToTest, secondDifferentCar);
    }

    @Test
    void testEqualsDifferentMake() {
        Car firstEqualCar = new Car("C1", "Toyota", "Corolla", 35.5);
        Car secondEqualCar = new Car("C1", "Honda", "Corolla", 35.5);

        assertNotEquals(firstEqualCar, secondEqualCar);
    }

    @Test
    void testEqualsDifferentModel() {
        Car differentModelCar = new Car("C1", "Toyota", "Corolla", 35.5);
        Car secondDifferentModelCar = new Car("C1", "Toyota", "Camry", 35.5);

        assertNotEquals(differentModelCar, secondDifferentModelCar);
    }

    @Test
    void testEqualsDifferentPrice() {
        Car differentPriceCar = new Car("C1", "Toyota", "Corolla", 35.5);
        Car secondDifferentPriceCar = new Car("C1", "Toyota", "Corolla", 36.0);

        assertNotEquals(differentPriceCar, secondDifferentPriceCar);
    }

    @Test
    void testEqualsWithNull() {
        Car carToTest = new Car("C1", "Toyota", "Corolla", 35.5);
        assertNotEquals(carToTest, null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        Car carToTest = new Car("C1", "Toyota", "Corolla", 35.5);
        assertNotEquals(carToTest, "not a car");
    }
}
