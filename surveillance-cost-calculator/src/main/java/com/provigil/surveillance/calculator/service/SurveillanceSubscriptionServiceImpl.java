package com.provigil.surveillance.calculator.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.provigil.surveillance.calculator.config.SurveillanceCostCalculatorConfigProperties;
import com.provigil.surveillance.calculator.model.SurveillanceMonthlyCostRequest;
import com.provigil.surveillance.calculator.model.SurveillanceMonthlyCostResult;
import com.provigil.surveillance.calculator.model.SurveillancePriceSlab;
import com.provigil.surveillance.calculator.model.SurveillanceSubscription;
import com.provigil.surveillance.calculator.repository.SurveillanceLocationRepository;
import com.provigil.surveillance.calculator.repository.SurveillancePriceSlabRepository;
import com.provigil.surveillance.calculator.repository.SurveillanceSubscriptionPlanRepository;

@Service
public class SurveillanceSubscriptionServiceImpl implements SurveillanceSubscriptionService {
  
  private Logger logger = LoggerFactory.getLogger(SurveillanceSubscriptionServiceImpl.class);
  
  @Autowired
  private Jaxb2Marshaller jaxb2Marshaller;
  
  @Autowired
  private SurveillanceLocationRepository surveillanceLocationRepository;
  
  @Autowired
  private SurveillanceSubscriptionPlanRepository surveillanceSubscriptionPlanRepository;
  
  @Autowired
  private SurveillancePriceSlabRepository surveillancePriceSlabRepository;
  
  @Autowired
  @Qualifier("surveillanceCostCalculatorConfigProperties")
  private SurveillanceCostCalculatorConfigProperties configProps;

  @Override
  public Double calculateMonthlyCost(SurveillanceSubscription surveillanceSubscription) {
    
    // Get applicable price slabs
    List<SurveillancePriceSlab> surveillancePriceSlabs = surveillancePriceSlabRepository.
        findByLocationAndPlanAndArea(surveillanceSubscription.getLocation(), 
            surveillanceSubscription.getPlan(), surveillanceSubscription.getArea());
    
    /*
     * Iterate through the above price slabs and aggregate all those slab costs 
     * to get the total monthly cost.
     */
    Double totalMonthlyCost = surveillancePriceSlabs.stream().collect(Collectors.summingDouble(surveillancePriceSlab -> {
      
      // Get current slab's considerable area for which monthly cost will be calculated
      Double currentSlabAreaMax = 0D;
      if(surveillancePriceSlab.getAreaMax() != null) {
        currentSlabAreaMax = Math.min(surveillanceSubscription.getArea(), surveillancePriceSlab.getAreaMax());
      } else {
        currentSlabAreaMax = surveillanceSubscription.getArea();
      }
      Double consideredSlabArea = currentSlabAreaMax - surveillancePriceSlab.getAreaMin();
      
      Double cost = consideredSlabArea * surveillancePriceSlab.getPricePerMonth();
      return cost;
      
    }));
    
    return totalMonthlyCost;
    
  }
  

  @Override
  public InputStream getMonthlyCostXml(File subscriptionsXmlFile) {
    SurveillanceMonthlyCostResult monthlyCostResult = getMonthlyCostResult(subscriptionsXmlFile);
    return getMonthlyCostXml(monthlyCostResult);
  }

  @Override
  public InputStream getMonthlyCostXml(InputStream subscriptionsXmlInputStream) {
    SurveillanceMonthlyCostResult monthlyCostResult = getMonthlyCostResult(subscriptionsXmlInputStream);
    return getMonthlyCostXml(monthlyCostResult);
  }

  @Override
  public InputStream getMonthlyCostXml(Reader subscriptionsXmlReader) {
    SurveillanceMonthlyCostResult monthlyCostResult = getMonthlyCostResult(subscriptionsXmlReader);
    return getMonthlyCostXml(monthlyCostResult);
  }

  @Override
  public InputStream getMonthlyCostXml(SurveillanceMonthlyCostRequest surveillanceCostCalculatorRequest) {
    SurveillanceMonthlyCostResult monthlyCostResult = getMonthlyCostResult(surveillanceCostCalculatorRequest);
    return getMonthlyCostXml(monthlyCostResult);
  }
  
