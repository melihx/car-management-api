package uni.fmi.car_management_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "maintenance")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id", nullable = false)
    private Car car;

    @Column(nullable = false)
    private String serviceType;

    @Column(nullable = false)
    @NotNull(message = "Scheduled date cannot be null")
    @Future(message = "Scheduled date must be in the future")
    private LocalDate scheduledDate;

    @ManyToOne
    @JoinColumn(name = "garage_id", referencedColumnName = "id", nullable = false)
    private Garage garage;

    public Maintenance(){}

    public Maintenance(Car car, String serviceType, LocalDate scheduledDate, Garage garage) {
        this.car = car;
        this.serviceType = serviceType;
        this.scheduledDate = scheduledDate;
        this.garage = garage;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
