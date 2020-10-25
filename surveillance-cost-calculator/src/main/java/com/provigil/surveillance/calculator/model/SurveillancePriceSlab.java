package com.provigil.surveillance.calculator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity for representing {@code PRICE SLAB} of the {@code Surveillance}
 * 
 * @author Pradeep Velpula
 */
@Entity
@Table(name = "SURVEILLANCE_PRICE_SLABS")
public class SurveillancePriceSlab {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  @ManyToOne
  private SurveillanceLocation surveillanceLocation;
  
  @ManyToOne
  private SurveillanceSubscriptionPlan surveillanceSubscriptionPlan;
  
  private Double areaMin;
  private Double areaMax;
  
  private double pricePerMonth;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public SurveillanceLocation getSurveillanceLocation() {
    return surveillanceLocation;
  }

  public void setSurveillanceLocation(SurveillanceLocation surveillanceLocation) {
    this.surveillanceLocation = surveillanceLocation;
  }

  public SurveillanceSubscriptionPlan getSurveillanceSubscriptionPlan() {
    return surveillanceSubscriptionPlan;
  }

  public void setSurveillanceSubscriptionPlan(SurveillanceSubscriptionPlan surveillanceSubscriptionPlan) {
    this.surveillanceSubscriptionPlan = surveillanceSubscriptionPlan;
  }

  public Double getAreaMin() {
    return areaMin;
  }

  public void setAreaMin(Double areaMin) {
    this.areaMin = areaMin;
  }

  public Double getAreaMax() {
    return areaMax;
  }

  public void setAreaMax(Double areaMax) {
    this.areaMax = areaMax;
  }

  public double getPricePerMonth() {
    return pricePerMonth;
  }

  public void setPricePerMonth(double pricePerMonth) {
    this.pricePerMonth = pricePerMonth;
  }
  
  @Override
  public String toString() {
    return "SurveillancePriceSlab [location=" + surveillanceLocation.getName() + 
        ", plan=" + surveillanceSubscriptionPlan.getName() + 
        ", areaMin=" + areaMin + 
        ", areaMax=" + areaMax + 
        ", pricePerMonth=" + pricePerMonth + 
        "]";
  }
  
}