  private InputStream getMonthlyCostXml(SurveillanceMonthlyCostResult surveillanceMonthlyCostResult) {
    
    // Create a ByteArrayOutputStream to hold the xml
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    Result result = new StreamResult(os);
    jaxb2Marshaller.marshal(surveillanceMonthlyCostResult, result);
    
    // Create a ByteArrayInputStream to return an InputStream instead of String or Byte-Array
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(os.toByteArray()); 
    
    return byteArrayInputStream;
  }
  

  @Override
  public SurveillanceMonthlyCostResult getMonthlyCostResult(SurveillanceMonthlyCostRequest surveillanceCostCalculatorRequest) {
    
    logger.info("######## REQUEST ########");
    logger.info(surveillanceCostCalculatorRequest.toString());
    
    logger.info("<<<<<<<<========================>>>>>>>>>>>");
    
    List<SurveillanceSubscription> surveillanceSubscriptions = surveillanceCostCalculatorRequest.getSubscriptions();
    
    // Iterate and collect the calculated monthly costs for each subscription
    List<SurveillanceSubscription> surveillanceSubscriptionCosts = surveillanceSubscriptions.stream().map(subscription -> {
      
      // Validate 'area'
      if(subscription.getArea() == null || subscription.getArea().doubleValue() <= 0) {
        // Prepare a subscription object with the corresponding id and the error
        SurveillanceSubscription subscriptionCost = new SurveillanceSubscription();
        subscriptionCost.setId(subscription.getId());
        subscriptionCost.setError(configProps.getInvalidAreaMessage());
        return subscriptionCost;
      }
      
      // Validate 'plan'
      if(!StringUtils.hasText(subscription.getPlan()) || 
          !surveillanceSubscriptionPlanRepository.existsByName(subscription.getPlan())) {
        // Prepare a subscription object with the corresponding id and the error
        SurveillanceSubscription subscriptionCost = new SurveillanceSubscription();
        subscriptionCost.setId(subscription.getId());
        subscriptionCost.setError(configProps.getInvalidPlanMessage());
        return subscriptionCost;
      }
      
      // Validate 'location'
      if(!StringUtils.hasText(subscription.getLocation()) || 
          !surveillanceLocationRepository.existsByName(subscription.getLocation())) {
        // Prepare a subscription object with the corresponding id and the error
        SurveillanceSubscription subscriptionCost = new SurveillanceSubscription();
        subscriptionCost.setId(subscription.getId());
        subscriptionCost.setError(configProps.getInvalidLocationMessage());
        return subscriptionCost;
      }
      
      Double monthlyCost = calculateMonthlyCost(subscription);
      
      // Prepare a subscription object with the corresponding id and the calculated monthly cost
      SurveillanceSubscription subscriptionCost = new SurveillanceSubscription();
      subscriptionCost.setId(subscription.getId());
      subscriptionCost.setCost(monthlyCost);
      return subscriptionCost;
    }).collect(Collectors.toList());
    
    // Prepare a result object with all the above collected subscription costs
    SurveillanceMonthlyCostResult surveillanceMonthlyCostResult = new SurveillanceMonthlyCostResult();
    surveillanceMonthlyCostResult.setSubscriptions(surveillanceSubscriptionCosts);
    
    return surveillanceMonthlyCostResult;
  }

  @Override
  public SurveillanceMonthlyCostResult getMonthlyCostResult(File subscriptionsXmlFile) {
    
    logger.info("<<<<<<<<========================>>>>>>>>>>>");
    
    Source source = new StreamSource(subscriptionsXmlFile);
    SurveillanceMonthlyCostRequest costCalculatorRequest = (SurveillanceMonthlyCostRequest)jaxb2Marshaller.unmarshal(source);
    logger.info("######## XML ########");
    logger.info(costCalculatorRequest.toString());
    
    logger.info("<<<<<<<<========================>>>>>>>>>>>");
    
    return getMonthlyCostResult(costCalculatorRequest);
    
  }

