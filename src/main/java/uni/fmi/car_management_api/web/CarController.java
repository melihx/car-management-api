package uni.fmi.car_management_api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.fmi.car_management_api.model.dto.request.CreateCarDTO;
import uni.fmi.car_management_api.model.dto.request.CreateGarageDTO;
import uni.fmi.car_management_api.model.dto.request.UpdateCarDTO;
import uni.fmi.car_management_api.model.dto.request.UpdateGarageDTO;
import uni.fmi.car_management_api.model.dto.response.ResponseCarDTO;
import uni.fmi.car_management_api.model.dto.response.ResponseGarageDTO;
import uni.fmi.car_management_api.service.CarService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/")
    public ResponseEntity<List<ResponseCarDTO>> getCars(
            @RequestParam(value = "make", required = false) String filterMake,
            @RequestParam(value = "garageId", required = false) Long filterGarageId,
            @RequestParam(value = "startYear", required = false) Integer filterStartYear,
            @RequestParam(value = "endYear", required = false) Integer filterEndYear) {
        List<ResponseCarDTO> resp = carService.getAllCars(Optional.ofNullable(filterMake),
                Optional.ofNullable(filterGarageId), Optional.ofNullable(filterStartYear),
                Optional.ofNullable(filterEndYear));
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCarDTO> getCar(@PathVariable("id") Long id) {
        ResponseCarDTO resp = carService.getCar(id);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseCarDTO> createCar(@RequestBody CreateCarDTO request) {
        ResponseCarDTO resp = carService.createNewCar(request);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseCarDTO> updateCar(@PathVariable("id") Long id, @RequestBody UpdateCarDTO request) {
        ResponseCarDTO resp = carService.updateCar(id, request);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseCarDTO> deleteCar(@PathVariable("id") Long id) {
        ResponseCarDTO resp = carService.deleteCar(id);
        return ResponseEntity.ok(resp);
    }
}
