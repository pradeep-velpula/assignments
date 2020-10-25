package com.provigil.surveillance.calculator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.provigil.surveillance.calculator.model.SurveillancePriceSlab;

@Repository
public interface SurveillancePriceSlabRepository extends JpaRepository<SurveillancePriceSlab, Long> {
  
  @Query(value = "select slab from SurveillancePriceSlab slab where "
      + "slab.surveillanceLocation.name = :locationName and "
      + "slab.surveillanceSubscriptionPlan.name = :planName and "
      + "slab.areaMin < :area")
  List<SurveillancePriceSlab> findByLocationAndPlanAndArea(@Param("locationName") String location, 
      @Param("planName") String plan, 
      @Param("area") Double area);

}
