import domain.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ReservationTests {
    private Reservation reservationToTest;
    @BeforeEach
    void setUpAReservation(){
        reservationToTest=new Reservation("10","10","3","09.11.2025","10.11.2025");
    }
    @Test
    void testConstructorAndGetters() {
        assertEquals("10", reservationToTest.getReservationId());
        assertEquals("10",reservationToTest.getCarId());
        assertEquals("3",reservationToTest.getCustomerId());
        assertEquals("09.11.2025",reservationToTest.getReservationStartDate());
        assertEquals("10.11.2025",reservationToTest.getReservationEndDate());
    }

    @Test
    void testEqualsAndHashCode(){
        Reservation reservationWithTheSameAttributes= new Reservation("10","10","3","09.11.2025","10.11.2025");
        Reservation differentReservation= new Reservation("11","12","5","09.09.2025","11.09.2025");
        assertEquals(reservationToTest,reservationWithTheSameAttributes);
        assertEquals(reservationToTest.hashCode(),reservationWithTheSameAttributes.hashCode());
        assertNotEquals(reservationToTest,differentReservation);
        assertNotEquals(reservationToTest.hashCode(),differentReservation.hashCode());
    }
    @Test
    void testSetters(){
        Reservation anotherReservationToTest= new Reservation("0","0","0","-","-");
        anotherReservationToTest.setReservationId("12");
        anotherReservationToTest.setCarId("13");
        anotherReservationToTest.setCustomerId("7");
        anotherReservationToTest.setReservationStartDate("01.01.2023");
        anotherReservationToTest.setReservationEndDate("03.01.2023");

        assertEquals("12", anotherReservationToTest.getReservationId());
        assertEquals("13",anotherReservationToTest.getCarId());
        assertEquals("7",anotherReservationToTest.getCustomerId());
        assertEquals("01.01.2023",anotherReservationToTest.getReservationStartDate());
        assertEquals("03.01.2023",anotherReservationToTest.getReservationEndDate());
    }

    @Test
    void testToString() {
        String expectedResult = "10,10,3,09.11.2025,10.11.2025";
        assertEquals(expectedResult, reservationToTest.toString());
    }

    @Test
    void testCarIsNotNull(){
        assertNotEquals(reservationToTest,null);
    }
}
