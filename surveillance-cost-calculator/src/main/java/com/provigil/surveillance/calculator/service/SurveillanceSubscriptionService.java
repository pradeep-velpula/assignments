package com.provigil.surveillance.calculator.service;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

import com.provigil.surveillance.calculator.model.SurveillanceMonthlyCostRequest;
import com.provigil.surveillance.calculator.model.SurveillanceMonthlyCostResult;
import com.provigil.surveillance.calculator.model.SurveillanceSubscription;

/**
 * A service to calculate Surveillance monthly costs.
 * @author Pradeep Velpula
 *
 */
public interface SurveillanceSubscriptionService {
  
  /**
   * Calculates the monthly cost for the provided subscription.
   *
   * @param surveillanceSubscription subscription for which monthly 
   * cost needs to be calculated.
   * @return the calculated monthly cost
   */
  Double calculateMonthlyCost(SurveillanceSubscription surveillanceSubscription);
  
  /**
   * Generates an XML InputStream which contains the calculated costs 
   * for the provided subscriptions XML file.<p>
   * 
   * Example of input {@code subscriptionsXmlFile} whose element structure 
   * maps to {@link SurveillanceMonthlyCostRequest}
   * <pre>
   * {@code
   * <?xml version="1.0" encoding="UTF-8"?>
   * <subscriptions>
   *  <subscription>
   *   <id>1</id>
   *   <area>2500</area>
   *   <plan>MONTHLY</plan>
   *   <location>Indoor</location>
   *  </subscription>
   *  <subscription>
   *   <id>2</id>
   *   <area>4000</area>
   *   <plan>YEARLY</plan>
   *   <location>Outdoor</location>
   *  </subscription>
   * </subscription>
   * }
   * </pre>
   * 
   * Example of the returned XML {@link InputStream} whose element structure  
   * maps to {@link SurveillanceMonthlyCostResult}
   * <pre>
   * {@code
   * <?xml version="1.0" encoding="UTF-8"?>
   * <result>
   *  <subscription>
   *   <id>1</id>
   *   <cost>5000</cost>
   *  </subscription>
   *  <subscription>
   *   <id>2</id>
   *   <cost>6500</cost>
   *  </subscription>
   * </result>
   * }
   * </pre>
   * 
   * If there is any error while calculating a subscription, then 
   * {@code error} element replaces {@code cost} element.
   *
   * @param subscriptionsXmlFile subscriptions XML whose element structure  
   * maps to {@link SurveillanceMonthlyCostRequest}. Costs will 
   * be calculated for each {@link SurveillanceSubscription}.
   * @return XML {@link InputStream} whose element structure  
   * maps to {@link SurveillanceMonthlyCostResult}
   */
  InputStream getMonthlyCostXml(File subscriptionsXmlFile);
  
  /**
   * Generates an XML InputStream which contains the calculated costs 
   * for the provided subscriptions XML file.<p>
   * 
   * Example of input {@code subscriptionsXmlInputStream} whose element structure 
   * maps to {@link SurveillanceMonthlyCostRequest}
   * <pre>
   * {@code
   * <?xml version="1.0" encoding="UTF-8"?>
   * <subscriptions>
   *  <subscription>
   *   <id>1</id>
   *   <area>2500</area>
   *   <plan>MONTHLY</plan>
   *   <location>Indoor</location>
   *  </subscription>
   *  <subscription>
   *   <id>2</id>
   *   <area>4000</area>
   *   <plan>YEARLY</plan>
   *   <location>Outdoor</location>
   *  </subscription>
   * </subscription>
   * }
   * </pre>
   * 
   * Example of the returned XML {@link InputStream} whose element structure  
   * maps to {@link SurveillanceMonthlyCostResult}
   * <pre>
   * {@code
   * <?xml version="1.0" encoding="UTF-8"?>
   * <result>
   *  <subscription>
   *   <id>1</id>
   *   <cost>5000</cost>
   *  </subscription>
   *  <subscription>
   *   <id>2</id>
   *   <cost>6500</cost>
   *  </subscription>
   * </result>
   * }
   * </pre>
   * 
   * If there is any error while calculating a subscription, then 
   * {@code error} element replaces {@code cost} element.
   *
   * @param subscriptionsXmlInputStream an {@link InputStream} containing XML whose 
   * element structure maps to {@link SurveillanceMonthlyCostRequest}. Costs will 
   * be calculated for each {@link SurveillanceSubscription}.
   * @return XML {@link InputStream} whose element structure  
   * maps to {@link SurveillanceMonthlyCostResult}
   */
  InputStream getMonthlyCostXml(InputStream subscriptionsXmlInputStream);
  
