package com.provigil.surveillance.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.provigil.surveillance.calculator.model.SurveillanceLocation;

/**
 * JPA Repository for the entity {@link SurveillanceLocation}
 * 
 * @author Pradeep Velpula
 */
@Repository
public interface SurveillanceLocationRepository extends JpaRepository<SurveillanceLocation, Long> {
  
  /**
   * Checks for the existence of the location with the {@code name} provided.
   *
   * @param name location name
   * @return true if location with the {@code name} exists or else return false
   */
  Boolean existsByName(String name);

}
