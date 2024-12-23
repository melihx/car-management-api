package uni.fmi.car_management_api.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uni.fmi.car_management_api.model.Maintenance;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRepository extends CrudRepository<Maintenance,Long> {
    Optional<Maintenance> findById(Long id);

    @Query("select m from Maintenance m where m.carId=? :carId and m.garageId=? :garageId and m.scheduledDate>= :startDate and m.scheduledDate<= :endDate")
    List<Maintenance> findAllByFilters(@Param("carId") String make, @Param("garageId") Long garageId, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
