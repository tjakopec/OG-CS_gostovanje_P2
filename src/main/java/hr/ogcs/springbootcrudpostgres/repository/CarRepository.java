package hr.ogcs.springbootcrudpostgres.repository;

import hr.ogcs.springbootcrudpostgres.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findById(Long id);

    Optional<Car> findByCarModel(String carModel);

    Optional<Car> findByYear(Integer year);

    Optional<Car> findByYearAndCarModelOrderByCarModelDesc(Integer year, String model);
}
