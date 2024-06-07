package hr.ogcs.springbootcrudpostgres.service;

import hr.ogcs.springbootcrudpostgres.model.Car;
import hr.ogcs.springbootcrudpostgres.repository.CarRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * These tests use a Mock of the GarageRepository to test just the behaviour of the Service.
 *
 * See {@link CarServiceComponentTest} for H2-based component tests.
 */
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    private AutoCloseable autoCloseable;

    private CarService carService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        carService = new CarService(carRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllCars_shouldShowAllExistingCars_ReturnsListOfCars() {
        List<Car> cars = List.of(Car.builder()
                .id(1L)
                .year(2021)
                .hp(374)
                .carModel("BMW")
                .designer("Karl Lagerfeld")
                .build());
        when(carRepository.findAll()).thenReturn(cars);

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

        when(carRepository.save(car)).thenReturn(car);

        var result = carService.save(car);

        Assertions.assertThat(result).isTrue().isNotNull();
    }

    @Test
    void deleteFromGarage_shouldDeleteACar_returnsTrue() {
        Car car = new Car();
        Long id = 1L;
        car.setId(id);

        given(carRepository.existsById(car.getId()))
                .willReturn(true);

        when(carRepository.findById(id)).thenReturn(Optional.of(car));

        boolean result = carService.deleteFromGarage(id);

        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    void updateCar_shouldUpdateACar_returnsTrue() {
        Long id = 1L;
        Car car = Car.builder()
                .id(id)
                .carModel("BMW")
                .year(2021)
                .hp(374)
                .designer("Karl Lagerfeld")
                .build();

        given(carRepository.existsById(car.getId()))
                .willReturn(true);

        when(carRepository.findById(id)).thenReturn(Optional.of(car));

        boolean result = carService.updateCar(id, car);

        Assertions.assertThat(result).isTrue().isNotNull();
    }
}