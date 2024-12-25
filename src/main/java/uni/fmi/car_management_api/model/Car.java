package uni.fmi.car_management_api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String make;
    private String model;
    private Integer productionYear;
    private String licensePlate;

    @ElementCollection
    private List<Long> garageIds;

    public Car(){}

    public Car(Long id, String make, String model, Integer productionYear, String licensePlate, List<Long> garageIds) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.productionYear = productionYear;
        this.licensePlate = licensePlate;
        this.garageIds = garageIds;
    }

    public Car(String make, String model, Integer productionYear, String licensePlate, List<Long> garageIds) {
        this.make = make;
        this.model = model;
        this.productionYear = productionYear;
        this.licensePlate = licensePlate;
        this.garageIds = garageIds;
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

    public List<Long> getGarageIds() {
        return garageIds;
    }

    public void setGarageIds(List<Long> garageIds) {
        this.garageIds = garageIds;
    }
}
