package uni.fmi.car_management_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "garage")
public class Garage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false)
    private String city;

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
