package filters;

import domain.Reservation;

public class ReservationByCustomerFilter implements  AbstractFilter<Reservation>{
    private String customerId;

    public ReservationByCustomerFilter(String customerId){
        this.customerId=customerId;
    }
    @Override
    public boolean matches(Reservation reservation) {
        if(reservation==null || customerId==null){
            return false;
        }
        return reservation.getCustomerId().equals(customerId);
    }
}
