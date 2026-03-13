package repository;

import domain.Reservation;

import java.io.*;

public class ReservationBinaryFileRepository extends ReservationFileRepository{
    public ReservationBinaryFileRepository(String fileName) {
        super(fileName);
    }
    @Override
    public void readFromFile() {
        this.storage.clear();
        File file = new File(this.fileName);
        int expectedNumberOfFields=5;
        int reservationIdIndex=0;
        int carIdIndex=1;
        int customerIdIndex=2;
        int reservationStartDateIndex=3;
        int reservationEndDateIndex=4;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                if (readLine.isBlank())
                    continue;

                String[] partsOfExtractedText = readLine.split(",");
                if (partsOfExtractedText.length < expectedNumberOfFields) {
                    continue;
                }

                Reservation reservation = new Reservation(
                        partsOfExtractedText[reservationIdIndex].trim(),
                        partsOfExtractedText[carIdIndex].trim(),
                        partsOfExtractedText[customerIdIndex].trim(),
                        partsOfExtractedText[reservationStartDateIndex].trim(),
                        partsOfExtractedText[reservationEndDateIndex].trim()
                );

                this.storage.put(reservation.getId(), reservation);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void writeToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.fileName))) {
            for (Reservation reservation : this.storage.values()) {
                bufferedWriter.write(reservation.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
