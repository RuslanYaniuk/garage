package com.garage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.garage.models.Vehicle;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public class CheckOutDTO {

    @JsonIgnore
    private Vehicle vehicle = new Vehicle();

    public CheckOutDTO() {
    }

    public CheckOutDTO(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getLicense() {
        return vehicle.getLicense();
    }

    public void setLicense(String license) {
        vehicle.setLicense(license);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
