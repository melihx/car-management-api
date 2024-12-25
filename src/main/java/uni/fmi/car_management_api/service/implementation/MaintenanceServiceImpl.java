package uni.fmi.car_management_api.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uni.fmi.car_management_api.dao.CarRepository;
import uni.fmi.car_management_api.dao.GarageRepository;
import uni.fmi.car_management_api.dao.MaintenanceRepository;
import uni.fmi.car_management_api.model.Car;
import uni.fmi.car_management_api.model.Garage;
import uni.fmi.car_management_api.model.Maintenance;
import uni.fmi.car_management_api.model.dto.request.CreateMaintenanceDTO;
import uni.fmi.car_management_api.model.dto.request.UpdateMaintenanceDTO;
import uni.fmi.car_management_api.model.dto.response.ResponseMaintenanceDTO;
import uni.fmi.car_management_api.service.MaintenanceService;

import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private GarageRepository garageRepository;

    private ResponseMaintenanceDTO mapMaintenanceToResponse(Maintenance maintenance) {
        Car car = carRepository.findById(maintenance.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found with ID: " + maintenance.getCarId()));

        Garage garage = garageRepository.findById(maintenance.getGarageId())
                .orElseThrow(() -> new EntityNotFoundException("Garage not found with ID: " + maintenance.getGarageId()));


        return new ResponseMaintenanceDTO(maintenance.getId(), maintenance.getCarId(), car.getMake(),
                maintenance.getServiceType(), maintenance.getScheduledDate(), maintenance.getGarageId(),
                garage.getName());
    }

    private Maintenance mapCreateRequestToMaintenance(CreateMaintenanceDTO request) {
        return new Maintenance(request.getCarId(), request.getServiceType(), request.getScheduledDate(),
                request.getGarageId());
    }

    @Override
    public List<ResponseMaintenanceDTO> getAllMaintenances(Optional<Long> carId, Optional<Long> garageId, Optional<String> startDate, Optional<String> endDate) {
        List<Maintenance> response;

        if (carId.isEmpty() && garageId.isEmpty() && startDate.isEmpty() && endDate.isEmpty()) {
            response = maintenanceRepository.findAll();
        } else {
            response = maintenanceRepository.findAllByFilters(
                    carId.orElse(null),
                    garageId.orElse(null),
                    startDate.orElse(null),
                    endDate.orElse(null)
            );
        }
        return response.stream().map(this::mapMaintenanceToResponse).toList();
    }

    @Override
    public ResponseMaintenanceDTO getMaintenance(Long id) {
        Optional<Maintenance> maintenanceFound = maintenanceRepository.findById(id);
        if (!maintenanceFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Maintenance not found!");
        }
        Maintenance maintenance = maintenanceFound.get();
        return mapMaintenanceToResponse(maintenance);
    }

    @Override
    public ResponseMaintenanceDTO createNewMaintenance(CreateMaintenanceDTO createRequest) {
        Maintenance newMaintenance = mapCreateRequestToMaintenance(createRequest);
        newMaintenance = maintenanceRepository.save(newMaintenance);
        return mapMaintenanceToResponse(newMaintenance);
    }

    @Override
    public ResponseMaintenanceDTO updateMaintenance(Long id, UpdateMaintenanceDTO updateRequest) {
        Optional<Maintenance> maintenanceFound = maintenanceRepository.findById(id);
        if (!maintenanceFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Maintenance not found!");
        }
        Maintenance maintenance = maintenanceFound.get();
        maintenance.setCarId(updateRequest.getCarId());
        maintenance.setServiceType(updateRequest.getServiceType());
        maintenance.setScheduledDate(updateRequest.getScheduledDate());
        maintenance.setGarageId(updateRequest.getGarageId());
        maintenance = maintenanceRepository.save(maintenance);
        return mapMaintenanceToResponse(maintenance);
    }

    @Override
    public ResponseMaintenanceDTO deleteMaintenance(Long id) {
        Optional<Maintenance> maintenanceFound = maintenanceRepository.findById(id);
        if (!maintenanceFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Maintenance not found!");
        }
        Maintenance maintenance = maintenanceFound.get();
        maintenanceRepository.delete(maintenance);
        return mapMaintenanceToResponse(maintenance);
    }
}
