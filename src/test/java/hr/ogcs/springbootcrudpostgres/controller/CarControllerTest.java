package hr.ogcs.springbootcrudpostgres.controller;

import hr.ogcs.springbootcrudpostgres.model.Car;
import hr.ogcs.springbootcrudpostgres.service.CarService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

@AutoConfigureMockMvc
@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void showAllCars_ShouldShowAllExistingCars_ReturnsListOfCars() {
        Mockito.when(carService.getAllCars()).thenReturn(
                List.of(Car.builder()
                        .id(1L)
                        .year(2021)
                        .hp(374)
                        .carModel("BMW")
                        .designer("Karl Lagerfeld")
                        .build()));


        RestAssuredMockMvc
                .given()
                .auth().none()
                .when()
                .get("/cars/")
                .then()
                .statusCode(200)
                .body("$.size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].carModel", Matchers.equalTo("BMW"))
                .body("[0].hp", Matchers.equalTo(374))
                .body("[0].year", Matchers.equalTo(2021))
                .body("[0].designer", Matchers.equalTo("Karl Lagerfeld"));
    }


    @Test
    void addCarToGarage_shouldSaveACar_returnsTrue() {
        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body("{\"carModel\": \"BMW C3\", \"year\":\"2021 \", \"hp\":\"3336\", \"designer\":\"Luka\"}")
                .auth().none()
                .when()
                .post("/cars/")
                .then()
                .statusCode(200);
    }


    @Test
    void updateCar() {
    }


    @Test
    void deleteCarFromGarage() {
    }
}