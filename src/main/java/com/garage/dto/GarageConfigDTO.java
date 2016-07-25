package com.garage.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public class GarageConfigDTO {

    @NotNull
    private Integer numberOfLevels;

    @NotNull
    private Integer lotsOnLevel;

    public GarageConfigDTO() {
    }

    public GarageConfigDTO(Integer numberOfLevels, Integer lotsOnLevel) {
        this.numberOfLevels = numberOfLevels;
        this.lotsOnLevel = lotsOnLevel;
    }

    public Integer getNumberOfLevels() {
        return numberOfLevels;
    }

    public void setNumberOfLevels(Integer numberOfLevels) {
        this.numberOfLevels = numberOfLevels;
    }

    public Integer getLotsOnLevel() {
        return lotsOnLevel;
    }

    public void setLotsOnLevel(Integer lotsOnLevel) {
        this.lotsOnLevel = lotsOnLevel;
    }
}
