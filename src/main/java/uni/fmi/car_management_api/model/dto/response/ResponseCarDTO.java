package uni.fmi.car_management_api.model.dto.response;

import java.util.List;

public class ResponseCarDTO {
    private Long id;
    private String make;
    private String model;
    private Integer productionYear;
    private String licensePlate;
    private List<ResponseGarageDTO> garages;

    public ResponseCarDTO(Long id, String make, String model, Integer productionYear, String licensePlate, List<ResponseGarageDTO> garages) {
        this.id = id;
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

    public List<ResponseGarageDTO> getGarages() {
        return garages;
    }

    public void setGarages(List<ResponseGarageDTO> garages) {
        this.garages = garages;
    }
}
