package repository;

import domain.Car;
import domain.Reservation;

import java.io.*;

public class ReservationTextFileRepository extends ReservationFileRepository{
    public ReservationTextFileRepository(String textFile) {
        super(textFile);
    }

    @Override
    public void readFromFile() {
        int expectedNumberOfArguments=5;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(this.fileName))){
            String readLine = bufferedReader.readLine();
            while(readLine!=null){
                String[] tokens = readLine.split(",");
                if(tokens.length != expectedNumberOfArguments){
                    readLine = bufferedReader.readLine();
                    continue;
                }
                String reservationId = tokens[0].trim();
                String carId = tokens[1].trim();
                String customerId = tokens[2].trim();
                String reservationStartDate = tokens[3].trim();
                String reservationEndDate = tokens[4].trim();
                Reservation newReservation = new Reservation(reservationId,carId,customerId,reservationStartDate,reservationEndDate);
                this.storage.put(reservationId,newReservation);
                readLine = bufferedReader.readLine();
            }
        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception);
        } catch (IOException secondException) {
            throw new RuntimeException(secondException);
        }

    }

    @Override
    public void writeToFile() {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.fileName))){
            for (Reservation reservation : storage.values()) {
                bufferedWriter.write(reservation.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
