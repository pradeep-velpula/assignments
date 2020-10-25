package com.provigil.surveillance.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.provigil.surveillance.calculator.model.SurveillanceSubscriptionPlan;

@Repository
public interface SurveillanceSubscriptionPlanRepository extends JpaRepository<SurveillanceSubscriptionPlan, Long> {
  
  Boolean existsByName(String name);

}
