package com.tat.ec.explorecali.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tat.ec.explorecali.domain.TourPackage;
import com.tat.ec.explorecali.repo.TourPackageRepository;

@Service
public class TourPackageService {

	private TourPackageRepository tourPackageRepository;

	@Autowired
	public TourPackageService(TourPackageRepository tourPackageRepository) {
		super();
		this.tourPackageRepository = tourPackageRepository;
	}
	
    public TourPackage createTourPackage(String code, String name ){
    	TourPackage tourPackage =new TourPackage(code, name);
    	if(!tourPackageRepository.existsById(code)){
    		tourPackageRepository.save(tourPackage);
    	}
    	return null;
    } 
    
    public Iterable<TourPackage> lookup(){
    	return tourPackageRepository.findAll();
    }
    
    public long total(){
    	return tourPackageRepository.count();
    }
    
}
