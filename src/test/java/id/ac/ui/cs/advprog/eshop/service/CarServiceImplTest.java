package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarReadRepository;
import id.ac.ui.cs.advprog.eshop.repository.CarWriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarReadRepository carReadRepository;

    @Mock
    private CarWriteRepository carWriteRepository;

    private CarServiceImpl carService;

    private Car car;

    @BeforeEach
    void setUp() {
        carService = new CarServiceImpl(carReadRepository, carWriteRepository);

        car = new Car();
        car.setCarId(UUID.randomUUID().toString());
        car.setCarName("Pajero Sport");
        car.setCarColor("Hitam");
        car.setCarQuantity(5);
    }

    @Test
    void testCreate() {
        when(carWriteRepository.create(car)).thenReturn(car);

        Car createdCar = carService.create(car);

        assertEquals(car.getCarId(), createdCar.getCarId());
        verify(carWriteRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        Car car2 = new Car();
        List<Car> carList = Arrays.asList(car, car2);
        Iterator<Car> iterator = carList.iterator();

        when(carReadRepository.findAll()).thenReturn(iterator);

        List<Car> result = carService.findAll();

        assertEquals(2, result.size());
        assertEquals(car.getCarName(), result.get(0).getCarName());
    }

    @Test
    void testFindById() {
        String id = car.getCarId();
        when(carReadRepository.findById(id)).thenReturn(car);

        Car foundCar = carService.findById(id);

        assertNotNull(foundCar);
        assertEquals(car.getCarId(), foundCar.getCarId());
    }

    @Test
    void testUpdate() {
        carService.update(car.getCarId(), car);

        verify(carWriteRepository, times(1)).update(car.getCarId(), car);
    }

    @Test
    void testDeleteCarById() {
        String id = UUID.randomUUID().toString();
        carService.deleteCarById(id);

        verify(carWriteRepository, times(1)).delete(id);
    }
}