package com.garage.test.services;

import com.garage.exceptions.*;
import com.garage.models.Garage;
import com.garage.models.Level;
import com.garage.models.ParkingLot;
import com.garage.models.Vehicle;
import com.garage.services.GarageService;
import org.dbunit.DatabaseUnitException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static com.garage.test.utils.fixtures.GarageFixtures.*;
import static com.garage.test.utils.fixtures.VehicleFixtures.getCar1InGarage1Level1;
import static com.garage.test.utils.fixtures.VehicleFixtures.getNewCar;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public class GarageServiceTests extends AbstractServiceTest {

    @Before
    public void setUp() throws DatabaseUnitException, SQLException, FileNotFoundException {
        dbUnit.deleteAllFixtures();

        dbUnit.insertGarages();
        dbUnit.insertVehicles();
        dbUnit.insertLevels();
        dbUnit.insertParkingLots();
    }

    @Autowired
    private GarageService garageService;

    @Test
    public void addGarage_initialConfig_garageAdded() throws GarageConfigurationException {
        Garage garage = getNewGarage();

        garage = garageService.addGarage(garage.getNumberOfLevels(), garage.getLotsOnLevel());
        assertNotNull(garage.getId());
        assertThat(garage.getLotsOnLevel(), greaterThanOrEqualTo(1));
        assertThat(garage.getNumberOfLevels(), greaterThanOrEqualTo(1));
        assertThat(garage.getLevels(), hasSize(4));
        for (Level level : garage.getLevels()) {
            assertThat(level.getGarage(), is(garage));
        }
    }

    @Test
    public void checkIn_vehicleLicence_parkingLotReturned() throws NoParkingLotsAreAvailableException, VehicleAlreadyInGarageException, GarageNotFoundException {
        Vehicle car = getNewCar();
        ParkingLot parkingLot = garageService.checkIn(car, getGarage1());

        assertNotNull(parkingLot.getId());
        assertNotNull(parkingLot.getNumber());
        assertNotNull(parkingLot.getLevel());
        assertThat(parkingLot.getVehicle(), is(car));
    }

    @Test(expected = VehicleAlreadyInGarageException.class)
    public void checkIn_vehicleAlreadyInGarage_exceptionThrown() throws NoParkingLotsAreAvailableException, VehicleAlreadyInGarageException, GarageNotFoundException {
        garageService.checkIn(getCar1InGarage1Level1(), getGarage1());
    }

    @Test
    public void checkOut_vehicleIsInGarage_vehicleCheckedOut() throws VehicleNotFoundException {
        garageService.checkOut(getCar1InGarage1Level1());
    }

    @Test(expected = VehicleNotFoundException.class)
    public void checkOut_vehicleIsNotInGarage_exceptionThrown() throws VehicleNotFoundException {
        garageService.checkOut(getNewCar());
    }

    @Test
    public void getEmptyParkingLots_garageWith1Vehicle_emptyParkingLotsReturned() throws NoParkingLotsAreAvailableException, GarageNotFoundException {
        Garage garage2 = getGarage2();
        List<ParkingLot> emptyParkingLots = garageService.getAvailableParkingLots(garage2);
        Level levelWithVehicle = new Level(1, garage2);
        ParkingLot parkingLotWithCar = new ParkingLot(1, levelWithVehicle);

        assertThat(emptyParkingLots, hasSize(98));
        assertThat(emptyParkingLots, not(contains(parkingLotWithCar)));
    }

    @Test(expected = NoParkingLotsAreAvailableException.class)
    public void getEmptyParkingLots_filledGarage_exceptionThrown() throws NoParkingLotsAreAvailableException, GarageNotFoundException {
        garageService.getAvailableParkingLots(getFullGarage());
    }

    @Test
    public void changeGarageConfiguration_emptyGarage_numberChanged() throws GarageModificationException, GarageNotFoundException, GarageConfigurationException {
        Garage garage1 = getGarage1();
        int newNumberOfLots = 99;
        int numberOfLevels = 5;

        garage1 = garageService.changeGarageConfiguration(garage1, newNumberOfLots, numberOfLevels);
        assertThat(garage1.getLotsOnLevel(), is(newNumberOfLots));
        assertThat(garage1.getNumberOfLevels(), is(numberOfLevels));
    }

    @Test(expected = GarageModificationException.class)
    public void changeGarageConfiguration_nonEmptyGarage_exceptionThrown() throws GarageModificationException, GarageNotFoundException, GarageConfigurationException {
        garageService.changeGarageConfiguration(getGarage2(), 99, 2);
    }

    @Test
    public void findGarage_existentGarage_garageReturned() throws GarageNotFoundException {
        Garage garage = garageService.findGarage(getGarage1());

        assertNotNull(garage.getId());
    }

    @Test(expected = GarageNotFoundException.class)
    public void findGarage_nonExistentGarage_exceptionThrown() throws GarageNotFoundException {
        garageService.findGarage(getNewGarage());
    }

    @Test
    public void applyConfiguration_validConfig_configuredGarageReturned() throws GarageConfigurationException {
        Garage garage = getNewGarage();
        int numberOfLevels = 5;
        int numberOfLotsOnLevel = 5;

        garage = garageService.applyConfiguration(garage, numberOfLotsOnLevel, numberOfLevels);
        assertThat(garage.getNumberOfLevels(), is(numberOfLevels));
        assertThat(garage.getLotsOnLevel(), is(numberOfLotsOnLevel));
    }

    @Test(expected = GarageConfigurationException.class)
    public void applyConfiguration_invalidConfig_exceptionThrown() throws GarageConfigurationException {
        garageService.applyConfiguration(getNewGarage(), -1, 0);
    }

    @Test
    public void findVehicle_vehicleInGarage_vehicleReturned() throws VehicleNotFoundException {
        Vehicle carInGarage = getCar1InGarage1Level1();
        Vehicle vehicle = garageService.findVehicle(carInGarage);

        assertThat(vehicle.getId(), is(carInGarage.getId()));
        assertThat(vehicle.getLicense(), is(carInGarage.getLicense()));
        assertThat(vehicle.getType(), is(carInGarage.getType()));
    }

    @Test(expected = VehicleNotFoundException.class)
    public void findVehicle_vehicleNotInGarage_exceptionThrown() throws VehicleNotFoundException {
        Vehicle newCar = getNewCar();

        newCar.setId(-456l);
        garageService.findVehicle(newCar);
    }
}
