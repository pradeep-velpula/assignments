package com.provigil.surveillance.calculator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.provigil.surveillance.calculator.model.SurveillanceLocation;
import com.provigil.surveillance.calculator.model.SurveillancePriceSlab;

/**
 * JPA Repository for the entity {@link SurveillancePriceSlab}
 * 
 * @author Pradeep Velpula
 */
@Repository
public interface SurveillancePriceSlabRepository extends JpaRepository<SurveillancePriceSlab, Long> {
  
  /**
   * Fetches the list of applicable price slabs {@link SurveillancePriceSlab} for the 
   * provided {@code location}, {@code plan} and {@code area}.
   *
   * @param location location name
   * @param plan plan name
   * @param area area for which price slabs are needed
   * @return list of applicable price slabs {@link SurveillancePriceSlab} for the 
   * provided {@code location}, {@code plan} and {@code area}
   */
  @Query(value = "select slab from SurveillancePriceSlab slab where "
      + "slab.surveillanceLocation.name = :locationName and "
      + "slab.surveillanceSubscriptionPlan.name = :planName and "
      + "slab.areaMin < :area")
  List<SurveillancePriceSlab> findByLocationAndPlanAndArea(@Param("locationName") String location, 
      @Param("planName") String plan, 
      @Param("area") Double area);

}
