package com.garage.controllers;

import com.garage.dto.GarageConfigDTO;
import com.garage.dto.ResponseDTO;
import com.garage.exceptions.GarageConfigurationException;
import com.garage.models.Garage;
import com.garage.services.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
@RestController
public class GarageAdministrationController {

    @Autowired
    private GarageService garageService;

    @RequestMapping(value = "/garage", method = PUT)
    public ResponseDTO addGarage(@Validated GarageConfigDTO garageConfigDTO) throws GarageConfigurationException {
        Garage garage = garageService
                .addGarage(garageConfigDTO.getNumberOfLevels(), garageConfigDTO.getLotsOnLevel());

        return new ResponseDTO<>("complete", garage);
    }



}
