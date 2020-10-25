package com.provigil.surveillance.calculator.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Data structure representing the response containing a {@link List} 
 * of calculated costs {@link SurveillanceSubscription}.
 * @author Pradeep Velpula
 *
 */
@XmlRootElement(name = "result")
@XmlType(propOrder = "subscriptions")
public class SurveillanceMonthlyCostResult {
  
  private List<SurveillanceSubscription> subscriptions;

  @XmlElement(name = "subscription")
  public List<SurveillanceSubscription> getSubscriptions() {
    return subscriptions;
  }

  /**
   * Sets the list of {@code subscriptions} of type {@link SurveillanceSubscription}.
   * Only {@code id} and ({@code cost} or {@code error} are required in the 
   * instances of {@link SurveillanceSubscription}.
   * @param subscriptions list of {@code subscriptions} of type {@link SurveillanceSubscription}
   */
  public void setSubscriptions(List<SurveillanceSubscription> subscriptions) {
    this.subscriptions = subscriptions;
  }

}
