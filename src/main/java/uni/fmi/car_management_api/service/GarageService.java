package uni.fmi.car_management_api.service;

import uni.fmi.car_management_api.model.dto.request.CreateGarageDTO;
import uni.fmi.car_management_api.model.dto.request.UpdateGarageDTO;
import uni.fmi.car_management_api.model.dto.response.ResponseGarageDTO;

import java.util.List;
import java.util.Optional;

public interface GarageService {
    List<ResponseGarageDTO> getAllGarages(Optional<String> city);

    ResponseGarageDTO getGarage(Long id);

    ResponseGarageDTO createNewGarage(CreateGarageDTO createRequest);

    ResponseGarageDTO updateGarage(Long id, UpdateGarageDTO updateRequest);

    ResponseGarageDTO deleteGarage(Long id);
}
