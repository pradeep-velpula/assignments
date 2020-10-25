package com.provigil.surveillance.calculator.config;

import java.util.HashMap;

import javax.xml.bind.Marshaller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.provigil.surveillance.calculator.model.SurveillanceMonthlyCostRequest;
import com.provigil.surveillance.calculator.model.SurveillanceMonthlyCostResult;
import com.provigil.surveillance.calculator.model.SurveillanceSubscription;

@Configuration
public class MarshallerConfig {
  
  @Bean
  public Jaxb2Marshaller getJaxb2Marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    
    marshaller.setClassesToBeBound(new Class[] {
        SurveillanceSubscription.class,
        SurveillanceMonthlyCostRequest.class,
        SurveillanceMonthlyCostResult.class
    });
    
    marshaller.setMarshallerProperties(new HashMap<String, Object>() {{
      put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    }});
    
    return marshaller;
  }

}
