package uni.fmi.car_management_api.service;

import uni.fmi.car_management_api.model.dto.request.CreateCarDTO;
import uni.fmi.car_management_api.model.dto.request.UpdateCarDTO;
import uni.fmi.car_management_api.model.dto.response.ResponseCarDTO;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<ResponseCarDTO> getAllCars(Optional<String> make, Optional<Long> garageId,
                                            Optional<Integer> startYear, Optional<Integer> endYear);

    ResponseCarDTO getCar(Long id);

    ResponseCarDTO createNewCar(CreateCarDTO createRequest);

    ResponseCarDTO updateCar(Long id, UpdateCarDTO updateRequest);

    ResponseCarDTO deleteCar(Long id);
}
