package uni.fmi.car_management_api.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uni.fmi.car_management_api.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {
    Optional<Car> findById(Long id);

    List<Car> findAll();

    @Query("SELECT c FROM Car c WHERE (:make IS NULL OR LOWER(c.make) = LOWER(:make)) AND "
            + "(:garageId IS NULL OR EXISTS (SELECT g FROM c.garages g WHERE g.id = :garageId)) AND "
            + "(:startYear IS NULL OR c.productionYear >= :startYear) AND "
            + "(:endYear IS NULL OR c.productionYear <= :endYear)")
    List<Car> findAllByFilters(@Param("make") String make, @Param("garageId") Long garageId,
                               @Param("startYear") Integer startYear, @Param("endYear") Integer endYear);

}
