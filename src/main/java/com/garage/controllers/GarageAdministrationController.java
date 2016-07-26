package com.garage.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garage.dto.*;
import com.garage.exceptions.*;
import com.garage.models.Garage;
import com.garage.models.ParkingLot;
import com.garage.services.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.garage.dto.ResponseDTO.getResponseComplete;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
@RestController
public class GarageAdministrationController extends AbstractController {

    @Autowired
    private GarageService garageService;

    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping(value = "/garage", method = POST)
    public ResponseDTO addGarage(@Validated @RequestBody GarageCreateDTO garageCreateDTO) throws GarageConfigurationException {
        Garage garage = garageService
                .addGarage(garageCreateDTO.getNumberOfLevels(), garageCreateDTO.getLotsOnLevel());

        return getResponseComplete(new GarageConfigDTO(garage));
    }

    @RequestMapping(value = "/garage/config", method = POST)
    public ResponseDTO changeGarageConfiguration(@Validated @RequestBody GarageConfigDTO garageConfigDTO) throws GarageConfigurationException, GarageNotFoundException, GarageModificationException {
        Garage garage = garageConfigDTO.getGarage();
        Integer lotsOnLevel = garageConfigDTO.getLotsOnLevel();
        Integer numberOfLevels = garageConfigDTO.getNumberOfLevels();

        garage = garageService.changeGarageConfiguration(garage, lotsOnLevel, numberOfLevels);
        return getResponseComplete(new GarageConfigDTO(garage));
    }

    @RequestMapping(value = "/garage/available-parking-lots", method = GET)
    public ResponseDTO getAvailableParkingLots(@Validated Garage garage) throws GarageNotFoundException, NoParkingLotsAreAvailableException {
        List<ParkingLot> parkingLots = garageService.getAvailableParkingLots(garage);
        List<ParkingLotDTO> parkingLotDTOs = new ArrayList<>(parkingLots.size());

        for (ParkingLot parkingLot : parkingLots) {
            parkingLotDTOs.add(new ParkingLotDTO(parkingLot));
        }
        return getResponseComplete(parkingLotDTOs);
    }

    @RequestMapping(value = "/check-in", method = POST)
    public ResponseDTO checkIn(@Validated @RequestBody CheckInDTO checkInDTO) throws GarageNotFoundException, NoParkingLotsAreAvailableException, VehicleAlreadyInGarageException {
        ParkingLot parkingLot = garageService.checkIn(checkInDTO.getVehicle(), checkInDTO.getGarage());

        return getResponseComplete(new ParkingLotDTO(parkingLot));
    }

    @RequestMapping(value = "/check-out", method = POST)
    public ResponseDTO checkOut(@Validated @RequestBody CheckOutDTO checkOutDTO) throws VehicleNotFoundException {
        garageService.checkOut(checkOutDTO.getVehicle());
        return getResponseComplete(null);
    }

    @RequestMapping(value = "/vehicle", method = GET)
    public ResponseDTO getVehicle(@Validated VehicleDTO vehicleDTO) throws VehicleNotFoundException {
        ParkingLotDTO parkingLotDTO = new ParkingLotDTO();

        parkingLotDTO.setParkingLot(garageService.findVehicle(vehicleDTO.getVehicle()).getParkingLot());
        return getResponseComplete(parkingLotDTO);
    }
}
