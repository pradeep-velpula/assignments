package com.provigil.surveillance.calculator.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Data structure representing the request containing a {@link List} 
 * of subscriptions {@link SurveillanceSubscription}.
 * @author Pradeep Velpula
 *
 */
@XmlRootElement(name = "subscriptions")
@XmlType(propOrder = {"subscriptions"})
public class SurveillanceMonthlyCostRequest {
  
  private List<SurveillanceSubscription> subscriptions;

  @XmlElement(name = "subscription")
  public List<SurveillanceSubscription> getSubscriptions() {
    return subscriptions;
  }

  /**
   * Sets the list of {@code subscriptions} of type {@link SurveillanceSubscription}.
   * {@code cost} and {@code error} are not required and will be ignored in the 
   * instances of {@link SurveillanceSubscription}.
   * @param subscriptions list of {@code subscriptions} of type {@link SurveillanceSubscription}
   */
  public void setSubscriptions(List<SurveillanceSubscription> subscriptions) {
    this.subscriptions = subscriptions;
  }

  @Override
  public String toString() {
    return "SurveillanceCostCalculatorRequest [subscriptions=" + subscriptions + "]";
  }

}
