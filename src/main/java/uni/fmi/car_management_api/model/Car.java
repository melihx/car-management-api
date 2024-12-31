package uni.fmi.car_management_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Make cannot be null")
    @NotEmpty(message = "Make cannot be empty")
    @Size(min = 2, max = 50, message = "Make must be between 2 and 50 characters")
    private String make;


    @NotNull(message = "Model cannot be null")
    @NotEmpty(message = "Model cannot be empty")
    @Size(min = 2, max = 50, message = "Model must be between 2 and 50 characters")
    private String model;

    @NotNull(message = "Production year cannot be null")
    @Min(value = 1900, message = "Production year must be after 1900")
    @Max(value = 2026, message = "Production year must be before 2025")
    private Integer productionYear;

    @NotNull(message = "License plate cannot be null")
    @NotEmpty(message = "License plate cannot be empty")
    @Pattern(regexp = "^[A-Z]{1,2}\\s?[0-9]{1,4}\\s?[A-Z]{1,2}$", message = "Incorrect license plate format!")
    private String licensePlate;



    @ManyToMany
    @JoinTable(
            name = "car_garage",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "garage_id")
    )
    private List<Garage> garages;

    public Car(){}

    public Car(String make, String model, Integer productionYear, String licensePlate, List<Garage> garages) {
        this.make = make;
        this.model = model;
        this.productionYear = productionYear;
        this.licensePlate = licensePlate;
        this.garages = garages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public List<Garage> getGarages() {
        return garages;
    }

    public void setGarages(List<Garage> garages) {
        this.garages = garages;
    }
}
