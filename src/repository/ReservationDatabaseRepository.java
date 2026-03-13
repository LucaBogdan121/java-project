package repository;
import domain.Reservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDatabaseRepository extends DatabaseRepository<Reservation,String> {
    public ReservationDatabaseRepository(String databaseUrl){
        super(databaseUrl);
    }

    @Override
    public Reservation addNewEntity(Reservation reservationToBeAdded) {
        this.openConnection();
        try(PreparedStatement statement= connection.prepareStatement("INSERT INTO reservationTable VALUES (?,?,?,?,?)")){
            statement.setString(1,reservationToBeAdded.getId());
            statement.setString(2,reservationToBeAdded.getCarId());
            statement.setString(3,reservationToBeAdded.getCustomerId());
            statement.setString(4,reservationToBeAdded.getReservationStartDate());
            statement.setString(5,reservationToBeAdded.getReservationEndDate());
            statement.executeUpdate();
            this.closeConnection();
            return reservationToBeAdded;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reservation findById(String reservationId) {
        this.openConnection();
        Reservation foundReservation=null;
        try(PreparedStatement statement= connection.prepareStatement("SELECT * FROM reservationTable WHERE reservationId=?")) {
            statement.setString(1,reservationId);
            try(ResultSet resultSet=statement.executeQuery()){
                if(resultSet.next()){
                    String carId=resultSet.getString("carId");
                    String customerId=resultSet.getString("customerId");
                    String reservationStartDate=resultSet.getString("reservationStartDate");
                    String reservationEndDate=resultSet.getString("reservationEndDate");
                    foundReservation=new Reservation(reservationId,carId,customerId,reservationStartDate,reservationEndDate);
                }
            }
            this.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return foundReservation;
    }

    @Override
    public List<Reservation> findAll() {
        ArrayList<Reservation> allResevations = new ArrayList<>();
        this.openConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservationTable");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String reservationId=resultSet.getString("reservationId");
                String carId=resultSet.getString("carId");
                String customerId=resultSet.getString("customerId");
                String reservationStartDate=resultSet.getString("reservationStartDate");
                String reservationEndDate=resultSet.getString("reservationEndDate");
                allResevations.add(new Reservation(reservationId,carId,customerId,reservationStartDate,reservationEndDate));
            }
            this.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allResevations;
    }

    @Override
    public Reservation updateEntity(Reservation reservationToBeUpdated) throws Exception {
        this.openConnection();
        try(PreparedStatement statement=connection.prepareStatement("UPDATE reservationTable SET carId = ?, customerId = ?, reservationStartDate = ? , reservationEndDate = ? WHERE reservationId = ?")){
            statement.setString(1,reservationToBeUpdated.getId());
            statement.setString(2,reservationToBeUpdated.getCarId());
            statement.setString(3,reservationToBeUpdated.getCustomerId());
            statement.setString(4,reservationToBeUpdated.getReservationStartDate());
            statement.setString(5,reservationToBeUpdated.getReservationEndDate());
            statement.executeUpdate();
            this.closeConnection();
            return reservationToBeUpdated;
        }
    }

    @Override
    public void deleteEntityById(String reservationId) throws Exception {
        this.openConnection();
        try(PreparedStatement statement= connection.prepareStatement("DELETE FROM reservationTable WHERE reservationId=?")) {
            statement.setString(1,reservationId);
            statement.executeUpdate();
            this.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
