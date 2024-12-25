package uni.fmi.car_management_api.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uni.fmi.car_management_api.dao.GarageRepository;
import uni.fmi.car_management_api.model.Garage;
import uni.fmi.car_management_api.model.dto.request.CreateGarageDTO;
import uni.fmi.car_management_api.model.dto.request.UpdateGarageDTO;
import uni.fmi.car_management_api.model.dto.response.ResponseGarageDTO;
import uni.fmi.car_management_api.service.GarageService;

import java.util.List;
import java.util.Optional;

@Service
public class GarageServiceImpl implements GarageService {

    @Autowired
    private GarageRepository garageRepository;

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
}
