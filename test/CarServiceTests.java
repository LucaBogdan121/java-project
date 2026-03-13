import domain.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CarRepository;
import service.CarService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarServiceTests {
    private CarRepository testCarRepository;
    private CarService testCarService;

    @BeforeEach
    void setUp(){
        testCarRepository = new CarRepository();
        testCarService = new CarService(testCarRepository);
    }

    @Test
    void testAddCar(){
        testCarService.addCar("100","Toyota","CX5",34.5);
        Car foundCar= testCarService.findCar("100");
        assertNotNull(foundCar);
        assertEquals("Toyota",foundCar.getMake());
    }

    @Test
    void testUpdateCar(){
        testCarService.addCar("200","Audi","Q6",103.5);
        testCarService.updateCar("200","Audi","Q5",100);
        assertEquals("Q5", testCarService.findCar("200").getModel());
        assertEquals(100, testCarService.findCar("200").getRentPrice());
    }

    @Test
    void testUpdateCarThrowsWrappedRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> testCarService.updateCar("999", "Ghost", "Car", 0));
        assertTrue(exception.getMessage().contains("Error updating car"));
    }

    @Test
    void removeCar(){
        testCarService.addCar("300","BMW","X6",103.5);
        assertNotNull(testCarService.findCar("300"));
        testCarService.removeCar("300");
        assertNull(testCarService.findCar("300"));
    }

    @Test
    void testRemoveCarThrowsWrappedRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> testCarService.removeCar("nope"));
        assertTrue(exception.getMessage().contains("Error deleting car"));
    }

    @Test
    void testFindCar(){
        Car fetchedCar= testCarService.findCar("1");
        assertNotNull(fetchedCar);
        assertEquals("Audi",fetchedCar.getMake());
    }

    @Test
    void testDisplayAllCars(){
        List<Car> allCars = testCarService.displayAllCars();
        assertEquals(5,allCars.size());
    }


}
