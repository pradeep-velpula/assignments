package com.provigil.surveillance.calculator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SURVEILLANCE_SUBSCRIPTION_PLANS")
public class SurveillanceSubscriptionPlan {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String name;
  private String desc;
  private int durationDays;
  
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDesc() {
    return desc;
  }
  public void setDesc(String desc) {
    this.desc = desc;
  }
  public int getDurationDays() {
    return durationDays;
  }
  public void setDurationDays(int durationDays) {
    this.durationDays = durationDays;
  }
  
  @Override
  public String toString() {
    return "SurveillanceSubscriptionPlan [name=" + name + 
        ", desc=" + desc + 
        "]";
  }
  
}
