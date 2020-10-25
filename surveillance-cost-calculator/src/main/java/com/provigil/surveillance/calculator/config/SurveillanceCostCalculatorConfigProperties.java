package com.provigil.surveillance.calculator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "surveillance-cost-calculator")
public class SurveillanceCostCalculatorConfigProperties {
  
  private String invalidAreaMessage;
  private String invalidPlanMessage;
  private String invalidLocationMessage;
  
  public String getInvalidAreaMessage() {
    return invalidAreaMessage;
  }
  public void setInvalidAreaMessage(String invalidAreaMessage) {
    this.invalidAreaMessage = invalidAreaMessage;
  }
  public String getInvalidPlanMessage() {
    return invalidPlanMessage;
  }
  public void setInvalidPlanMessage(String invalidPlanMessage) {
    this.invalidPlanMessage = invalidPlanMessage;
  }
  public String getInvalidLocationMessage() {
    return invalidLocationMessage;
  }
  public void setInvalidLocationMessage(String invalidLocationMessage) {
    this.invalidLocationMessage = invalidLocationMessage;
  }

}
