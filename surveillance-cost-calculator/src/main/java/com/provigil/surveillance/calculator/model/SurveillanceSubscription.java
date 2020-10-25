package com.provigil.surveillance.calculator.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Data structure representing the subscription.<p>
 * When using this for generating {@link SurveillanceMonthlyCostRequest}, 
 * {@code cost} and {@code error} is not required.<p>
 * When using this for generating {@link SurveillanceMonthlyCostResult}, 
 * only {@code id} and ({@code cost} or {@code error}) are required.<p>
 * @author Pradeep Velpula
 *
 */
@XmlRootElement(name = "subscription")
@XmlType(propOrder = {"id", "area", "plan", "location", "cost", "error"})
public class SurveillanceSubscription {
  
  private long id;
  private Double area;
  private String plan;
  private String location;
  private Double cost;
  private String error;
  
  @XmlElement
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  
  @XmlElement
  public Double getArea() {
    return area;
  }
  public void setArea(Double area) {
    this.area = area;
  }
  
  @XmlElement
  public String getPlan() {
    return plan;
  }
  public void setPlan(String plan) {
    this.plan = plan;
  }
  
  @XmlElement
  public String getLocation() {
    return location;
  }
  public void setLocation(String location) {
    this.location = location;
  }
  
  @XmlElement
  public Double getCost() {
    return cost;
  }
  public void setCost(Double cost) {
    this.cost = cost;
  }
  
  @XmlElement
  public String getError() {
    return error;
  }
  public void setError(String error) {
    this.error = error;
  }
  
  @Override
  public String toString() {
    return "SurveillanceSubscription [id=" + id + ", area=" + area + ", plan=" + plan + ", location=" + location
        + ", cost=" + cost + ", error=" + error + "]";
  }

}
