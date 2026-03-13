import domain.Car;
import filters.CarMaxPriceFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarMaxPriceFilterTests {
    @Test
    void testMatchesWithACarBelowMaxPrice(){
        CarMaxPriceFilter filterToTest= new CarMaxPriceFilter(150.0);
        Car carToTest= new Car("11","Audi","Q3",140);
        assertTrue(filterToTest.matches(carToTest));
    }

    @Test
    void testMatchesWithACarWithMaxPrice(){
        CarMaxPriceFilter filterToTest= new CarMaxPriceFilter(150.0);
        Car carToTest= new Car("12","BMW","X3",150);
        assertTrue(filterToTest.matches(carToTest));
    }

    @Test
    void testMatchesWithACarWithaABiggerPrice(){
        CarMaxPriceFilter filterToTest= new CarMaxPriceFilter(130.0);
        Car carToTest= new Car("11","Audi","Q3",140);
        assertFalse(filterToTest.matches(carToTest));
    }
}
