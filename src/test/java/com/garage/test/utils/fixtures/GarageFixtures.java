package com.garage.test.utils.fixtures;

import com.garage.models.Garage;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public class GarageFixtures {

    public static Garage getNewGarage() {
        return new Garage(4, 50);
    }

    public static Garage getGarage1() {
        Garage garage = new Garage(1, 50);

        garage.setId(1L);
        return garage;
    }

    public static Garage getGarage2() {
        Garage garage = new Garage(3, 33);

        garage.setId(2L);
        return garage;
    }

    public static Garage getFullGarage() {
        Garage garage = new Garage(1, 1);

        garage.setId(3L);
        return garage;
    }
}
