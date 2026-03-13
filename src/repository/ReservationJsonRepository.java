package repository;
import domain.Reservation;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationJsonRepository extends ReservationFileRepository {
    public ReservationJsonRepository(String filename) {
        super(filename);
    }

    @Override
    public void readFromFile() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String json = reader.lines().collect(Collectors.joining());
            json = json.replace("[", "").replace("]", "");

            if (json.trim().isEmpty()) return;

            String[] reservationObjects = json.split("},\\{");

            for (String reservationString : reservationObjects) {
                reservationString = reservationString.replace("{", "").replace("}", "");
                String[] parts = reservationString.split(",");
                String reservationId = getValue(parts[0]);
                String carId = getValue(parts[1]);
                String customerId = getValue(parts[2]);
                String startDate = getValue(parts[3]);
                String endDate = getValue(parts[4]);
                Reservation reservation = new Reservation(reservationId, carId, customerId, startDate, endDate);
                super.addNewEntity(reservation);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private String getValue(String part) {
        String[] split = part.split(":");
        return split[1].replace("\"", "").trim();
    }

    @Override
    public void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            List<Reservation> reservations = findAll();
            writer.write("[");
            writer.newLine();
            for (int indexToGetElementFrom = 0; indexToGetElementFrom < reservations.size(); indexToGetElementFrom++) {
                Reservation currentReservation = reservations.get(indexToGetElementFrom);
                String line = String.format(
                        "{\"id\":\"%s\",\"carId\":\"%s\",\"customerId\":\"%s\",\"startDate\":\"%s\",\"endDate\":\"%s\"}",
                        currentReservation.getId(),
                        currentReservation.getCarId(),
                        currentReservation.getCustomerId(),
                        currentReservation.getReservationStartDate(),
                        currentReservation.getReservationEndDate()
                );
                writer.write(line);
                if (indexToGetElementFrom < reservations.size() - 1) {
                    writer.write(",");
                }
                writer.newLine();
            }
            writer.write("]");
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}