package com.garage.dto;

import com.garage.models.Garage;

import javax.validation.constraints.NotNull;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public class GarageConfigDTO extends GarageCreateDTO {

    public GarageConfigDTO() {
        super();
    }

    public GarageConfigDTO(Garage garage) {
        super.garage = garage;
    }

    @NotNull
    public Long getId() {
        return garage.getId();
    }

    public void setId(Long id) {
        this.garage.setId(id);
    }
}
