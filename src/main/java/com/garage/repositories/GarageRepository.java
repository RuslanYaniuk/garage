package com.garage.repositories;

import com.garage.models.Garage;
import com.garage.models.Level;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public interface GarageRepository extends PagingAndSortingRepository<Garage, Long> {

    @Query("select lv from Level lv where lv.garage in " +
            "(select g from Garage g where g = :garage) order by lv.number asc")
    List<Level> getAllLevelsOrderByNumberASC(@Param("garage") Garage garage);
}
