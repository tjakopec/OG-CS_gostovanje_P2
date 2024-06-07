package hr.ogcs.springbootcrudpostgres.controller;

import hr.ogcs.springbootcrudpostgres.exception.RestExceptionHandler;
import hr.ogcs.springbootcrudpostgres.model.Car;
import hr.ogcs.springbootcrudpostgres.service.CarService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @Operation(summary = "Show all cars in the Garage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of all cars in the Garage",
                    content = {@Content(
                            schema = @Schema(implementation = Car.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "This car is not in our garage",
                    content = {@Content(
                            schema = @Schema(implementation = RestExceptionHandler.class),
                            mediaType = "application/json")})})
    @GetMapping("/")
    public List<Car> showAllCars() {
        return carService.getAllCars();
    }

    @Operation(summary = "Add a car to the Garage")
    @ApiResponse(responseCode = "200",
            description = "A car is added to the Garage",
            content = {@Content(
                    mediaType = "application/json")})
    @PostMapping("/")
    public boolean addCarToGarage(@RequestBody Car car) {
        return carService.save(car);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<Car> updateCar(@PathVariable("carId") Long id) {
        var car = carService.getCarById(id);
        if (car.isPresent()) {
            return ResponseEntity.ok(car.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Updates a car by its id")
    @ApiResponse(responseCode = "200",
            description = "The car is successfully updated",
            content = {@Content(mediaType = "application/json")})
    @PutMapping("/{carId}")
    public boolean updateCar(
            @PathVariable("carId") Long id,
            @RequestBody Car car) {
        return carService.updateCar(id, car);
    }

    @Operation(summary = "Delete a car by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "A car is deleted from the Garage",
                    content = {@Content(
                            schema = @Schema(implementation = Car.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "A car with this id is not in our garage",
                    content = {@Content(
                            schema = @Schema(implementation = Car.class),
                            mediaType = "application/json")})})
    @DeleteMapping("/{carId}")
    public boolean deleteCarFromGarage(@PathVariable("carId") Long id) {
        return carService.deleteFromGarage(id);
    }

}
