package repository;

import domain.Car;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testInitialCarsLoadedCorrectly() {
        List<Car> allCars = carRepository.getAllCars();
        assertEquals(5, allCars.size());
        assertEquals("Audi", carRepository.getCarById("1").getMake());
        assertEquals("BMW", carRepository.getCarById("2").getMake());
    }

    @Test
    void testAddCarSuccess() {
        Car newCar = new Car("6", "Toyota", "Corolla", 90.0);
        carRepository.addCar(newCar);
        assertEquals(6, carRepository.getAllCars().size());
        assertEquals("Toyota", carRepository.getCarById("6").getMake());
    }

    @Test
    void testAddCarDuplicateThrows() {
        Car duplicateCar = new Car("1", "Audi", "A5", 120.0);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> carRepository.addCar(duplicateCar));
        assertTrue(exception.getMessage().contains("already exists"));
    }

    @Test
    void testUpdateCarSuccess() {
        carRepository.updateCarById("1", "Audi", "A6", 200.0);
        Car updatedCar = carRepository.getCarById("1");
        assertEquals("A6", updatedCar.getModel());
        assertEquals(200.0, updatedCar.getRentPrice());
    }

    @Test
    void testUpdateCarNotFoundThrows() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () ->
                carRepository.updateCarById("999", "Tesla", "S", 500.0));
        assertTrue(runtimeException.getMessage().contains("not found"));
    }

    @Test
    void testRemoveCarSuccess() throws Exception {
        carRepository.removeCarById("1");
        assertNull(carRepository.getCarById("1"));
        assertEquals(4, carRepository.getAllCars().size());
    }

    @Test
    void testRemoveCarNotFoundThrows() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () ->
                carRepository.removeCarById("999"));
        assertTrue(runtimeException.getMessage().contains("not found"));
    }

    @Test
    void testGetCarByIdReturnsNullForMissing() {
        assertNull(carRepository.getCarById("999"));
    }

    @Test
    void testMemoryRepositoryMethodsDirectly() throws Exception {
        Car newCar = new Car("10", "Lexus", "RX", 180);
        carRepository.addNewEntity(newCar);
        assertEquals(newCar, carRepository.findById("10"));

        List<Car> allCars = carRepository.findAll();
        assertTrue(allCars.contains(newCar));

        Car updated = new Car("10", "Lexus", "NX", 190);
        carRepository.updateEntity(updated);
        assertEquals("NX", carRepository.findById("10").getModel());

        carRepository.deleteEntityById("10");
        assertNull(carRepository.findById("10"));
    }

    @Test
    void testAddNullEntityReturnsNull() {
        assertNull(carRepository.addNewEntity(null));
    }

    @Test
    void testUpdateNullEntityReturnsNull() throws Exception {
        assertNull(carRepository.updateEntity(null));
    }

    @Test
    void testAddExistingEntityThrows() {
        Car existingCar = new Car("1", "Audi", "A4", 100);
        assertThrows(IllegalStateException.class, () -> carRepository.addNewEntity(existingCar));
    }

    @Test
    void testUpdateNonexistentEntityThrows() {
        Car nonExistingCar = new Car("999", "Ghost", "Car", 0);
        assertThrows(IllegalStateException.class, () -> carRepository.updateEntity(nonExistingCar));
    }

    @Test
    void testDeleteNonexistentEntityThrows() {
        assertThrows(IllegalStateException.class, () -> carRepository.deleteEntityById("999"));
    }
}