  /**
   * Generates an XML InputStream which contains the calculated costs 
   * for the provided subscriptions XML file.<p>
   * 
   * Example of input {@code subscriptionsXmlReader} whose element structure 
   * maps to {@link SurveillanceMonthlyCostRequest}
   * <pre>
   * {@code
   * <?xml version="1.0" encoding="UTF-8"?>
   * <subscriptions>
   *  <subscription>
   *   <id>1</id>
   *   <area>2500</area>
   *   <plan>MONTHLY</plan>
   *   <location>Indoor</location>
   *  </subscription>
   *  <subscription>
   *   <id>2</id>
   *   <area>4000</area>
   *   <plan>YEARLY</plan>
   *   <location>Outdoor</location>
   *  </subscription>
   * </subscription>
   * }
   * </pre>
   * 
   * Example of the returned XML {@link InputStream} whose element structure  
   * maps to {@link SurveillanceMonthlyCostResult}
   * <pre>
   * {@code
   * <?xml version="1.0" encoding="UTF-8"?>
   * <result>
   *  <subscription>
   *   <id>1</id>
   *   <cost>5000</cost>
   *  </subscription>
   *  <subscription>
   *   <id>2</id>
   *   <cost>6500</cost>
   *  </subscription>
   * </result>
   * }
   * </pre>
   * 
   * If there is any error while calculating a subscription, then 
   * {@code error} element replaces {@code cost} element.
   *
   * @param subscriptionsXmlReader an {@link Reader} containing XML whose 
   * element structure maps to {@link SurveillanceMonthlyCostRequest}. Costs will 
   * be calculated for each {@link SurveillanceSubscription}.
   * @return XML {@link InputStream} whose element structure  
   * maps to {@link SurveillanceMonthlyCostResult}
   */
  InputStream getMonthlyCostXml(Reader subscriptionsXmlReader);
  
  /**
   * Generates an XML InputStream which contains the calculated costs 
   * for the provided request object {@link SurveillanceMonthlyCostRequest}.
   * {@code cost} and {@code error} are not required and will be ignored.<p>
   * 
   * Example of the returned XML {@link InputStream} whose element structure  
   * maps to {@link SurveillanceMonthlyCostResult}
   * <pre>
   * {@code
   * <?xml version="1.0" encoding="UTF-8"?>
   * <result>
   *  <subscription>
   *   <id>1</id>
   *   <cost>5000</cost>
   *  </subscription>
   *  <subscription>
   *   <id>2</id>
   *   <cost>6500</cost>
   *  </subscription>
   * </result>
   * }
   * </pre>
   * 
   * If there is any error while calculating a subscription, then 
   * {@code error} element replaces {@code cost} element.
   *
   * @param surveillanceCostCalculatorRequest request object {@link SurveillanceMonthlyCostRequest} 
   * for which costs needs to be calculated for each {@link SurveillanceSubscription}.
   * @return XML {@link InputStream} whose element structure  
   * maps to {@link SurveillanceMonthlyCostResult}
   */
  InputStream getMonthlyCostXml(SurveillanceMonthlyCostRequest surveillanceCostCalculatorRequest);
  
