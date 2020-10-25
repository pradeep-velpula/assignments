package com.provigil.surveillance.calculator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity for representing {@code LOCATION} of the {@code Surveillance}
 * 
 * @author Pradeep Velpula
 */
@Entity
@Table(name = "SURVEILLANCE_LOCATIONS")
public class SurveillanceLocation {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  private String name;
  private String desc;
  
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
  
  @Override
  public String toString() {
    return "SurveillanceLocation [name=" + name + 
        ", desc=" + desc + 
        "]";
  }

}
