
package repository;
import domain.Identifiable;
import domain.Reservation;

import java.util.List;

public class ReservationRepository extends MemoryRepository<Reservation, String> {

    public ReservationRepository(List<Reservation> initialReservations) {
        super();

        if (initialReservations != null) {
            for (Reservation reservation : initialReservations) {
                addNewEntity(reservation);
            }
        }
    }

    public List<Reservation> getAllReservations() {
        return findAll();
    }

    public Reservation getReservationById(String id) {
        return findById(id);
    }
}