package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarRepositoryTest {

    @InjectMocks
    CarRepository carRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Car car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Pajero Sport");
        car.setCarColor("Hitam");
        car.setCarQuantity(2);
        carRepository.create(car);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car.getCarId(), savedCar.getCarId());
        assertEquals(car.getCarName(), savedCar.getCarName());
        assertEquals(car.getCarColor(), savedCar.getCarColor());
        assertEquals(car.getCarQuantity(), savedCar.getCarQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneCar() {
        Car car1 = new Car();
        car1.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car1.setCarName("Pajero Sport");
        car1.setCarColor("Hitam");
        car1.setCarQuantity(2);
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        car2.setCarName("Toyota Innova");
        car2.setCarColor("Putih");
        car2.setCarQuantity(5);
        carRepository.create(car2);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car1.getCarId(), savedCar.getCarId());
        savedCar = carIterator.next();
        assertEquals(car2.getCarId(), savedCar.getCarId());
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testFindByIdFound() {
        Car car = new Car();
        String carId = UUID.randomUUID().toString();
        car.setCarId(carId);
        car.setCarName("Pajero Sport");
        car.setCarColor("Hitam");
        car.setCarQuantity(2);
        carRepository.create(car);

        Car foundCar = carRepository.findById(carId);

        assertNotNull(foundCar);
        assertEquals(carId, foundCar.getCarId());
        assertEquals("Pajero Sport", foundCar.getCarName());
        assertEquals("Hitam", foundCar.getCarColor());
    }

    @Test
    void testFindByIdNotFound() {
        Car car = new Car();
        car.setCarId(UUID.randomUUID().toString());
        car.setCarName("Pajero Sport");
        car.setCarColor("Hitam");
        car.setCarQuantity(2);
        carRepository.create(car);

        String randomId = UUID.randomUUID().toString();
        Car foundCar = carRepository.findById(randomId);

        assertNull(foundCar);
    }

    @Test
    void testUpdateCar() {
        Car car = new Car();
        car.setCarName("Pajero Sport");
        car.setCarColor("Hitam");
        car.setCarQuantity(2);
        Car createdCar = carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarId(createdCar.getCarId());
        updatedCar.setCarName("Pajero Dakar");
        updatedCar.setCarColor("Silver");
        updatedCar.setCarQuantity(5);

        Car result = carRepository.update(createdCar.getCarId(), updatedCar);

        assertNotNull(result);
        assertEquals("Pajero Dakar", result.getCarName());
        assertEquals("Silver", result.getCarColor());
        assertEquals(5, result.getCarQuantity());

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals("Pajero Dakar", savedCar.getCarName());
        assertEquals("Silver", savedCar.getCarColor());
        assertEquals(5, savedCar.getCarQuantity());
    }

    @Test
    void testUpdateCarAtSecondIndex() {
        Car firstCar = new Car();
        firstCar.setCarName("Pajero Sport");
        carRepository.create(firstCar);

        Car secondCar = new Car();
        secondCar.setCarName("Toyota Innova");
        Car createdSecondCar = carRepository.create(secondCar);

        Car updatedSecondCar = new Car();
        updatedSecondCar.setCarName("Toyota Alphard");
        updatedSecondCar.setCarColor("Hitam");
        updatedSecondCar.setCarQuantity(1);

        Car result = carRepository.update(createdSecondCar.getCarId(), updatedSecondCar);

        assertNotNull(result);
        assertEquals("Toyota Alphard", result.getCarName());
        assertEquals("Hitam", result.getCarColor());
    }

    @Test
    void testUpdateCarIfNotFound() {
        Car car = new Car();
        car.setCarName("Pajero Sport");
        car.setCarQuantity(2);

        Car result = carRepository.update(UUID.randomUUID().toString(), car);
        assertNull(result);
    }

    @Test
    void testDeleteCar() {
        Car car = new Car();
        car.setCarName("Pajero Sport");
        car.setCarQuantity(2);
        Car createdCar = carRepository.create(car);

        // Delete di CarRepository adalah void, jadi kita cek dari hasil pencarian
        carRepository.delete(createdCar.getCarId());

        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());

        Car deletedCarCheck = carRepository.findById(createdCar.getCarId());
        assertNull(deletedCarCheck);
    }

    @Test
    void testDeleteCarIfNotFound() {
        String randomId = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> carRepository.delete(randomId));
    }
}