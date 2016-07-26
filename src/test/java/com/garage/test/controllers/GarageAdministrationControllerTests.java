package com.garage.test.controllers;

import com.garage.dto.*;
import com.garage.exceptions.*;
import com.garage.models.Garage;
import com.garage.models.VehicleType;
import org.dbunit.DatabaseUnitException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static com.garage.test.utils.fixtures.GarageFixtures.*;
import static com.garage.test.utils.fixtures.VehicleFixtures.getCar1InGarage1Level1;
import static com.garage.test.utils.fixtures.VehicleFixtures.getNewCar;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GarageAdministrationControllerTests extends AbstractControllerTest {

    public static final String $_STATUS = "$.status";
    public static final String $_MESSAGE = "$.message";

    @Before
    public void setUp() throws DatabaseUnitException, SQLException, FileNotFoundException {
        dbUnit.deleteAllFixtures();

        dbUnit.insertGarages();
        dbUnit.insertVehicles();
        dbUnit.insertLevels();
        dbUnit.insertParkingLots();
    }

    @Test
    public void addGarage_validGarageConfig_garageAdded() throws Exception {
        GarageCreateDTO garageCreateDTO = new GarageCreateDTO(2, 50);

        mockMvc.perform(post("/garage")
                .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper.writeValueAsString(garageCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath($_STATUS, is("complete")))
                .andExpect(jsonPath($_MESSAGE + ".id", notNullValue()))
                .andExpect(jsonPath($_MESSAGE + ".numberOfLevels", is(garageCreateDTO.getNumberOfLevels())))
                .andExpect(jsonPath($_MESSAGE + ".lotsOnLevel", is(garageCreateDTO.getLotsOnLevel())));
    }

    @Test
    public void addGarage_inValidGarageConfig_exceptionReturned() throws Exception {
        GarageCreateDTO garageCreateDTO = new GarageCreateDTO(-2, 50);

        mockMvc.perform(post("/garage")
                .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper.writeValueAsString(garageCreateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath($_STATUS, is(GarageConfigurationException.class.getSimpleName())));

        garageCreateDTO = new GarageCreateDTO(2, null);
        mockMvc.perform(post("/garage")
                .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper.writeValueAsString(garageCreateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath($_STATUS, is(ValidationException.class.getSimpleName())));
    }

    @Test
    public void checkIn_vehicleLicenseAndTypeAndGarage_parkingLotReturned() throws Exception {
        CheckInDTO checkInDTO = new CheckInDTO(getNewCar(), getGarage1());

        mockMvc.perform(post("/check-in")
                .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper.writeValueAsString(checkInDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath($_STATUS, is("complete")))
                .andExpect(jsonPath($_MESSAGE + ".level", not(nullValue())))
                .andExpect(jsonPath($_MESSAGE + ".number", not(nullValue())));
    }

    @Test
    public void checkIn_fullGarage_exceptionReturned() throws Exception {
        CheckInDTO checkInDTO = new CheckInDTO(getNewCar(), getFullGarage());

        mockMvc.perform(post("/check-in")
                .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper.writeValueAsString(checkInDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath($_STATUS, is(NoParkingLotsAreAvailableException.class.getSimpleName())));
    }

    @Test
    public void checkIn_vehicleInGarage_exceptionReturned() throws Exception {
        CheckInDTO checkInDTO = new CheckInDTO(getCar1InGarage1Level1(), getGarage1());

        mockMvc.perform(post("/check-in")
                .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper.writeValueAsString(checkInDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath($_STATUS, is(VehicleAlreadyInGarageException.class.getSimpleName())));
    }

    @Test
    public void checkIn_nonExistentGarage_exceptionReturned() throws Exception {
        Garage garage = getNewGarage();
        CheckInDTO checkInDTO;

        garage.setId(999l);
        checkInDTO = new CheckInDTO(getNewCar(), garage);
        mockMvc.perform(post("/check-in")
                .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper.writeValueAsString(checkInDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath($_STATUS, is(GarageNotFoundException.class.getSimpleName())));
    }

    @Test
    public void checkOut_vehicleInGarage_statusCompleteReturned() throws Exception {
        CheckOutDTO checkOutDTO = new CheckOutDTO(getCar1InGarage1Level1());

        mockMvc.perform(post("/check-out")
                .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper.writeValueAsString(checkOutDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath($_STATUS, is(ResponseDTO.STATUS_COMPLETE)));
    }

    @Test
    public void checkOut_vehicleIsNotInGarage_exceptionReturned() throws Exception {
        CheckOutDTO checkOutDTO = new CheckOutDTO(getNewCar());

        mockMvc.perform(post("/check-out")
                .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper.writeValueAsString(checkOutDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath($_STATUS, is(VehicleNotFoundException.class.getSimpleName())));
    }

    @Test
    public void getAvailableParkingLots_garageId_lotsReturned() throws Exception {
        Garage garage2 = getGarage2();

        mockMvc.perform(get("/garage/available-parking-lots")
                .param("id", garage2.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath($_STATUS, is(ResponseDTO.STATUS_COMPLETE)))
                .andExpect(jsonPath($_MESSAGE + ".", hasSize(98)))
                .andExpect(jsonPath($_MESSAGE + "[0].number", notNullValue()))
                .andExpect(jsonPath($_MESSAGE + "[0].garage", notNullValue()))
                .andExpect(jsonPath($_MESSAGE + "[0].level", notNullValue()));
    }

    @Test
    public void getAvailableParkingLots_notExistentGarage_exceptionReturned() throws Exception {
        mockMvc.perform(get("/garage/available-parking-lots")
                .param("id", "-123456789"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath($_STATUS, is(GarageNotFoundException.class.getSimpleName())));
    }

    @Test
    public void findVehicle_vehicleInGarage_parkingLotReturned() throws Exception {
        String license = getCar1InGarage1Level1().getLicense();

        mockMvc.perform(get("/vehicle")
                .param("license", license))
                .andExpect(status().isOk())
                .andExpect(jsonPath($_STATUS, is(ResponseDTO.STATUS_COMPLETE)))
                .andExpect(jsonPath($_MESSAGE + ".number", is(5)))
                .andExpect(jsonPath($_MESSAGE + ".garage", is(2)))
                .andExpect(jsonPath($_MESSAGE + ".level", is(1)))
                .andExpect(jsonPath($_MESSAGE + ".vehicle.license", is(license)))
                .andExpect(jsonPath($_MESSAGE + ".vehicle.type", is(VehicleType.CAR.toString())));
    }

    @Test
    public void findVehicle_vehicleNotInGarage_parkingLotReturned() throws Exception {
        mockMvc.perform(get("/vehicle")
                .param("license", getNewCar().getLicense()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath($_STATUS, is(VehicleNotFoundException.class.getSimpleName())));
    }

    @Test
    public void changeGarageConfiguration_validNewConfig_garageChanged() throws Exception {
        GarageConfigDTO garageConfigDTO = new GarageConfigDTO();
        int numberOfLevels = 5;
        int lotsOnLevel = 100;

        garageConfigDTO.setGarage(getGarage1());
        garageConfigDTO.setNumberOfLevels(numberOfLevels);
        garageConfigDTO.setLotsOnLevel(lotsOnLevel);
        mockMvc.perform(post("/garage/config")
                .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper.writeValueAsString(garageConfigDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath($_STATUS, is(ResponseDTO.STATUS_COMPLETE)))
                .andExpect(jsonPath($_MESSAGE + ".numberOfLevels", is(numberOfLevels)))
                .andExpect(jsonPath($_MESSAGE + ".lotsOnLevel", is(lotsOnLevel)));
    }

    @Test
    public void changeGarageConfiguration_notEmptyGarage_exceptionReturned() throws Exception {
        GarageConfigDTO garageConfigDTO = new GarageConfigDTO();
        int numberOfLevels = 5;
        int lotsOnLevel = 100;

        garageConfigDTO.setGarage(getGarage2());
        garageConfigDTO.setNumberOfLevels(numberOfLevels);
        garageConfigDTO.setLotsOnLevel(lotsOnLevel);
        mockMvc.perform(post("/garage/config")
                .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper.writeValueAsString(garageConfigDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath($_STATUS, is(GarageModificationException.class.getSimpleName())));
    }

    @Test
    public void changeGarageConfiguration_notExistentGarageId_exceptionReturned() throws Exception {
        GarageConfigDTO garageConfigDTO = new GarageConfigDTO();
        Garage garage = getNewGarage();
        int numberOfLevels = 5;
        int lotsOnLevel = 100;

        garage.setId(-56l);
        garageConfigDTO.setGarage(garage);
        garageConfigDTO.setNumberOfLevels(numberOfLevels);
        garageConfigDTO.setLotsOnLevel(lotsOnLevel);
        mockMvc.perform(post("/garage/config")
                .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper.writeValueAsString(garageConfigDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath($_STATUS, is(GarageNotFoundException.class.getSimpleName())));
    }
}
