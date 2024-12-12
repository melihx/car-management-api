package uni.fmi.car_management_api.model.dto.response;

public class ResponseGarageDTO {
    private Long id;
    private String name;
    private String location;
    private Integer capacity;
    private String city;

    public ResponseGarageDTO(Long id, String name, String location, Integer capacity, String city) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.city = city;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
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
