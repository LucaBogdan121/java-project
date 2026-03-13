import domain.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CarBinaryFileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class CarBinaryFileRepositoryTests {
    private static String binaryFile="data/car.bin";
    private CarBinaryFileRepository repositoryForCarBinary;

    @BeforeEach
    void setUp() throws IOException {
        if (Files.exists(Paths.get(binaryFile))) {
            Files.delete(Paths.get(binaryFile));
        }

        repositoryForCarBinary = new CarBinaryFileRepository(binaryFile);
        repositoryForCarBinary.addNewEntity(new Car("20", "Toyota", "Camry", 50.0));
        repositoryForCarBinary.addNewEntity(new Car("21", "Audi", "Q3", 50.0));
    }

    @Test
    void simpleTest() {
        assertNotNull(repositoryForCarBinary);
    }

    @Test
    void testAddNewEntityWhichIsWrittenInTheBinaryFile(){
        Car newCar= new Car("22", "Audi", "A3", 59.0);
        repositoryForCarBinary.addNewEntity(newCar);
        CarBinaryFileRepository reloadedRepo= new CarBinaryFileRepository(binaryFile);
        assertEquals(3,reloadedRepo.findAll().size());

        Car retrivedCar=reloadedRepo.findById("22");
        assertNotNull(retrivedCar);
        assertEquals("Audi",retrivedCar.getMake());
        assertEquals("A3",retrivedCar.getModel());
        assertEquals(59.0,retrivedCar.getRentPrice());

    }

    @Test
    void testUpdateEntity() throws Exception {
        Car updatedCar = new Car("20","Mercedes","C Class",60.0);
        repositoryForCarBinary.updateEntity(updatedCar);

        CarBinaryFileRepository reloadedRepo= new CarBinaryFileRepository(binaryFile);
        Car retrivedCar = reloadedRepo.findById("20");
        assertNotNull(retrivedCar);
        assertEquals("Mercedes",retrivedCar.getMake());
        assertEquals("C Class",retrivedCar.getModel());
        assertEquals(60.0,retrivedCar.getRentPrice());
    }

    @Test
    void testDeleteEntityIsPersisted() throws Exception {
        repositoryForCarBinary.deleteEntityById("21");

        CarBinaryFileRepository reloadedRepository = new CarBinaryFileRepository(binaryFile);

        assertNull(reloadedRepository.findById("21"));
        assertEquals(1, reloadedRepository.findAll().size());
    }

    @Test
    void testFileIsCreatedAfterWrite() {
        assertTrue(Files.exists(Paths.get(binaryFile)));
    }

}
