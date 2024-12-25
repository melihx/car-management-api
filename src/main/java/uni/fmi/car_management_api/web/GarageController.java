package uni.fmi.car_management_api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.fmi.car_management_api.model.dto.request.CreateGarageDTO;
import uni.fmi.car_management_api.model.dto.request.UpdateGarageDTO;
import uni.fmi.car_management_api.model.dto.response.ResponseGarageDTO;
import uni.fmi.car_management_api.service.GarageService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/garage")
public class GarageController {

    @Autowired
    private GarageService garageService;


    @GetMapping("/")
    public ResponseEntity<List<ResponseGarageDTO>> getGarages(
            @RequestParam(value = "city", required = false) String filterCity) {
        List<ResponseGarageDTO> resp = garageService.getAllGarages(Optional.ofNullable(filterCity));
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGarageDTO> getGarage(@PathVariable("id") Long id) {
        ResponseGarageDTO resp = garageService.getGarage(id);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseGarageDTO> createGarage(@RequestBody CreateGarageDTO request) {
        ResponseGarageDTO resp = garageService.createNewGarage(request);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseGarageDTO> updateGarage(@PathVariable("id") Long id,
                                                     @RequestBody UpdateGarageDTO request) {
        ResponseGarageDTO resp = garageService.updateGarage(id, request);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseGarageDTO> deleteGarage(@PathVariable("id") Long id) {
        ResponseGarageDTO resp = garageService.deleteGarage(id);
        return ResponseEntity.ok(resp);
    }

}
