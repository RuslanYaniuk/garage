package com.garage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.garage.models.Vehicle;
import com.garage.models.VehicleType;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public class VehicleDTO {

    @JsonIgnore
    private Vehicle vehicle = new Vehicle();

    public VehicleDTO() {
    }

    public VehicleDTO(Vehicle vehicle) {
        if (vehicle != null) {
            this.vehicle = vehicle;
        }
    }

    @NotBlank
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
        this.vehicle.setType(type);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
