package com.garage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.garage.models.Garage;
import com.garage.models.Level;
import com.garage.models.ParkingLot;
import com.garage.models.Vehicle;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public class ParkingLotDTO {

    @JsonIgnore
    private ParkingLot parkingLot = new ParkingLot();

    public ParkingLotDTO() {
    }

    public ParkingLotDTO(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public Integer getNumber() {
        return parkingLot.getNumber();
    }

    public void setNumber(Integer number) {
        parkingLot.setNumber(number);
    }

    public VehicleDTO getVehicle() {
        Vehicle vehicle = parkingLot.getVehicle();
        return vehicle != null ? new VehicleDTO(vehicle) : null;
    }

    public void setVehicle(VehicleDTO vehicleDTO) {
        this.parkingLot.setVehicle(vehicleDTO.getVehicle());
    }

    public Long getGarage() {
        return parkingLot.getGarage().getId();
    }

    public void setGarage(Garage garage) {
        parkingLot.setGarage(garage);
    }

    public Integer getLevel() {
        return parkingLot.getLevel().getNumber();
    }

    public void setLevel(Level level) {
        parkingLot.setLevel(level);
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}
