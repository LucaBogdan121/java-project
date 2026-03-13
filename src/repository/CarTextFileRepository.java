package repository;

import domain.Car;

import java.io.*;
import java.util.Iterator;

public class CarTextFileRepository extends CarFileRepository{

    public CarTextFileRepository(String textFile) {
        super(textFile);
    }

    @Override
    protected void readFromFile() {
        File file = new File(this.fileName);
        int expectedNumberOfArguments=4;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isBlank())
                    continue;

                String[] partsOfExtractedText = line.split(",");
                if (partsOfExtractedText.length != expectedNumberOfArguments)
                    continue;

                String id = partsOfExtractedText[0].trim();
                String make = partsOfExtractedText[1].trim();
                String model = partsOfExtractedText[2].trim();
                double rentPrice = Double.parseDouble(partsOfExtractedText[3].trim());

                Car newCar = new Car(id, make, model, rentPrice);
                storage.put(id, newCar);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Error reading cars file: " + exception.getMessage(), exception);
        }
    }


    @Override
    protected void writeToFile() {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.fileName))){
            for (Car car : storage.values()) {
                bufferedWriter.write(car.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
