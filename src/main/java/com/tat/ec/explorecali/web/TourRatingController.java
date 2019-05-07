package com.tat.ec.explorecali.web;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tat.ec.explorecali.domain.Tour;
import com.tat.ec.explorecali.domain.TourRating;
import com.tat.ec.explorecali.repo.TourRatingRepository;
import com.tat.ec.explorecali.repo.TourRepository;

@RestController
@RequestMapping(path="tours/{tourId}/ratings")
public class TourRatingController {

	TourRatingRepository tourRatingRepository;
	TourRepository tourRepository;
	
	@Autowired
	public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
		super();
		this.tourRatingRepository = tourRatingRepository;
		this.tourRepository = tourRepository;
	}
	public TourRatingController() {
		super();
	}
	
	private RatingDto toDto(TourRating tourRating){
		return new RatingDto(tourRating.getScore(), tourRating.getComment(), tourRating.getPk().getCustomerId());
	}
	
	private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException{
		TourRating rating = tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId);
		if(rating == null) {
			throw new NoSuchElementException("Tour Rating for request("+tourId+" for customer ID "+customerId);
		}
		return rating;
	}
	
	private Optional<Tour> verifyTour(int tourId) throws NoSuchElementException{
		Optional<Tour> tour = tourRepository.findById(tourId);
		if(tour == null){
			 throw new NoSuchElementException("Tour does not exist " + tourId);
		}
	  return tour; 
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public String return400(NoSuchElementException ex){
		return ex.getMessage();
	}
}
