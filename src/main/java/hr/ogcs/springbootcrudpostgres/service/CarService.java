package hr.ogcs.springbootcrudpostgres.service;

import hr.ogcs.springbootcrudpostgres.model.Car;
import hr.ogcs.springbootcrudpostgres.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CarService implements ICarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public boolean save(Car car) {
        Optional<Car> carOptional = carRepository.findByCarModel(car.getCarModel());

        if(carOptional.isPresent()) {
            log.info(String.format("%s is already in our garage!",car.getCarModel()));
            throw new IllegalStateException(String.format("%s is already in our garage!",car.getCarModel()));
        }

        carRepository.save(car);
        log.info(String.format("Saved car: Model-%s, Hp-%d, Year-%d, Designer-%s", car.getCarModel(), car.getHp(), car.getYear(), car.getDesigner()));
        return true;
    }

    public boolean deleteFromGarage(Long id) {
        Optional<Car> carOptional = carRepository.findById(id);

        Car car = carOptional.get();
        if(carOptional.isEmpty()) {
            log.info("The ID is not valid");
            throw new IllegalArgumentException("The ID is not valid");
        }

        carRepository.deleteById(id);
        log.info(String.format("Deleted car: Id-%s, Model-%s, Hp-%d, Year-%d, Designer-%s", car.getId(), car.getCarModel(), car.getHp(), car.getYear(), car.getDesigner()));
        return true;
    }

    public boolean updateCar(Long id, Car theCar) {
        Optional<Car> carOptional = carRepository.findById(id);
        Car car = carOptional.get();

        if(carOptional.isEmpty()) {
            log.info("A car with id " + theCar.getId() + " is not in our Garage.");
            throw new IllegalArgumentException("A car with id " + theCar.getId() + " is not in our Garage.");
        }

        if(theCar.getCarModel() != null && !Objects.equals(car.getCarModel(), theCar.getCarModel())) {
            car.setCarModel(theCar.getCarModel());
        }
        if(theCar.getHp() != null && !Objects.equals(car.getHp(), theCar.getHp())) {
            car.setHp(theCar.getHp());
        }
        if(theCar.getYear() != null && !Objects.equals(car.getYear(), theCar.getYear())) {
            car.setYear(theCar.getYear());
        }
        if(theCar.getDesigner() != null && !Objects.equals(car.getDesigner(), theCar.getDesigner())) {
            car.setDesigner(theCar.getDesigner());
        }

        carRepository.save(car);

        log.info(String.format("Updated car: Id-%s, Model-%s, Hp-%d, Year-%d, Designer-%s", car.getId(), car.getCarModel(), car.getHp(), car.getYear(), car.getDesigner()));
        return true;
    }

}
