package uni.fmi.car_management_api.service;

import uni.fmi.car_management_api.model.dto.request.CreateMaintenanceDTO;
import uni.fmi.car_management_api.model.dto.request.UpdateMaintenanceDTO;
import uni.fmi.car_management_api.model.dto.response.ResponseMaintenanceDTO;

import java.util.List;
import java.util.Optional;

public interface MaintenanceService {
    List<ResponseMaintenanceDTO> getAllMaintenances(Optional<Long> carId, Optional<Long> garageId,
                                                    Optional<String> startDate, Optional<String> endDate);

    ResponseMaintenanceDTO getMaintenance(Long id);

    ResponseMaintenanceDTO createNewMaintenance(CreateMaintenanceDTO createRequest);

    ResponseMaintenanceDTO updateMaintenance(Long id, UpdateMaintenanceDTO updateRequest);

    ResponseMaintenanceDTO deleteMaintenance(Long id);
}
