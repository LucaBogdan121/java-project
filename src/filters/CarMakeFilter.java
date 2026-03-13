package filters;

import domain.Car;

public class CarMakeFilter implements AbstractFilter<Car> {
    private String make;
    public CarMakeFilter (String make){
        this.make=make;
    }
    @Override
    public boolean matches(Car carToCompare) {
        if(carToCompare==null || carToCompare.getMake()==null){
            return false;
        }
        return carToCompare.getMake().equalsIgnoreCase(make);
    }
}
