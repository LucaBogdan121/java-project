package repository;
import domain.Car;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDatabaseRepository extends DatabaseRepository<Car,String> {
    public CarDatabaseRepository(String databaseUrl){
        super(databaseUrl);
    }

    @Override
    public Car addNewEntity(Car carToBeAdded){
        this.openConnection();
        try(PreparedStatement statement= connection.prepareStatement("INSERT INTO CarTable VALUES (?,?,?,?)")){
            statement.setString(1,carToBeAdded.getId());
            statement.setString(2, carToBeAdded.getMake());
            statement.setString(3,carToBeAdded.getModel());
            statement.setDouble(4,carToBeAdded.getRentPrice());
            statement.executeUpdate();
            this.closeConnection();
            return carToBeAdded;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteEntityById(String id){
        this.openConnection();
        try(PreparedStatement statement= connection.prepareStatement("DELETE FROM CarTable WHERE carId=?")) {
            statement.setString(1,id);
            statement.executeUpdate();
            this.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Car findById(String id) {
        this.openConnection();
        Car foundCar=null;
        try(PreparedStatement statement= connection.prepareStatement("SELECT * FROM CarTable WHERE carId=?")) {
            statement.setString(1,id);
            try(ResultSet resultSet=statement.executeQuery()){
                if(resultSet.next()){
                    String make=resultSet.getString("carMake");
                    String model= resultSet.getString("carModel");
                    double rentPrice= resultSet.getDouble("rentPrice");
                    foundCar= new Car(id,make,model,rentPrice);
                }
            }
            this.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return foundCar;
    }

    @Override
    public List<Car> findAll() {
        ArrayList<Car> allCars = new ArrayList<>();
        this.openConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM CarTable");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String id = resultSet.getString("carId");
                String make = resultSet.getString("carMake");
                String model = resultSet.getString("carModel");
                double rentPrice = resultSet.getDouble("rentPrice");
                allCars.add(new Car(id, make, model, rentPrice));
            }
            this.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allCars;
    }

    @Override
    public Car updateEntity(Car carToBeUpdated) throws Exception{
        this.openConnection();
        try(PreparedStatement statement=connection.prepareStatement("UPDATE CarTable SET carMake = ?, carModel = ?, rentPrice = ? WHERE carId = ?")){
            statement.setString(1, carToBeUpdated.getMake());
            statement.setString(2, carToBeUpdated.getModel());
            statement.setDouble(3, carToBeUpdated.getRentPrice());
            statement.setString(4, carToBeUpdated.getId());
            statement.executeUpdate();
            this.closeConnection();
            return carToBeUpdated;
        }
    }


}
