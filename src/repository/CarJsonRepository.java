package repository;

import domain.Car;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class CarJsonRepository extends CarFileRepository {

    public CarJsonRepository(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String json = reader.lines().collect(Collectors.joining());
            json = json.replace("[", "").replace("]", "");

            if (json.trim().isEmpty()) return;

            String[] carObjects = json.split("},\\{");

            for (String carString : carObjects) {
                carString = carString.replace("{", "").replace("}", "");

                String[] parts = carString.split(",");

                String id = getValue(parts[0]);
                String make = getValue(parts[1]);
                String model = getValue(parts[2]);
                double rentPrice = Double.parseDouble(getValue(parts[3]));

                Car car = new Car(id, make, model, rentPrice);
                super.addNewEntity(car);
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
    protected void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            List<Car> cars = findAll();
            writer.write("[");
            writer.newLine();

            for (int indexToGetElementFrom = 0; indexToGetElementFrom < cars.size(); indexToGetElementFrom++) {
                Car carToWrite = cars.get(indexToGetElementFrom);
                String line = String.format(
                        "{\"id\":\"%s\",\"make\":\"%s\",\"model\":\"%s\",\"rentPrice\":%s}",
                        carToWrite.getId(), carToWrite.getMake(), carToWrite.getModel(), carToWrite.getRentPrice()
                );
                writer.write(line);

                if (indexToGetElementFrom < cars.size() - 1) {
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