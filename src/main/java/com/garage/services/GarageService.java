package com.garage.services;

import com.garage.exceptions.*;
import com.garage.models.Garage;
import com.garage.models.Level;
import com.garage.models.ParkingLot;
import com.garage.models.Vehicle;
import com.garage.repositories.GarageRepository;
import com.garage.repositories.LevelRepository;
import com.garage.repositories.ParkingLotRepository;
import com.garage.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
@Service
public class GarageService {

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private LevelRepository levelRepository;

    public Garage addGarage(Integer numberOfLevels, Integer lotsOnLevel) throws GarageConfigurationException {
        List<Level> levels = new ArrayList<>();
        Garage garage = new Garage();

        applyConfiguration(garage, lotsOnLevel, numberOfLevels);
        garage = garageRepository.save(garage);
        for (int i = 0; i < garage.getNumberOfLevels(); i++) {
            levels.add(new Level(i + 1, garage));
        }
        levels = (List<Level>) levelRepository.save(levels);
        garage.setLevels(levels);
        return garage;
    }

    public ParkingLot checkIn(Vehicle vehicle, Garage garage) throws NoParkingLotsAreAvailableException, VehicleAlreadyInGarageException, GarageNotFoundException {
        ParkingLot parkingLot;

        if (vehicleRepository.findByLicense(vehicle.getLicense()) != null) {
            throw new VehicleAlreadyInGarageException();
        }
        parkingLot = getAvailableParkingLots(garage).get(0);
        vehicle = vehicleRepository.save(vehicle);
        parkingLot.setVehicle(vehicle);
        return parkingLotRepository.save(parkingLot);
    }

    public List<ParkingLot> getAvailableParkingLots(Garage garage) throws NoParkingLotsAreAvailableException, GarageNotFoundException {
        List<ParkingLot> emptyParkingLots = new ArrayList<>();
        List<Level> levels;

        garage = findGarage(garage);
        levels = garageRepository.getAllLevelsOrderByNumberASC(garage);
        for (Level level : levels) {
            List<ParkingLot> parkingLots = level.getOccupiedParkingLots();

            for (int i = 1; i <= garage.getLotsOnLevel(); i++) {
                ParkingLot parkingLot = new ParkingLot(i, level);

                if (!parkingLots.contains(parkingLot)) {
                    emptyParkingLots.add(parkingLot);
                }
            }
        }
        if (emptyParkingLots.isEmpty()) {
            throw new NoParkingLotsAreAvailableException();
        }
        return emptyParkingLots;
    }

    public void checkOut(Vehicle vehicle) throws VehicleNotFoundException {
        vehicle = findVehicle(vehicle);
        if (vehicle == null) {
            throw new VehicleNotFoundException();
        }
        parkingLotRepository.delete(vehicle.getParkingLot());
        vehicleRepository.delete(vehicle);
    }

    public Garage changeGarageConfiguration(Garage garage, Integer numberOfLots, Integer numberOfLevels) throws GarageModificationException, GarageNotFoundException, GarageConfigurationException {
        garage = findGarage(garage);
        if (!parkingLotRepository.findAllByGarage(garage).isEmpty()) {
            throw new GarageModificationException();
        }
        applyConfiguration(garage, numberOfLots, numberOfLevels);
        return garageRepository.save(garage);
    }

    public Garage applyConfiguration(Garage garage, Integer numberOfLots, Integer numberOfLevels) throws GarageConfigurationException {
        if (numberOfLevels <= 0 || numberOfLots <= 0) {
            throw new GarageConfigurationException();
        }
        garage.setNumberOfLevels(numberOfLevels);
        garage.setLotsOnLevel(numberOfLots);
        return garage;
    }

    public Garage findGarage(Garage garage) throws GarageNotFoundException {
        Long garageId = garage.getId();

        if (garageId == null || (garage = garageRepository.findOne(garageId)) == null) {
            throw new GarageNotFoundException();
        }
        return garage;
    }

    public Vehicle findVehicle(Vehicle vehicle) throws VehicleNotFoundException {
        vehicle = vehicleRepository.findByLicense(vehicle.getLicense());
        if (vehicle == null) {
            throw new VehicleNotFoundException();
        }
        return vehicle;
    }
}
