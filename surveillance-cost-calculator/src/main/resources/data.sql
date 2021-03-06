DROP TABLE IF EXISTS SURVEILLANCE_PRICE_SLABS;
DROP TABLE IF EXISTS SURVEILLANCE_LOCATIONS;
DROP TABLE IF EXISTS SURVEILLANCE_SUBSCRIPTION_PLANS;

CREATE TABLE SURVEILLANCE_LOCATIONS (
  ID INT AUTO_INCREMENT  PRIMARY KEY,
  NAME VARCHAR(250) NOT NULL,
  DESC VARCHAR(250)
);

 
CREATE TABLE SURVEILLANCE_SUBSCRIPTION_PLANS (
  ID INT AUTO_INCREMENT  PRIMARY KEY,
  NAME VARCHAR(250) NOT NULL,
  DESC VARCHAR(250),
  DURATION_DAYS INT NOT NULL
);

 
CREATE TABLE SURVEILLANCE_PRICE_SLABS (
  ID INT AUTO_INCREMENT  PRIMARY KEY,
  SURVEILLANCE_LOCATION_ID INT NOT NULL,
  SURVEILLANCE_SUBSCRIPTION_PLAN_ID INT NOT NULL,
  AREA_MIN DOUBLE NOT NULL,
  AREA_MAX DOUBLE,
  PRICE_PER_MONTH DOUBLE NOT NULL
);


INSERT INTO SURVEILLANCE_LOCATIONS (NAME, DESC) VALUES
  ('Indoor', 'Closed Locations'),
  ('Outdoor', 'Open Locations');


INSERT INTO SURVEILLANCE_SUBSCRIPTION_PLANS (NAME, DESC, DURATION_DAYS) VALUES
  ('MONTHLY', 'Monthly Subscription', 30),
  ('YEARLY', 'Yearly Subscription', 365);
 
  
INSERT INTO SURVEILLANCE_PRICE_SLABS (SURVEILLANCE_LOCATION_ID, SURVEILLANCE_SUBSCRIPTION_PLAN_ID, 
AREA_MIN, AREA_MAX, PRICE_PER_MONTH) VALUES
  (1, 1, 0, 2500, 2),
  (1, 2, 0, 2500, 1.5),
  (2, 1, 0, 2500, 2.5),
  (2, 2, 0, 2500, 2),
  
  (1, 1, 2500, 5000, 1.5),
  (1, 2, 2500, 5000, 1),
  (2, 1, 2500, 5000, 1.5),
  (2, 2, 2500, 5000, 1),
  
  (1, 1, 5000, 50000, 1),
  (1, 2, 5000, 50000, 0.6),
  (2, 1, 5000, 50000, 1.2),
  (2, 2, 5000, 50000, 0.8),
  
  (1, 1, 50000, null, 0.8),
  (1, 2, 50000, null, 0.5),
  (2, 1, 50000, null, 0.8),
  (2, 2, 50000, null, 0.5)
  ;
