package com.tat.ec.explorecali.web;

import java.util.AbstractMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tat.ec.explorecali.domain.Tour;
import com.tat.ec.explorecali.domain.TourRating;
import com.tat.ec.explorecali.domain.TourRatingPk;
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
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void createTourRating(@PathVariable(value="tourId") 
	                             int tourId,
			                     @RequestBody 
			                     @Validated 
			                     RatingDto ratingDto){
		Tour tour = verifyTour(tourId);
		tourRatingRepository.save(new TourRating(new TourRatingPk(tour, ratingDto.getCustomerId()), ratingDto.getScore(),  ratingDto.getComment()));
	
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public Page<RatingDto> getAllRatingsForTour(@PathVariable(value="tourId") 
    											int tourId, Pageable pageable){
		Tour tour = verifyTour(tourId);
		Page<TourRating> tourRatingPage=tourRatingRepository.findByPkTourId(tourId, pageable);
		List<RatingDto> ratingDtoList = tourRatingPage.getContent().stream()
				.map(tourRating -> toDto(tourRating)).collect(Collectors.toList());
		return new PageImpl<RatingDto>(ratingDtoList, pageable, tourRatingPage.getTotalPages());
	}

	@RequestMapping(method=RequestMethod.GET, path="/average")
	public AbstractMap.SimpleEntry<String, Double> getAverage(@PathVariable(value="tourId") 
    											int tourId){
		Tour tour = verifyTour(tourId);
		List<TourRating> tourRatings= tourRatingRepository.findByPkTourId(tourId);
		OptionalDouble average = tourRatings.stream().mapToInt(TourRating::getScore).average();
		return new AbstractMap.SimpleEntry<String, Double>("Average",average.isPresent() ? average.getAsDouble() : null);
	}
	
	@RequestMapping(method= RequestMethod.PUT)
	public RatingDto updateWithPut(@PathVariable(value="tourId") 
									int tourId,  
									@RequestBody 
									@Validated 
									RatingDto ratingDto){
		TourRating tourRating = verifyTourRating(tourId, ratingDto.getCustomerId());
		tourRating.setScore(ratingDto.getScore());
		tourRating.setComment(ratingDto.getComment());
		return toDto(tourRatingRepository.save(tourRating));
	}
	
	@RequestMapping(method= RequestMethod.PATCH)
	public RatingDto updateWithPatch(@PathVariable(value="tourId") 
									int tourId,  
									@RequestBody 
									@Validated 
									RatingDto ratingDto){
		TourRating tourRating = verifyTourRating(tourId, ratingDto.getCustomerId());
		if(ratingDto.getScore() != null){
			tourRating.setScore(ratingDto.getScore());
		}
		if(ratingDto.getComment() != null){
			tourRating.setComment(ratingDto.getComment());
		}
		return toDto(tourRatingRepository.save(tourRating));
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path="/{customerId}")
	public void deleteTourRating(@PathVariable(value="tourId") 
								int tourId, 
								@PathVariable(value="customerId") 
								int customerId){
		TourRating tourRating = verifyTourRating(tourId, customerId);
		tourRatingRepository.delete(tourRating);
	}
	
	private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException{
		TourRating rating = tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId);
		if(rating == null) {
			throw new NoSuchElementException("Tour Rating for request("+tourId+" for customer ID "+customerId);
		}
		return rating;
	}
	

	
	private Tour verifyTour(int tourId) throws NoSuchElementException{
		Optional<Tour> tour = tourRepository.findById(tourId);
		if(tour.isPresent()){
			return tour.get(); 
		}else{
			throw new NoSuchElementException("Tour does not exist " + tourId); 
		}
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public String return400(NoSuchElementException ex){
		return ex.getMessage();
	}
}
