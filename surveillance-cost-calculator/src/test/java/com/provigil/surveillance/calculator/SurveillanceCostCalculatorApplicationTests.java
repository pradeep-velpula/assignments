package com.provigil.surveillance.calculator;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.provigil.surveillance.calculator.config.SurveillanceCostCalculatorConfigProperties;
import com.provigil.surveillance.calculator.model.SurveillanceMonthlyCostRequest;
import com.provigil.surveillance.calculator.model.SurveillanceMonthlyCostResult;
import com.provigil.surveillance.calculator.model.SurveillanceSubscription;
import com.provigil.surveillance.calculator.repository.SurveillanceLocationRepository;
import com.provigil.surveillance.calculator.repository.SurveillancePriceSlabRepository;
import com.provigil.surveillance.calculator.repository.SurveillanceSubscriptionPlanRepository;
import com.provigil.surveillance.calculator.service.SurveillanceSubscriptionService;

@SpringBootTest
@EnableConfigurationProperties(SurveillanceCostCalculatorConfigProperties.class)
public class SurveillanceCostCalculatorApplicationTests {
  
  private Logger logger = LoggerFactory.getLogger(SurveillanceCostCalculatorApplicationTests.class);
  
  @Autowired
  private Jaxb2Marshaller jaxb2Marshaller;
  
  @Autowired
  private SurveillanceSubscriptionService surveillanceSubscriptionService;
  
  @Autowired
  private SurveillanceLocationRepository surveillanceLocationRepository;
  
  @Autowired
  private SurveillanceSubscriptionPlanRepository surveillanceSubscriptionPlanRepository;
  
  @Autowired
  private SurveillancePriceSlabRepository surveillancePriceSlabRepository;

	@Test
	void contextLoads() {
    assertThat(surveillanceLocationRepository).isNotNull();
    assertThat(surveillanceSubscriptionPlanRepository).isNotNull();
    assertThat(surveillancePriceSlabRepository).isNotNull();
	}
	
	@Test
	void testDataLoaded() {
	  assertThat(surveillanceLocationRepository.count()).isGreaterThan(0);
    assertThat(surveillanceSubscriptionPlanRepository.count()).isGreaterThan(0);
    assertThat(surveillancePriceSlabRepository.count()).isGreaterThan(0);
	}
	
	@Test
	void testSurveillanceCostCalculator() throws IOException {
	  
    Resource subscriptionsXmlResource = new ClassPathResource("test-subscriptions-01.xml");
    File subscriptionsXmlFile = subscriptionsXmlResource.getFile();
    
	  Source source = new StreamSource(subscriptionsXmlFile);
    SurveillanceMonthlyCostRequest costCalculatorRequest = (SurveillanceMonthlyCostRequest)jaxb2Marshaller.unmarshal(source);
    
    //SurveillanceMonthlyCostResult surveillanceMonthlyCostResult = surveillanceSubscriptionService.getMonthlyCostResult(subscriptionsXmlFile);
    InputStream monthlyCostXml = surveillanceSubscriptionService.getMonthlyCostXml(subscriptionsXmlFile);
    Source resultSource = new StreamSource(monthlyCostXml);
    SurveillanceMonthlyCostResult surveillanceMonthlyCostResult = (SurveillanceMonthlyCostResult)jaxb2Marshaller.unmarshal(resultSource);
    
    logger.info("==================== TEST INPUT DATA BEGIN =====================");
    costCalculatorRequest.getSubscriptions().forEach(surveillanceSubscription -> logger.info(surveillanceSubscription.toString()));
    logger.info("==================== TEST INPUT DATA END =====================");
    
    logger.info("==================== TEST OUTPUT DATA BEGIN =====================");
    surveillanceMonthlyCostResult.getSubscriptions().forEach(surveillanceSubscription -> logger.info(surveillanceSubscription.toString()));
    logger.info("==================== TEST OUTPUT DATA END =====================");
    
    // Iterate and validate each input subscription's cost with the calculated output subscription's cost
    costCalculatorRequest.getSubscriptions().stream().forEach(surveillanceSubscription -> {
      Optional<SurveillanceSubscription> matchedSurveillanceSubscription = surveillanceMonthlyCostResult.getSubscriptions().stream()
        .filter(surveillanceSubscriptionCost -> surveillanceSubscription.getId() == surveillanceSubscriptionCost.getId())
        .findFirst();
      
      assertThat(matchedSurveillanceSubscription.get().getError())
        .as("Error Occurred - Subscription[id=" + surveillanceSubscription.getId() + "] - " + matchedSurveillanceSubscription.get().getError())
        .isNull();
      
      assertThat(matchedSurveillanceSubscription.get().getCost())
        .as("Incorrect Cost Calculation - Subscription[id=" + surveillanceSubscription.getId() + "]")
        .isEqualTo(surveillanceSubscription.getCost());

    });
	}

}