  @Override
  public SurveillanceMonthlyCostResult getMonthlyCostResult(InputStream subscriptionsXmlInputStream) {
    
    Source source = new StreamSource(subscriptionsXmlInputStream);
    SurveillanceMonthlyCostRequest costCalculatorRequest = (SurveillanceMonthlyCostRequest)jaxb2Marshaller.unmarshal(source);
    logger.info("######## INPUT STREAM ########");
    logger.info(costCalculatorRequest.toString());
    
    logger.info("<<<<<<<<========================>>>>>>>>>>>");
    
    return getMonthlyCostResult(costCalculatorRequest);
    
  }

  @Override
  public SurveillanceMonthlyCostResult getMonthlyCostResult(Reader subscriptionsXmlReader) {
    
    Source source = new StreamSource(subscriptionsXmlReader);
    SurveillanceMonthlyCostRequest costCalculatorRequest = (SurveillanceMonthlyCostRequest)jaxb2Marshaller.unmarshal(source);
    logger.info("######## READER ########");
    logger.info(costCalculatorRequest.toString());
    
    logger.info("<<<<<<<<========================>>>>>>>>>>>");
    
    return getMonthlyCostResult(costCalculatorRequest);
    
  }

  public SurveillanceMonthlyCostResult getMonthlyCostDemo(File subscriptionsXmlFile) {
    
    logger.info("<<<<<<<<========================>>>>>>>>>>>");
    
    SurveillanceMonthlyCostRequest request = new SurveillanceMonthlyCostRequest();
    SurveillanceSubscription sub1 = new SurveillanceSubscription();
    sub1.setId(1);sub1.setArea(12000D);sub1.setLocation("Indoor");sub1.setPlan("MONTHLY");sub1.setCost(35000D);
    SurveillanceSubscription sub2 = new SurveillanceSubscription();
    sub2.setId(2);sub2.setArea(15000D);sub2.setLocation("Outdoor");sub2.setPlan("MONTHLY");sub2.setCost(45000D);
    SurveillanceSubscription sub3 = new SurveillanceSubscription();
    sub3.setId(3);sub3.setArea(15000D);sub3.setLocation("Outdoor");sub3.setPlan("MONTHLY");//sub3.setCost(45000D);
    List<SurveillanceSubscription> subs = Arrays.asList(sub1, sub2, sub3);
    request.setSubscriptions(subs);
    
    StringWriter sw = new StringWriter();
    Result res = new StreamResult(sw);
    jaxb2Marshaller.marshal(request, res);
    logger.info(sw.toString());
    
    logger.info("<<<<<<<<========================>>>>>>>>>>>");
    
    SurveillanceMonthlyCostResult result = new SurveillanceMonthlyCostResult();
    SurveillanceSubscription sub4 = new SurveillanceSubscription();
    sub4.setId(1);sub4.setArea(12000D);sub4.setLocation("Indoor");sub4.setPlan("MONTHLY");sub4.setCost(35000D);
    SurveillanceSubscription sub5 = new SurveillanceSubscription();
    sub5.setId(2);sub5.setCost(45000D);
    SurveillanceSubscription sub6 = new SurveillanceSubscription();
    sub6.setId(3);sub6.setCost(45000D);
    List<SurveillanceSubscription> subs2 = Arrays.asList(sub4, sub5, sub6);
    result.setSubscriptions(subs2);
    
    StringWriter sw2 = new StringWriter();
    Result res2 = new StreamResult(sw2);
    jaxb2Marshaller.marshal(result, res2);
    logger.info(sw2.toString());
    
    logger.info("<<<<<<<<========================>>>>>>>>>>>");
    
    Source source = new StreamSource(subscriptionsXmlFile);
    SurveillanceMonthlyCostRequest costCalculatorRequest = (SurveillanceMonthlyCostRequest)jaxb2Marshaller.unmarshal(source);
    logger.info("######## XML ########");
    logger.info(costCalculatorRequest.toString());
    
    logger.info("<<<<<<<<========================>>>>>>>>>>>");
    
    return null;
    
  }

}
