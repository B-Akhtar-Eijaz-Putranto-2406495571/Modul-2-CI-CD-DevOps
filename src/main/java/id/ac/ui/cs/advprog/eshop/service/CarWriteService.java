package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Car;

public interface CarWriteService {
    public Car create(Car car);
    public void update(String id, Car car);
    public void deleteCarById(String carId);
}
