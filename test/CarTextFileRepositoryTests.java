import domain.Car;
import org.junit.jupiter.api.*;
import repository.CarTextFileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
/// MAJOR REVIEW!!!!!
import static org.junit.jupiter.api.Assertions.*;

public class CarTextFileRepositoryTests {

    private static final String TEST_FILE = "data/test_cars.txt";

    @BeforeEach
    void setup() throws IOException {
        Files.createDirectories(Paths.get("data"));
    }

    @AfterEach
    void cleanup() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE));
    }

    @Test
    void testReadWithValidAndInvalidLines() throws IOException {
        Files.write(Paths.get(TEST_FILE), (
                "1,Audi,A4,100\n" +
                        "BAD_LINE\n" +
                        "2,BMW,X5,150\n" +
                        "3,Ford,Kuga,35.6\n" +   // invalid price
                        "4,Skoda,Octavia,70"
        ).getBytes());

        CarTextFileRepository carTextFileRepository = new CarTextFileRepository(TEST_FILE);

        List<Car> cars = carTextFileRepository.findAll();
        assertEquals(4, cars.size());

        assertNotNull(carTextFileRepository.findById("1"));
        assertNotNull(carTextFileRepository.findById("2"));
        assertNotNull(carTextFileRepository.findById("4"));
        assertNull(carTextFileRepository.findById("999"));
    }

    @Test
    void testAddNewEntityPersists() throws IOException {
        Files.write(Paths.get(TEST_FILE), new byte[0]); // empty file

        CarTextFileRepository carTextFileRepository = new CarTextFileRepository(TEST_FILE);

        carTextFileRepository.addNewEntity(new Car("10", "Tesla", "S", 200));

        CarTextFileRepository reloadCarRepository = new CarTextFileRepository(TEST_FILE);
        assertNotNull(reloadCarRepository.findById("10"));
        assertEquals("Tesla", reloadCarRepository.findById("10").getMake());
    }

    @Test
    void testUpdateEntityPersists() throws Exception {
        Files.write(Paths.get(TEST_FILE), "1,Audi,A4,100".getBytes());
        CarTextFileRepository carTextFileRepository = new CarTextFileRepository(TEST_FILE);

        Car updatedCarRepository = new Car("1", "Audi-Updated", "A5", 150);
        carTextFileRepository.updateEntity(updatedCarRepository);

        CarTextFileRepository reloadCarRepository = new CarTextFileRepository(TEST_FILE);
        Car fromFile = reloadCarRepository.findById("1");

        assertEquals("Audi-Updated", fromFile.getMake());
        assertEquals("A5", fromFile.getModel());
        assertEquals(150, fromFile.getRentPrice());
    }

    @Test
    void testDeleteEntityPersists() throws Exception {
        Files.write(Paths.get(TEST_FILE), "1,Audi,A4,100".getBytes());
        CarTextFileRepository carTextFileRepository = new CarTextFileRepository(TEST_FILE);

        carTextFileRepository.deleteEntityById("1");

        CarTextFileRepository reloadRepository = new CarTextFileRepository(TEST_FILE);
        assertNull(reloadRepository.findById("1"));
        assertEquals(0, reloadRepository.findAll().size());
    }

    @Test
    void testDeleteMissingEntityThrows() throws IOException {
        Files.write(Paths.get(TEST_FILE), "".getBytes());
        CarTextFileRepository carTextFileRepository = new CarTextFileRepository(TEST_FILE);

        Exception exception = assertThrows(Exception.class, () -> carTextFileRepository.deleteEntityById("1"));
        assertTrue(exception.getMessage().contains("ID"));
    }

    @Test
    void testUpdateMissingEntityThrows() throws IOException {
        Files.write(Paths.get(TEST_FILE), "".getBytes());
        CarTextFileRepository carTextFileRepository = new CarTextFileRepository(TEST_FILE);

        Car missingCar = new Car("999", "Ghost", "Phantom", 400);

        assertThrows(Exception.class, () -> carTextFileRepository.updateEntity(missingCar));
    }

    @Test
    void testToStringWrittenCorrectly() throws IOException {
        Files.write(Paths.get(TEST_FILE), "1,Audi,A4,100".getBytes());
        CarTextFileRepository carTextFileRepository = new CarTextFileRepository(TEST_FILE);

        carTextFileRepository.addNewEntity(new Car("2", "BMW", "M3", 250));

        List<String> lines = Files.readAllLines(Paths.get(TEST_FILE));
        assertTrue(lines.stream().anyMatch(l -> l.equals("2,BMW,M3,250.0")));
    }

    @Test
    void testEmptyFileLoadsWithoutError() throws IOException {
        Files.write(Paths.get(TEST_FILE), new byte[0]); // empty
        CarTextFileRepository carTextFileRepository = new CarTextFileRepository(TEST_FILE);

        assertEquals(0, carTextFileRepository.findAll().size());
    }

    @Test
    void testFileNotFoundThrows() {
        assertThrows(RuntimeException.class, () -> new CarTextFileRepository("nonexistent.file"));
    }
}
