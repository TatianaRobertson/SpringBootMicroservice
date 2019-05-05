package com.tat.ec.explorecali.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tat.ec.explorecali.domain.Tour;

public interface TourRepository extends CrudRepository<Tour, Integer>{

	List<Tour> findByTourPackageCode(@Param("code") String code);
}