  /**
   * Generates {@link SurveillanceMonthlyCostResult} which contains the calculated costs 
   * for the provided subscriptions XML file.<p>
   * 
   * Example of input file {@code subscriptionsXmlFile} whose element structure 
   * maps to {@link SurveillanceMonthlyCostRequest}
   * <pre>
   * {@code
   * <?xml version="1.0" encoding="UTF-8"?>
   * <subscriptions>
   *  <subscription>
   *   <id>1</id>
   *   <area>2500</area>
   *   <plan>MONTHLY</plan>
   *   <location>Indoor</location>
   *  </subscription>
   *  <subscription>
   *   <id>2</id>
   *   <area>4000</area>
   *   <plan>YEARLY</plan>
   *   <location>Outdoor</location>
   *  </subscription>
   * </subscription>
   * }
   * </pre>
   *
   * @param subscriptionsXmlFile a {@link File} object containing XML whose 
   * element structure maps to {@link SurveillanceMonthlyCostRequest}. Costs will 
   * be calculated for each {@link SurveillanceSubscription}.
   * @return an instance of {@link SurveillanceMonthlyCostResult} which contains 
   * the calculated costs.
   */
  SurveillanceMonthlyCostResult getMonthlyCostResult(File subscriptionsXmlFile);
  
  /**
   * Generates {@link SurveillanceMonthlyCostResult} which contains the calculated costs 
   * for the provided subscriptions XML {@link InputStream}.<p>
   * 
   * Example of input stream {@code subscriptionsXmlInputStream} which contains XML 
   * whose element structure maps to {@link SurveillanceMonthlyCostRequest}
   * <pre>
   * {@code
   * <?xml version="1.0" encoding="UTF-8"?>
   * <subscriptions>
   *  <subscription>
   *   <id>1</id>
   *   <area>2500</area>
   *   <plan>MONTHLY</plan>
   *   <location>Indoor</location>
   *  </subscription>
   *  <subscription>
   *   <id>2</id>
   *   <area>4000</area>
   *   <plan>YEARLY</plan>
   *   <location>Outdoor</location>
   *  </subscription>
   * </subscription>
   * }
   * </pre>
   *
   * @param subscriptionsXmlInputStream an {@link InputStream} object containing XML whose 
   * element structure maps to {@link SurveillanceMonthlyCostRequest}. Costs will 
   * be calculated for each {@link SurveillanceSubscription}.
   * @return an instance of {@link SurveillanceMonthlyCostResult} which contains 
   * the calculated costs.
   */
  SurveillanceMonthlyCostResult getMonthlyCostResult(InputStream subscriptionsXmlInputStream);
  
  /**
   * Generates {@link SurveillanceMonthlyCostResult} which contains the calculated costs 
   * for the provided subscriptions XML {@link Reader}.<p>
   * 
   * Example of reader {@code subscriptionsXmlReader} which contains XML 
   * whose element structure maps to {@link SurveillanceMonthlyCostRequest}
   * <pre>
   * {@code
   * <?xml version="1.0" encoding="UTF-8"?>
   * <subscriptions>
   *  <subscription>
   *   <id>1</id>
   *   <area>2500</area>
   *   <plan>MONTHLY</plan>
   *   <location>Indoor</location>
   *  </subscription>
   *  <subscription>
   *   <id>2</id>
   *   <area>4000</area>
   *   <plan>YEARLY</plan>
   *   <location>Outdoor</location>
   *  </subscription>
   * </subscription>
   * }
   * </pre>
   *
   * @param subscriptionsXmlReader a {@link Reader} object containing XML whose 
   * element structure maps to {@link SurveillanceMonthlyCostRequest}. Costs will 
   * be calculated for each {@link SurveillanceSubscription}.
   * @return an instance of {@link SurveillanceMonthlyCostResult} which contains 
   * the calculated costs.
   */
  SurveillanceMonthlyCostResult getMonthlyCostResult(Reader subscriptionsXmlReader);
  
  /**
   * Generates {@link SurveillanceMonthlyCostResult} which contains the calculated costs 
   * for the provided subscriptions {@link SurveillanceMonthlyCostRequest}.
   * {@code cost} and {@code error} are not required and will be ignored.<p>
   *
   * @param surveillanceCostCalculatorRequest a {@link SurveillanceMonthlyCostRequest}  
   * containing subscriptions. Costs will be calculated for each {@link SurveillanceSubscription}.
   * @return an instance of {@link SurveillanceMonthlyCostResult} which contains 
   * the calculated costs.
   */
  SurveillanceMonthlyCostResult getMonthlyCostResult(SurveillanceMonthlyCostRequest surveillanceCostCalculatorRequest);
  

}
