package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarReadRepository;
import id.ac.ui.cs.advprog.eshop.repository.CarWriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

@Service
public class CarServiceImpl implements CarReadService, CarWriteService {
    @Autowired
    private CarReadRepository carReadRepository;

    @Autowired
    private CarWriteRepository carWriteRepository;

    @Override
    public Car create(Car car) {
        carWriteRepository.create(car);
        return car;
    }

    @Override
    public List<Car> findAll() {
        Iterator<Car> carIterator = carReadRepository.findAll();
        List<Car> allCar = new ArrayList<>();
        carIterator.forEachRemaining(allCar::add);
        return allCar;
    }

    @Override
    public Car findById(String carId) {
        Car car = carReadRepository.findById(carId);
        return car;
    }

    @Override
    public void update(String id, Car car) {
        carWriteRepository.update(id, car);
    }

    @Override
    public void deleteCarById(String carId) {
        carWriteRepository.delete(carId);
    }
}
