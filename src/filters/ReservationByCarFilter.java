package filters;

import domain.Reservation;
public class ReservationByCarFilter implements AbstractFilter<Reservation>{
    private String carId;
    public ReservationByCarFilter(String carId){
        this.carId=carId;
    }
    @Override
    public boolean matches(Reservation reservation) {
        if(reservation==null || carId==null){
            return false;
        }
        return reservation.getCarId().equals(carId);
    }
}
