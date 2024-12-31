package uni.fmi.car_management_api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.fmi.car_management_api.model.dto.request.CreateMaintenanceDTO;
import uni.fmi.car_management_api.model.dto.request.UpdateMaintenanceDTO;
import uni.fmi.car_management_api.model.dto.response.MonthlyRequestsReportDTO;
import uni.fmi.car_management_api.model.dto.response.ResponseMaintenanceDTO;
import uni.fmi.car_management_api.service.MaintenanceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    @GetMapping("")
    public ResponseEntity<List<ResponseMaintenanceDTO>> getMaintenances(
            @RequestParam(value = "carId", required = false) Long filterCarId,
            @RequestParam(value = "garageId", required = false) Long filterGarageId,
            @RequestParam(value = "startDate", required = false) String filterStartDate,
            @RequestParam(value = "endDate", required = false) String filterEndDate) {
        List<ResponseMaintenanceDTO> resp = maintenanceService.getAllMaintenances(Optional.ofNullable(filterCarId),
                Optional.ofNullable(filterGarageId), Optional.ofNullable(filterStartDate),
                Optional.ofNullable(filterEndDate));
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMaintenanceDTO> getMaintenance(@PathVariable("id") Long id) {
        ResponseMaintenanceDTO resp = maintenanceService.getMaintenance(id);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("")
    public ResponseEntity<ResponseMaintenanceDTO> createMaintenance(@RequestBody CreateMaintenanceDTO request) {
        ResponseMaintenanceDTO resp = maintenanceService.createNewMaintenance(request);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMaintenanceDTO> updateMaintenance(@PathVariable("id") Long id,
                                                                    @RequestBody UpdateMaintenanceDTO request) {
        ResponseMaintenanceDTO resp = maintenanceService.updateMaintenance(id, request);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMaintenanceDTO> deleteMaintenance(@PathVariable("id") Long id) {
        ResponseMaintenanceDTO resp = maintenanceService.deleteMaintenance(id);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/monthlyRequestsReport")
    public ResponseEntity<List<MonthlyRequestsReportDTO>> getReport(
            @RequestParam(value = "garageId") Long garageId,
            @RequestParam(value = "startMonth")@DateTimeFormat(pattern = "yyyy-MM") String filterStartMonth,
            @RequestParam(value = "endMonth")@DateTimeFormat(pattern = "yyyy-MM") String filterEndMonth) {
        List<MonthlyRequestsReportDTO> resp =
                maintenanceService.getMonthlyRequestsReport(garageId, filterStartMonth, filterEndMonth);
        return ResponseEntity.ok(resp);
    }
}
