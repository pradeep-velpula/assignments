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

/**
 * This is a Test class for testing 'Surveillance Cost Calculator' Application 
 * built on Spring Boot.<br>
 * 
 * <h3>Test Cases</h3>
 * - <b>contextLoads:</b> This will test if the context is loaded properly or not by 
 * checking on a repository instance<br>
 * - <b>testDataLoaded:</b> This will test if the master data is loaded properly or not<br>
 * - <b>testSurveillanceCostCalculator:</b> <br>
 * This test invokes 
 * {@link SurveillanceSubscriptionService#getMonthlyCostResult(InputStream)} 
 * by passing {@code test-subscriptions-01.xml} as an {@link InputStream} and <br>
 * holds the result {@link SurveillanceMonthlyCostResult} and <br>
 * outputs the results XML containing all the calculated costs and <b>FAILS</b> if <br>
 * -- one of the subscriptions passed in the XML file {@code test-subscriptions-01.xml} 
 * has an invalid area or location or plan<br>
 * -- one of the costs passed in the XML file {@code test-subscriptions-01.xml} 
 * doesn't match with the calculated cost in the result {@link SurveillanceMonthlyCostResult}
 * 
 * <h3>Environment Used</h3>
 * - Java11<br>
 * - Maven
 * 
 * <h3>Execution Steps</h3>
 * <b>Run Tests:</b> {@code mvn clean test} (from directory surveillance-cost-calculator)<br>
 * 
 * @author Pradeep Velpula
 *
 */
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
