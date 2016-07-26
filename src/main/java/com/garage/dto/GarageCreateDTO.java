package com.garage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.garage.models.Garage;

import javax.validation.constraints.NotNull;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public class GarageCreateDTO {

    @JsonIgnore
    protected Garage garage = new Garage();

    public GarageCreateDTO() {
    }

    public GarageCreateDTO(Integer numberOfLevels, Integer lotsOnLevel) {
        this.garage.setNumberOfLevels(numberOfLevels);
        this.setLotsOnLevel(lotsOnLevel);
    }

    @NotNull
    public Integer getNumberOfLevels() {
        return garage.getNumberOfLevels();
    }

    public void setNumberOfLevels(Integer numberOfLevels) {
        this.garage.setNumberOfLevels(numberOfLevels);
    }

    @NotNull
    public Integer getLotsOnLevel() {
        return garage.getLotsOnLevel();
    }

    public void setLotsOnLevel(Integer lotsOnLevel) {
        garage.setLotsOnLevel(lotsOnLevel);
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }
}
