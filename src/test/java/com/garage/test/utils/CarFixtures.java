package com.garage.test.utils;

import com.garage.models.Vehicle;
import com.garage.models.VehicleType;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public class CarFixtures {

    public static Vehicle getNewCar() {
        return new Vehicle("BC-998877", VehicleType.CAR);
    }

    public static Vehicle getCar1InGarage1Level1() {
        Vehicle vehicle = new Vehicle("PL-3322445", VehicleType.CAR);

        vehicle.setId(1L);
        return vehicle;
    }

    public static Vehicle getMotorcycle1InGarage2Level1() {
        Vehicle vehicle = new Vehicle("GR-1111111", VehicleType.MOTORCYCLE);

        vehicle.setId(2L);
        return vehicle;
    }
}
