# SURVEILLANCE COST CALCULATOR

This is a 'Surveillance Cost Calculator' Application built on Spring Boot.
Application takes an XML which contains the Surveillance subscriptions and outputs the result XML containing all the costs.

Application invokes the below code by passing subscriptions-01.xml as an InputStream and outputs the results XML containing all the calculated costs.
```sh
SurveillanceSubscriptionService.getMonthlyCostResult(InputStream)
```

## Documentation
Open surveillance-cost-calculator/doc/index.html

## Environment Used
- Java11
- Maven

## Technical Stack Used
- Spring Boot and Spring Frameworks
- JPA (for CRUD operations on the master data via Hibernate)
- JAXB (for Marshaling and Unmarshaling XML)
- H2 In-Memory Database (for simplification)

## Execution Steps
### 1. Run Tests:
```sh
$ cd surveillance-cost-calculator
$ mvn clean test
```
### 2. Build App:
```sh
$ cd surveillance-cost-calculator
$ mvn clean install
```
### 3. Run App:
```sh
$ cd surveillance-cost-calculator
$ java -jar target/surveillance-cost-calculator-0.0.1-SNAPSHOT.jar
```
