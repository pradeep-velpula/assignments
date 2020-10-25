package com.provigil.surveillance.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.provigil.surveillance.calculator.model.SurveillanceLocation;

@Repository
public interface SurveillanceLocationRepository extends JpaRepository<SurveillanceLocation, Long> {
  
  Boolean existsByName(String name);

}
