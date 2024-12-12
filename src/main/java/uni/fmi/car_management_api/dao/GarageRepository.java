package uni.fmi.car_management_api.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uni.fmi.car_management_api.model.Garage;

import java.util.List;
import java.util.Optional;

public interface GarageRepository extends CrudRepository<Garage, Long> {
    Optional<Garage> findById(Long id);

    @Query(value = "select * from garage where city=true", nativeQuery = true)
    List<Garage> findAllByCity(@Param("city") String city);
}
