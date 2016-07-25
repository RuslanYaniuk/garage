package com.garage.repositories;

import com.garage.models.Vehicle;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, Long> {

    Vehicle findByLicense(String license);
}
