package com.garage.test.utils;

import com.garage.models.ParkingLot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public class ParkingLotFixtures {

    public static List<ParkingLot> getNotSortedParkingLots() {
        List<ParkingLot> parkingLots = new ArrayList<>();

        parkingLots.add(new ParkingLot(5, null));
        parkingLots.add(new ParkingLot(1, null));
        parkingLots.add(new ParkingLot(8, null));
        parkingLots.add(new ParkingLot(2, null));
        parkingLots.add(new ParkingLot(9, null));
        return parkingLots;
    }
}
