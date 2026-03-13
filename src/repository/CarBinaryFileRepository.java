package repository;
import java.io.*;
import java.util.HashMap;

public class CarBinaryFileRepository extends CarFileRepository {
    public CarBinaryFileRepository(String fileName) {
        super(fileName);
    }

    @Override
    protected void readFromFile() {
        File file=new File(this.fileName);
        if (!file.exists()) {
            this.storage = new HashMap<>();
            return;
        }
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(this.fileName))){
            this.storage= (java.util.Map<String, domain.Car>) input.readObject();

        } catch (FileNotFoundException | ClassNotFoundException exception) {
            throw new RuntimeException(new RuntimeException());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @Override
    protected void writeToFile() {
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(this.fileName))){
            output.writeObject(this.storage);

        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
