package uni.fmi.car_management_api.model.dto.response;

import java.time.LocalDate;

public class ResponseMaintenanceDTO {
    private Long id;
    private Long carId;
    private String carName;
    private String serviceType;
    private LocalDate scheduledDate;
    private Long garageId;
    private String garageName;

    public ResponseMaintenanceDTO(Long id, Long carId, String carName, String serviceType, LocalDate scheduledDate, Long garageId, String garageName) {
        this.id = id;
        this.carId = carId;
        this.carName = carName;
        this.serviceType = serviceType;
        this.scheduledDate = scheduledDate;
        this.garageId = garageId;
        this.garageName = garageName;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getGarageName() {
        return garageName;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGarageId() {
        return garageId;
    }

    public void setGarageId(Long garageId) {
        this.garageId = garageId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
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
