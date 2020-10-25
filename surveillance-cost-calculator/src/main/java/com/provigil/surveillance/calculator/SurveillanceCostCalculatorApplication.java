package com.provigil.surveillance.calculator;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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

import com.provigil.surveillance.calculator.repository.SurveillanceLocationRepository;
import com.provigil.surveillance.calculator.repository.SurveillancePriceSlabRepository;
import com.provigil.surveillance.calculator.repository.SurveillanceSubscriptionPlanRepository;
import com.provigil.surveillance.calculator.service.SurveillanceSubscriptionService;

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
