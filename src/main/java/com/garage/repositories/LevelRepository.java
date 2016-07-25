package com.garage.repositories;

import com.garage.models.Level;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public interface LevelRepository extends PagingAndSortingRepository<Level, Long> {
}
