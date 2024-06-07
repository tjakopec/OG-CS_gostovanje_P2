package hr.ogcs.springbootcrudpostgres;

import hr.ogcs.springbootcrudpostgres.model.Car;
import hr.ogcs.springbootcrudpostgres.service.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
// Activate profile for later integration-test use
@ActiveProfiles("integration-test")
public class IntegrationTest {

    private RestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private CarService carService;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
    }

    @Container
    private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("garages")
            .withUsername("postgres")
            .withPassword("secret");

    @DynamicPropertySource
    public static void setupThings(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void should_test_if_container_is_running() {
        assertThat(postgreSQLContainer.isRunning()).isTrue();
        System.out.println(postgreSQLContainer.getJdbcUrl());
        System.out.println(postgreSQLContainer.getJdbcUrl());
    }

    @Test
    void should_create_update_and_delete_car() {
        Car car = Car.builder()
                .carModel("Mercedes")
                .hp(300)
                .year(2009)
                .designer("Mercedes engineer")
                .build();

        System.out.println(postgreSQLContainer.getDatabaseName());
        System.out.println(postgreSQLContainer.getUsername());
        System.out.println(postgreSQLContainer.getPassword());
        System.out.println(postgreSQLContainer.getJdbcUrl());

        // create Car ...
        var addCar = restTemplate.postForObject("http://localhost:" + port + "/cars/", car, boolean.class);
        // verify Car ... is being created
        List<Car> cars = carService.getAllCars();

        Long carId = cars.get(cars.size() - 1).getId();

        Car retrievedCar = restTemplate.getForObject("http://localhost:" + port + "/cars/" + carId,
                Car.class);

        assertEquals(car.getCarModel(), retrievedCar.getCarModel());
        assertEquals(car.getHp(), retrievedCar.getHp());
        assertEquals(car.getYear(), retrievedCar.getYear());
        assertEquals(car.getDesigner(), retrievedCar.getDesigner());

        // update Car ...
        car.setId(carId);
        car.setCarModel("Volkswagen");
        car.setHp(100);
        car.setYear(2013);
        car.setDesigner("Pero mehanicar");

        // update Car
        restTemplate.put("http://localhost:" + port + "/cars/" + carId, car);
        // verify Car ... is being updated
        Car retrievedUpdatedCar = restTemplate.getForObject("http://localhost:" + port + "/cars/" + car.getId(),
                Car.class);
        assertEquals(retrievedUpdatedCar.getCarModel(), car.getCarModel());
        assertEquals(retrievedUpdatedCar.getHp(), car.getHp());
        assertEquals(retrievedUpdatedCar.getYear(), car.getYear());
        assertEquals(retrievedUpdatedCar.getDesigner(), car.getDesigner());
        assertNotEquals(retrievedUpdatedCar.getUpdated(), car.getUpdated());

        // delete Car ...
        restTemplate.delete("http://localhost:" + port + "/cars/" + retrievedUpdatedCar.getId());
        // ... and verify that Car doesn't exist anymore
        HttpClientErrorException thrown = Assertions.assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForEntity("http://localhost:" + port + "/cars/" +
                    retrievedUpdatedCar.getId(), Object.class);
        });
        assertEquals("404 NOT_FOUND", thrown.getStatusCode().toString());
    }
}