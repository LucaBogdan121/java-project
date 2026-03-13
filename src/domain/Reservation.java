package domain;
import domain.Identifiable;
import java.io.Serializable;
import java.util.Objects;

public class Reservation implements domain.Identifiable<String>, Serializable {
    private String reservationId;
    private String carId;
    private String customerId;
    private String reservationStartDate;
    private String reservationEndDate;

    public Reservation(String reservationId, String carId, String customerId, String reservationStartDate, String reservationEndDate) {
        this.reservationId = reservationId;
        this.carId = carId;
        this.customerId = customerId;
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getCarId() {
        return carId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getReservationStartDate() {
        return reservationStartDate;
    }

    public String getReservationEndDate() {
        return reservationEndDate;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setReservationStartDate(String reservationStartDate) {
        this.reservationStartDate = reservationStartDate;
    }

    public void setReservationEndDate(String reservationEndDate) {
        this.reservationEndDate = reservationEndDate;
    }

    @Override
    public boolean equals(Object possibleReservation) {
        if (possibleReservation == null || getClass() != possibleReservation.getClass()) return false;
        Reservation reservationToCompareWith = (Reservation) possibleReservation;
        return Objects.equals(reservationId, reservationToCompareWith.reservationId) && Objects.equals(carId, reservationToCompareWith.carId) && Objects.equals(customerId, reservationToCompareWith.customerId) && Objects.equals(reservationStartDate, reservationToCompareWith.reservationStartDate) && Objects.equals(reservationEndDate, reservationToCompareWith.reservationEndDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, carId, customerId, reservationStartDate, reservationEndDate);
    }

    @Override
    public String getId() {
        return reservationId;
    }

    @Override
    public String toString() {
        return reservationId + "," + carId + "," + customerId + "," + reservationStartDate + "," + reservationEndDate;
    }
}
