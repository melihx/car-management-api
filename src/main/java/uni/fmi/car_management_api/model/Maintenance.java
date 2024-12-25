package uni.fmi.car_management_api.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "maintenance")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Long carId;
    @Column(nullable = false)
    private String serviceType;
    @Column(nullable = false)
    private LocalDate scheduledDate;
    @Column(nullable = false)
    private Long garageId;

    public Maintenance(Long id, Long carId, String carName, String serviceType, LocalDate scheduledDate, Long garageId, String garageName) {
        this.id = id;
        this.carId = carId;
        this.serviceType = serviceType;
        this.scheduledDate = scheduledDate;
        this.garageId = garageId;
    }

    public Maintenance(Long carId, String serviceType, LocalDate scheduledDate, Long garageId) {
        this.carId = carId;
        this.serviceType = serviceType;
        this.scheduledDate = scheduledDate;
        this.garageId = garageId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getGarageId() {
        return garageId;
    }

    public void setGarageId(Long garageId) {
        this.garageId = garageId;
    }
}
