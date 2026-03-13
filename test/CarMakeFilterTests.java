import domain.Car;
import filters.CarMakeFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarMakeFilterTests {
    @Test
    void testMatchesWithAValidCar() {
        CarMakeFilter filterToTest= new CarMakeFilter("Toyota");
        Car car= new Car("10","Toyota","Try1",34.5);
        assertTrue(filterToTest.matches(car));
    }

    @Test
    void testMatchesWithNonMatchingMake(){
        CarMakeFilter filterToTest= new CarMakeFilter("Audi");
        Car car= new Car("10","Toyota","Try1",34.5);
        assertFalse(filterToTest.matches(car));
    }

    @Test
    void testMatchesWithNull(){
        CarMakeFilter filterToTest= new CarMakeFilter("Audi");
        assertFalse(filterToTest.matches(null));
    }
}