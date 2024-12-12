package uni.fmi.car_management_api.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uni.fmi.car_management_api.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {
    Optional<Car> findById(Long id);

    @Query("select c from Car c where c.make= :make and c.garageId= :garageId and c.productionYear>= :startYear and c.productionYear<= :endYear")
    List<Car> findAllByFilters(@Param("make") String make, @Param("garageId") Long garageId, @Param("startYear") Integer startYear, @Param("endYear") Integer endYear);
}
