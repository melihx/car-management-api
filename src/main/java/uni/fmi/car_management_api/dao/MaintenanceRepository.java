package uni.fmi.car_management_api.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uni.fmi.car_management_api.model.Maintenance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MaintenanceRepository extends CrudRepository<Maintenance,Long> {
    Optional<Maintenance> findById(Long id);

    List<Maintenance> findAll();

    @Query("SELECT m FROM Maintenance m WHERE " +
            "(:carId IS NULL OR m.car.id = :carId) AND " +
            "(:garageId IS NULL OR m.garage.id = :garageId) AND " +
            "(:startDate IS NULL OR m.scheduledDate >= :startDate) AND " +
            "(:endDate IS NULL OR m.scheduledDate <= :endDate)")
    List<Maintenance> findAllByFilters(@Param("carId") Long carId, @Param("garageId") Long garageId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT m FROM Maintenance m WHERE m.garage.id = :garageId")
    List<Maintenance> findByGarageId(@Param("garageId") Long garageId);

}
