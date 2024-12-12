package uni.fmi.car_management_api.model.dto.response;

import java.time.LocalDate;

public class ResponseMaintenanceDTO {
    private Long id;
    private Integer garageId;
    private Integer carId;
    private String serviceType;
    private LocalDate scheduledDate;

    public ResponseMaintenanceDTO(Long id, Integer garageId, Integer carId, String serviceType, LocalDate scheduledDate) {
        this.id = id;
        this.garageId = garageId;
        this.carId = carId;
        this.serviceType = serviceType;
        this.scheduledDate = scheduledDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGarageId() {
        return garageId;
    }

    public void setGarageId(Integer garageId) {
        this.garageId = garageId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
}