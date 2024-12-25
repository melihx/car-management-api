package uni.fmi.car_management_api.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import uni.fmi.car_management_api.dao.CarRepository;
import uni.fmi.car_management_api.dao.GarageRepository;
import uni.fmi.car_management_api.model.Car;
import uni.fmi.car_management_api.model.Garage;
import uni.fmi.car_management_api.model.dto.request.CreateCarDTO;
import uni.fmi.car_management_api.model.dto.request.UpdateCarDTO;
import uni.fmi.car_management_api.model.dto.response.ResponseCarDTO;
import uni.fmi.car_management_api.model.dto.response.ResponseGarageDTO;
import uni.fmi.car_management_api.service.CarService;

import java.util.List;
import java.util.Optional;

public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private GarageRepository garageRepository;

    private ResponseGarageDTO mapGarageToResponse(Garage garage) {
        return new ResponseGarageDTO(garage.getId(), garage.getName(), garage.getLocation(),
                garage.getCapacity(), garage.getCity());
    }

    private ResponseCarDTO mapCarToResponse(Car car) {
        List<Garage> garages = garageRepository.findAllByIdIn(car.getGarageIds());

        return new ResponseCarDTO(car.getId(), car.getMake(), car.getModel(), car.getProductionYear(),
                car.getLicensePlate(), garages.stream().map(this::mapGarageToResponse).toList());
    }

    private Car mapCreateRequestToCar(CreateCarDTO request) {
        return new Car(request.getMake(), request.getModel(), request.getProductionYear(),
                request.getLicensePlate(), request.getGarageIds());
    }

    @Override
    public List<ResponseCarDTO> getAllCars(Optional<String> make, Optional<Long> garageId, Optional<Integer> startYear,
                                           Optional<Integer> endYear) {
        List<Car> response;

        if (make.isEmpty() && garageId.isEmpty() && startYear.isEmpty() && endYear.isEmpty()) {
            response = carRepository.findAll();
        } else {
            response = carRepository.findAllByFilters(
                    make.orElse(null),
                    garageId.orElse(null),
                    startYear.orElse(null),
                    endYear.orElse(null)
            );
        }
        return response.stream().map(this::mapCarToResponse).toList();
    }

    @Override
    public ResponseCarDTO getCar(Long id) {
        Optional<Car> carFound = carRepository.findById(id);
        if (!carFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found!");
        }
        Car car = carFound.get();
        return mapCarToResponse(car);
    }

    @Override
    public ResponseCarDTO createNewCar(CreateCarDTO createRequest) {
        Car newCar = mapCreateRequestToCar(createRequest);
        newCar = carRepository.save(newCar);
        return mapCarToResponse(newCar);
    }

    @Override
    public ResponseCarDTO updateCar(Long id, UpdateCarDTO updateRequest) {
        Optional<Car> carFound = carRepository.findById(id);
        if (!carFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found!");
        }
        Car car = carFound.get();
        car.setMake(updateRequest.getMake());
        car.setModel(updateRequest.getModel());
        car.setProductionYear(updateRequest.getProductionYear());
        car.setLicensePlate(updateRequest.getLicensePlate());
        car.setGarageIds(updateRequest.getGarageIds());
        car = carRepository.save(car);
        return mapCarToResponse(car);
    }

    @Override
    public ResponseCarDTO deleteCar(Long id) {
        Optional<Car> carFound = carRepository.findById(id);
        if (!carFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found!");
        }
        Car car = carFound.get();
        carRepository.delete(car);
        return mapCarToResponse(car);
    }
}
