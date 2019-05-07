package com.tat.ec.explorecali.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.tat.ec.explorecali.domain.Tour;

public interface TourRepository extends PagingAndSortingRepository<Tour, Integer>{

	Page<Tour> findByTourPackageCode(@Param("code") String code, Pageable pageable );
}
