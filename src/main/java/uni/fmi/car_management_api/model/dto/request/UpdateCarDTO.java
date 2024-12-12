package uni.fmi.car_management_api.model.dto.request;

import java.util.List;

public class UpdateCarDTO {
    private String make;
    private String model;
    private Integer productionYear;
    private String licensePlate;
    private List<Integer> garageIds;

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

    public List<Integer> getGarageIds() {
        return garageIds;
    }

    public void setGarageIds(List<Integer> garageIds) {
        this.garageIds = garageIds;
    }
}
