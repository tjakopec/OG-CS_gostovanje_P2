package hr.ogcs.springbootcrudpostgres.service;

import hr.ogcs.springbootcrudpostgres.model.Car;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface ICarService {

    List<Car> getAllCars();
    boolean save(Car car);

    boolean deleteFromGarage(Long id);

    boolean updateCar(Long id, Car theCar);



}
