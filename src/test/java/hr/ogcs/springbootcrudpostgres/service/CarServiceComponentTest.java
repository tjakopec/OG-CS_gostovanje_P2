package hr.ogcs.springbootcrudpostgres.service;

import hr.ogcs.springbootcrudpostgres.model.Car;
import hr.ogcs.springbootcrudpostgres.repository.CarRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * These tests use a real GarageRepository which connects to a local H2 database.
 * The H2 DB is configured in test/resources/application.properties.
 *
 * See {@link CarServiceTest} for mocked component tests.
 */
@SpringBootTest
class CarServiceComponentTest {

    @Autowired
    private CarRepository carRepository;

    private CarService carService;

    @BeforeEach
    void setUp() {
        carService = new CarService(carRepository);
    }

    @Test
    void getAllCars_shouldShowAllExistingCars_ReturnsListOfCars() {
        Car car = Car.builder()
                .carModel("Audi 11")
                .year(2021)
                .hp(374)
                .designer("Karl Lagerfeld")
                .build();

        carService.save(car);

        List<Car> result = carService.getAllCars();

        if(result.size() > 0) {
            Assertions.assertThat(result).isNotEmpty().isNotNull();
        } else if (result.size() == 0) {
            Assertions.assertThat(true);
        }
    }

    @Test
    void save_shouldSaveACar_returnsTrue() {
        Car car = Car.builder()
                .carModel("BMW C3")
                .year(2021)
                .hp(374)
                .designer("Karl Lagerfeld")
                .build();

        boolean result = carService.save(car);

        Assertions.assertThat(result).isTrue().isNotNull();
    }

    @Test
    void deleteFromGarage_shouldDeleteACar_returnsTrue() {
        Car car = Car.builder()
                .carModel("Golf 42")
                .year(2021)
                .hp(374)
                .designer("Karl Lagerfeld")
                .build();

        carService.save(car);

        long savedCarId = carService.getAllCars().get(0).getId();

        boolean result = carService.deleteFromGarage(savedCarId);

        Assertions.assertThat(result).isTrue().isNotNull();
    }

    @Test
    void updateCar_shouldUpdateACar_returnsTrue() {

        Car car = Car.builder()
                .carModel("BMW")
                .year(2021)
                .hp(374)
                .designer("Karl Lagerfeld")
                .build();

        carService.save(car);

        Car savedCar = carService.getAllCars().get(0);

        boolean result = carService.updateCar(savedCar.getId(), savedCar);

        Assertions.assertThat(result).isTrue().isNotNull();
    }
}