package com.provigil.surveillance.calculator;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.provigil.surveillance.calculator.model.SurveillanceSubscription;
import com.provigil.surveillance.calculator.repository.SurveillanceLocationRepository;
import com.provigil.surveillance.calculator.repository.SurveillancePriceSlabRepository;
import com.provigil.surveillance.calculator.repository.SurveillanceSubscriptionPlanRepository;
import com.provigil.surveillance.calculator.service.SurveillanceSubscriptionService;

/**
 * This is a 'Surveillance Cost Calculator' Application built on Spring Boot.<br>
 * Application takes an XML which contains the Surveillance subscriptions and outputs 
 * the result XML containing all the costs.<p>
 * Application invokes {@link SurveillanceSubscriptionService#getMonthlyCostResult(InputStream)} 
 * by passing {@code subscriptions-01.xml} as an {@link InputStream} and outputs 
 * the results XML containing all the calculated costs.
 * 
 * <h3>Environment Used</h3>
 * - Java11<br>
 * - Maven
 * 
 * <h3>Technical Stack Used</h3>
 * - <b>Spring Boot and Spring Frameworks</b><br>
 * - <b>JPA</b> (for CRUD operations on the master data via <b>Hibernate</b>)<br>
 * - <b>JAXB</b> (for Marshaling and Unmarshaling XML)<br>
 * - <b>H2</b> In-Memory Database (for simplification)
 * 
 * <h3>Execution Steps</h3>
 * <b>1. Run Tests:</b> {@code mvn clean test}<br>
 * <b>2. Build App:</b> {@code mvn clean install} (from directory surveillance-cost-calculator)<br>
 * <b>3. Run App:</b> {@code java -jar target/jar-file-name.jar}
 * 
 * @author Pradeep Velpula
 *
 */
@SpringBootApplication
public class SurveillanceCostCalculatorApplication {
  
  private Logger logger = LoggerFactory.getLogger(SurveillanceCostCalculatorApplication.class);
  
  @Autowired
  private SurveillanceLocationRepository surveillanceLocationRepository;
  
  @Autowired
  private SurveillanceSubscriptionPlanRepository surveillanceSubscriptionPlanRepository;
  
  @Autowired
  private SurveillancePriceSlabRepository surveillancePriceSlabRepository;
  
  @Autowired
  private SurveillanceSubscriptionService surveillanceSubscriptionService;

	public static void main(String[] args) {
		SpringApplication.run(SurveillanceCostCalculatorApplication.class, args);
	}
	
	@Bean
  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
	  logger.info("'Surveillance Cost Calcuator' is ready!");
	  
    return args -> {
      
      logger.info("###### LOGGING SEEDED DATA - BEGIN ######");
      
      surveillanceLocationRepository.findAll().forEach(location -> {logger.info(location.toString());});
      
      surveillanceSubscriptionPlanRepository.findAll().forEach(plan -> {logger.info(plan.toString());});
      
      surveillancePriceSlabRepository.findAll().forEach(slab -> {logger.info(slab.toString());});
      
      logger.info("###### LOGGING SEEDED DATA - END ######");
      
      try {
      
        Resource subscriptionsXmlResource = new ClassPathResource("subscriptions-01.xml");
        
        InputStream requestXmlInputStream = subscriptionsXmlResource.getInputStream();
        
        InputStream responseXmlInputStream = surveillanceSubscriptionService.getMonthlyCostXml(subscriptionsXmlResource.getInputStream());
        //InputStream xmlInputStream = surveillanceSubscriptionService.getMonthlyCostXml(subscriptionsXmlFile);
        //InputStream xmlInputStream = surveillanceSubscriptionService.getMonthlyCostXml(new InputStreamReader(subscriptionsXmlResource.getInputStream()));
  
        String inputXmlString = new String(requestXmlInputStream.readAllBytes(), StandardCharsets.UTF_8);
        String outputXmlString = new String(responseXmlInputStream.readAllBytes(), StandardCharsets.UTF_8);
        
        // Log INPUT
        logger.info("\n\n############  I N P U T  ############\n\n" + inputXmlString);
        
        // Log OUTPUT
        logger.info("\n\n############  O U T P U T  ############\n\n" + outputXmlString);
      
      } catch(Exception e) {
        logger.error("Error encountered while prosessing calculation", e);
      }
      
      
      // Get result object from xml file, input stream and reader
      //SurveillanceMonthlyCostResult surveillanceMonthlyCostResult = surveillanceSubscriptionService.getMonthlyCostResult(subscriptionsXmlFile);
      //SurveillanceMonthlyCostResult surveillanceMonthlyCostResult = surveillanceSubscriptionService.getMonthlyCost(subscriptionsXmlResource.getInputStream());
      //SurveillanceMonthlyCostResult surveillanceMonthlyCostResult = surveillanceSubscriptionService.getMonthlyCost(new InputStreamReader(subscriptionsXmlResource.getInputStream()));
      
      // Marshaling result object to XML
      /*
      StringWriter sw = new StringWriter();
      Result result = new StreamResult(sw);
      jaxb2Marshaller.marshal(surveillanceMonthlyCostResult, result);
      logger.info(sw.toString());
      */
      
    };
	}

}
