package com.garage.repositories;

import com.garage.models.Garage;
import com.garage.models.ParkingLot;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public interface ParkingLotRepository extends PagingAndSortingRepository<ParkingLot, Long> {

    List<ParkingLot> findAllByGarage(Garage garage);
}
