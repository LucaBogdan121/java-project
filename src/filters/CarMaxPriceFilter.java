package filters;

import domain.Car;
public class CarMaxPriceFilter implements AbstractFilter<Car>{
    private double maxPrice;

    public CarMaxPriceFilter(double maxPrice){
        this.maxPrice=maxPrice;
    }

    @Override
    public boolean matches(Car carToCompare) {
        if (carToCompare==null){
            return false;
        }
        return carToCompare.getRentPrice() <= maxPrice;
    }
}
