package com.tat.ec.explorecali.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tat.ec.explorecali.domain.Difficulty;
import com.tat.ec.explorecali.domain.Region;
import com.tat.ec.explorecali.domain.Tour;
import com.tat.ec.explorecali.domain.TourPackage;
import com.tat.ec.explorecali.repo.TourPackageRepository;
import com.tat.ec.explorecali.repo.TourRepository;

@Service
public class TourService {
	private TourPackageRepository tourPackageRepository;
	private TourRepository tourRepository;

	@Autowired
	public TourService(TourPackageRepository tourPackageRepository, TourRepository tourRepository) {
	    super();
	    this.tourPackageRepository = tourPackageRepository;
	    this.tourRepository = tourRepository;
	}
	
	public  Tour createTour(String title, String description, String blurb, Integer price, String duration,
			String bullets, String keywords, String tourPackageCode, Difficulty difficulty, Region region){
		
		Optional<TourPackage>  tourPackage =tourPackageRepository.findById(tourPackageCode);
		if(tourPackage == null) {
			throw new RuntimeException("Tour Package doesn't exists: "+tourPackageCode);
		}else{
			Tour tour = new Tour(title, description, blurb, price, duration,
					bullets, keywords,tourPackage, difficulty, region);
			
			return tourRepository.save(tour);
		}
	}
	
	public Iterable<Tour> lookup() {
		return tourRepository.findAll();
		
	}
	public long total() {
		return tourRepository.count();
	}

}
