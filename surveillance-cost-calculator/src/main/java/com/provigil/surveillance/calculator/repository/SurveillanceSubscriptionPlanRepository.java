package com.provigil.surveillance.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.provigil.surveillance.calculator.model.SurveillanceLocation;
import com.provigil.surveillance.calculator.model.SurveillanceSubscriptionPlan;

/**
 * JPA Repository for the entity {@link SurveillanceSubscriptionPlan}
 * 
 * @author Pradeep Velpula
 */
@Repository
public interface SurveillanceSubscriptionPlanRepository extends JpaRepository<SurveillanceSubscriptionPlan, Long> {
  
  /**
   * Checks for the existence of the plan with the {@code name} provided.
   *
   * @param name plan name
   * @return true if plan with the {@code name} exists or else return false
   */
  Boolean existsByName(String name);

}
