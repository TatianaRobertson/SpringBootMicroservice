package com.tat.ec.explorecali.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tat.ec.explorecali.domain.TourPackage;


public interface TourPackageRepository extends CrudRepository<TourPackage, String>{

	Optional<TourPackage> findByName(@Param("name") String name);
}
