package com.garage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.garage.models.Garage;
import com.garage.models.Vehicle;
import com.garage.models.VehicleType;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public class CheckInDTO {

    @JsonIgnore
    private Vehicle vehicle = new Vehicle();

    @JsonIgnore
    private Garage garage = new Garage();

    public CheckInDTO() {
    }

    public CheckInDTO(Vehicle vehicle, Garage garage) {
        this.vehicle = vehicle;
        this.garage = garage;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Garage getGarage() {
        return garage;
    }

    public String getLicense() {
        return vehicle.getLicense();
    }

    public void setLicense(String license) {
        vehicle.setLicense(license);
    }

    public VehicleType getType() {
        return vehicle.getType();
    }

    public void setType(VehicleType type) {
        vehicle.setType(type);
    }

    public void setGarageId(Long id) {
        garage.setId(id);
    }

    public Long getGarageId() {
        return garage.getId();
    }
}
