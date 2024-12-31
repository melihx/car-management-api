package uni.fmi.car_management_api.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uni.fmi.car_management_api.dao.GarageRepository;
import uni.fmi.car_management_api.dao.MaintenanceRepository;
import uni.fmi.car_management_api.model.Garage;
import uni.fmi.car_management_api.model.Maintenance;
import uni.fmi.car_management_api.model.dto.request.CreateGarageDTO;
import uni.fmi.car_management_api.model.dto.request.UpdateGarageDTO;
import uni.fmi.car_management_api.model.dto.response.GarageDailyAvailabilityReportDTO;
import uni.fmi.car_management_api.model.dto.response.ResponseGarageDTO;
import uni.fmi.car_management_api.service.GarageService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GarageServiceImpl implements GarageService {

    @Autowired
    private GarageRepository garageRepository;
    @Autowired
    private MaintenanceRepository maintenanceRepository;

    private ResponseGarageDTO mapGarageToResponse(Garage garage) {
        return new ResponseGarageDTO(garage.getId(), garage.getName(), garage.getLocation(),
                garage.getCapacity(), garage.getCity());
    }

    private Garage mapCreateRequestToGarage(CreateGarageDTO request) {
        return new Garage(request.getName(), request.getLocation(), request.getCapacity(), request.getCity());
    }

    @Override
    public List<ResponseGarageDTO> getAllGarages(Optional<String> city) {
        List<Garage> response;
        if (city.isPresent()) {
            response = garageRepository.findAllByCity(city.get());
        } else {
            response = garageRepository.findAll();
        }
        return response.stream().map(this::mapGarageToResponse).toList();
    }

    @Override
    public ResponseGarageDTO getGarage(Long id) {
        Optional<Garage> garageFound = garageRepository.findById(id);
        if (!garageFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Garage not found!");
        }
        Garage garage = garageFound.get();
        return mapGarageToResponse(garage);
    }

    @Override
    public ResponseGarageDTO createNewGarage(CreateGarageDTO createRequest) {
        Garage newGarage = mapCreateRequestToGarage(createRequest);
        newGarage = garageRepository.save(newGarage);
        return mapGarageToResponse(newGarage);
    }

    @Override
    public ResponseGarageDTO updateGarage(Long id, UpdateGarageDTO updateRequest) {
        Optional<Garage> garageFound = garageRepository.findById(id);
        if (!garageFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Garage not found!");
        }
        Garage garage = garageFound.get();
        garage.setName(updateRequest.getName());
        garage.setLocation(updateRequest.getLocation());
        garage.setCapacity(updateRequest.getCapacity());
        garage.setCity(updateRequest.getCity());
        garage = garageRepository.save(garage);
        return mapGarageToResponse(garage);
    }

    @Override
    public ResponseGarageDTO deleteGarage(Long id) {
        Optional<Garage> garageFound = garageRepository.findById(id);
        if (!garageFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Garage not found!");
        }
        Garage garage = garageFound.get();
        garageRepository.delete(garage);
        return mapGarageToResponse(garage);
    }

    @Override
    public List<GarageDailyAvailabilityReportDTO> getDailyAvailabilityReport(Long id, String startDateString, String endDateString) {
        Optional<Garage> garageFound = garageRepository.findById(id);
        if (garageFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Garage not found!");
        }
        Garage garage = garageFound.get();

        List<Maintenance> maintenancesFound = maintenanceRepository.findByGarageId(id);
        if (maintenancesFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requests not found for this garage!");
        }

        LocalDate startDate;
        try {
            startDate = LocalDate.parse(startDateString);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid start date!");
        }
        LocalDate endDate;
        try {
            endDate = LocalDate.parse(endDateString);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid end date!");
        }

        if (startDate.isAfter(endDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date cannot be after end date!");
        }

        List<GarageDailyAvailabilityReportDTO> reportList = new ArrayList<>();

        for(LocalDate date = startDate; !date.isEqual(endDate.plusDays(1)); date = date.plusDays(1)){
            LocalDate finalDate = date;
            int maintenanceCount = (int) maintenancesFound.stream()
                    .filter(maintenance -> maintenance.getScheduledDate().equals(finalDate))
                    .count();

            if(maintenanceCount>0) {
                GarageDailyAvailabilityReportDTO report = new GarageDailyAvailabilityReportDTO(
                        date, maintenanceCount, garage.getCapacity() - maintenanceCount);
                reportList.add(report);
            }
        }

        return reportList;
    }
}
