package uni.fmi.car_management_api.service.implementation;

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
import uni.fmi.car_management_api.model.dto.response.MonthlyRequestsReportDTO;
import uni.fmi.car_management_api.model.dto.response.ResponseMaintenanceDTO;
import uni.fmi.car_management_api.service.MaintenanceService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
        Car car = carRepository.findById(maintenance.getCar().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found!"));

        Garage garage = garageRepository.findById(maintenance.getGarage().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garage not found!"));

        return new ResponseMaintenanceDTO(maintenance.getId(), maintenance.getCar().getId(), car.getMake(),
                maintenance.getServiceType(), maintenance.getScheduledDate(), maintenance.getGarage().getId(),
                garage.getName());
    }

    /*private Maintenance mapCreateRequestToMaintenance(CreateMaintenanceDTO request) {
        return new Maintenance(request.getCarId(), request.getServiceType(), request.getScheduledDate(),
                request.getGarageId());
    }*/

    @Override
    public List<ResponseMaintenanceDTO> getAllMaintenances(Optional<Long> carId, Optional<Long> garageId, Optional<String> startDateString, Optional<String> endDateString) {
        List<Maintenance> response;

        if (carId.isEmpty() && garageId.isEmpty() && startDateString.isEmpty() && endDateString.isEmpty()) {
            response = maintenanceRepository.findAll();
        } else {
            LocalDate startDate = null;
            LocalDate endDate = null;

            if (startDateString.isPresent()) {
                try {
                    startDate = LocalDate.parse(startDateString.get());
                } catch (DateTimeParseException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid start date!");
                }
            }

            if (endDateString.isPresent()) {
                try {
                    endDate = LocalDate.parse(endDateString.get());
                } catch (DateTimeParseException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid end date!");
                }
            }

            if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date cannot be after end date!");
            }

            response = maintenanceRepository.findAllByFilters(
                    carId.orElse(null),
                    garageId.orElse(null),
                    startDate,
                    endDate
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
        Optional<Garage> garageFound = garageRepository.findById(createRequest.getGarageId());
        if (garageFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Garage not found!");
        }
        Garage garage = garageFound.get();

        Optional<Car> carFound = carRepository.findById(createRequest.getCarId());
        if (carFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found!");
        }
        Car car = carFound.get();

        List<Maintenance> maintenancesFound = maintenanceRepository.findByGarageId(createRequest.getGarageId());

        int maintenanceCount = (int) maintenancesFound.stream()
                    .filter(maintenance -> maintenance.getScheduledDate().equals(createRequest.getScheduledDate()))
                    .count();

        if(garage.getCapacity() - maintenanceCount <= 0) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "No capacity left in the garage!");
        }

        Maintenance newMaintenance = new Maintenance(car, createRequest.getServiceType(), createRequest.getScheduledDate(),
                garage);
        newMaintenance = maintenanceRepository.save(newMaintenance);
        return mapMaintenanceToResponse(newMaintenance);
    }

    @Override
    public ResponseMaintenanceDTO updateMaintenance(Long id, UpdateMaintenanceDTO updateRequest) {
        Optional<Maintenance> maintenanceFound = maintenanceRepository.findById(id);
        if (!maintenanceFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Maintenance not found!");
        }

        Optional<Garage> garageFound = garageRepository.findById(updateRequest.getCarId());
        if (garageFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Garage not found!");
        }
        Garage garage = garageFound.get();

        Optional<Car> carFound = carRepository.findById(updateRequest.getCarId());
        if (carFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found!");
        }
        Car car = carFound.get();

        List<Maintenance> maintenancesFound = maintenanceRepository.findByGarageId(updateRequest.getGarageId());

        int maintenanceCount = (int) maintenancesFound.stream()
                .filter(maintenance -> maintenance.getScheduledDate().equals(updateRequest.getScheduledDate()))
                .count();

        if(garage.getCapacity() - maintenanceCount <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "No capacity left in the garage!");
        }

        Maintenance maintenance = maintenanceFound.get();
        maintenance.setCar(car);
        maintenance.setServiceType(updateRequest.getServiceType());
        maintenance.setScheduledDate(updateRequest.getScheduledDate());
        maintenance.setGarage(garage);
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

    @Override
    public List<MonthlyRequestsReportDTO> getMonthlyRequestsReport(Long id, String startMonthString, String endMonthString) {
        Optional<Garage> garageFound = garageRepository.findById(id);
        if (garageFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Garage not found!");
        }
        Garage garage = garageFound.get();

        List<Maintenance> maintenancesFound = maintenanceRepository.findByGarageId(id);
        if (maintenancesFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requests not found for this garage!");
        }

        YearMonth startMonth;
        try {
            startMonth = YearMonth.parse(startMonthString);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid start month!");
        }
        YearMonth endMonth;
        try {
            endMonth = YearMonth.parse(endMonthString);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid end month!");
        }

        if (startMonth.isAfter(endMonth)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start month cannot be after end month!");
        }

        List<MonthlyRequestsReportDTO> reportList = new ArrayList<>();

        for(YearMonth month = startMonth; !month.isAfter(endMonth); month = month.plusMonths(1)){
            YearMonth currentMonth = month;
            int maintenanceCount = (int) maintenancesFound.stream()
                    .filter(maintenance -> YearMonth.from(maintenance.getScheduledDate()).equals(currentMonth))
                    .count();

                MonthlyRequestsReportDTO report = new MonthlyRequestsReportDTO(
                        month, maintenanceCount);
                reportList.add(report);
        }

        return reportList;
    }
}
