package uni.fmi.car_management_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "garage")
public class Garage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Garage name cannot be null")
    @NotEmpty(message = "Garage name cannot be empty")
    @Size(min = 3, max = 100, message = "Garage name must be between 3 and 100 characters")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Location cannot be null")
    @NotEmpty(message = "Location cannot be empty")
    @Column(nullable = false)
    private String location;

    @NotNull(message = "Capacity cannot be null")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Column(nullable = false)
    private Integer capacity;

    @NotNull(message = "City cannot be null")
    @NotEmpty(message = "City cannot be empty")
    @Column(nullable = false)
    private String city;

    public Garage(){}

    public Garage(Long id, String name, String location, Integer capacity, String city) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.city = city;
    }

    public Garage(String name, String location, Integer capacity, String city) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
