package com.tat.ec.explorecali.repo;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tat.ec.explorecali.domain.TourRating;
import com.tat.ec.explorecali.domain.TourRatingPk;

@RepositoryRestResource(exported= false)
public interface TourRatingRepository extends CrudRepository<TourRating, TourRatingPk>{

	//lookup all tour ratings for a tour
	List<TourRating> findByPkTourId(Integer tourId);
	
	Page<TourRating> findByPkTourId(Integer tourId, Pageable pageable);
	
	//Lookup a tour Rating by TourId and customerId
	TourRating findByPkTourIdAndPkCustomerId(Integer tourId, Integer CustomerId);
}
