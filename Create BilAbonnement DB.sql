DROP DATABASE IF EXISTS bilabonnement;
CREATE DATABASE bilabonnement;

USE bilabonnement;

CREATE TABLE employee_type
(
  employee_type_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  employee_type VARCHAR(45) NULL
  );
  
CREATE TABLE employee
(
  employee_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  employee_name VARCHAR(45) NULL,
  employee_username VARCHAR(45) NULL,
  employee_password VARCHAR(45) NULL,
  employee_type_id INT NOT NULL,
  FOREIGN KEY (employee_type_id) REFERENCES employee_type(employee_type_id)
  );
  
  CREATE TABLE subscription
  (
  subscription_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  subscription_type VARCHAR(45) NULL
  );
  
  CREATE TABLE brand
  (
  brand_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  brand_name VARCHAR(45) NULL
  );
  
  CREATE TABLE model_image
  (
  model_image_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  model_image_url VARCHAR(256) NULL
  );
  
  CREATE TABLE model
  (
  model_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  model_name VARCHAR(45) NULL,
  model_image_id INT NULL,
  brand_id INT NOT NULL,
  FOREIGN KEY (brand_id) REFERENCES brand(brand_id)
  );
  
  CREATE TABLE car 
  (
  car_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  vin_nr VARCHAR(45) NULL,
  equipment_level INT NULL,
  base_price DOUBLE NULL,
  vat DOUBLE NULL,
  emission DOUBLE NULL,
  model_id INT NOT NULL,
  FOREIGN KEY (model_id) REFERENCES model(model_id)
  );
  
CREATE TABLE customer
(
  customer_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  customer_name VARCHAR(45) NULL,
  email VARCHAR(45) NULL,
  phone_number VARCHAR(45) NULL,
  address VARCHAR(45) NULL,
  driver_license_number INT NULL,
  cpr_number INT NULL
  );
  
  CREATE TABLE damage_report
  (
  damage_report_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  damage_report_comment VARCHAR(1000) NULL,
  damage_report_overdriven_km INT NULL,
  employee_id INT DEFAULT NULL,
  FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
  );
  
  CREATE TABLE damage_type
  (
  damage_type_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  damage_type_name VARCHAR(45) NULL,
  damage_type_price DOUBLE NULL
  );
  
  CREATE TABLE damage_on_report
  (
  damages_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  damage_report_id INT NOT NULL,
  damage_type_id INT NOT NULL,
  FOREIGN KEY (damage_report_id) REFERENCES damage_report(damage_report_id),
  FOREIGN KEY (damage_type_id) REFERENCES damage_type(damage_type_id)
  );
  
  CREATE TABLE rental_contract
  (
  rental_contract_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  start_date DATE NULL,
  end_date DATE NULL,
  ongoing BOOLEAN DEFAULT FALSE,
  employee_id INT NOT NULL,
  subscription_id INT NOT NULL,
  customer_id INT NOT NULL,
  car_id INT NOT NULL,
  damage_report_id INT NOT NULL,
  FOREIGN KEY (employee_id) REFERENCES employee(employee_id),
  FOREIGN KEY (subscription_id) REFERENCES subscription(subscription_id),
  FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
  FOREIGN KEY (car_id) REFERENCES car(car_id),
  FOREIGN KEY (damage_report_id) REFERENCES damage_report(damage_report_id)
  );
  
  INSERT INTO subscription(subscription_type) VALUES
  ("Limited"),
  ("Unlimited");
  
  INSERT INTO employee_type(employee_type) VALUES 
  ("Dataregistrering"),
  ("Skade og Udbedring"),
  ("Forretningsudvikler"),
  ("Økonomi");
  
  INSERT INTO brand(brand_name) VALUES
("Toyota"),
("Honda"),
("Ford"),
("Chevrolet"),
("BMW"),
("Mercedes-Benz"),
("Audi"),
("Volkswagen"),
("Nissan"),
("Hyundai"),
("Kia"),
("Volvo"),
("Mazda"),
("Subaru"),
("Lexus"),
("Porsche"),
("Land Rover"),
("Jeep"),
("Ferrari"),
("Tesla"),
("Maserati");

INSERT INTO model(model_name, brand_id) VALUES
("Prius", 1),
("Civic", 2),
("Focus", 3),
("Cruze", 4),
("3 Series", 5),
("C-Class", 6),
("A4", 7),
("Golf", 8),
("Altima", 9),
("Elantra", 10),
("Optima", 11),
("S60", 12),
("MX-5", 13),
("Impreza", 14),
("IS", 15),
("911", 16),
("Range Rover", 17),
("Wrangler", 18),
("488 GTB", 19),
("Model S", 20);

INSERT INTO car(vin_nr, equipment_level, base_price, vat, emission, model_id) VALUES
("239DB132", 3, 10000, 2500.00, 2.9, 1),
("JHGBF12", 2, 12000, 3000.00, 3.5, 2),
("KLOP123", 1, 15000, 3750.00, 2.8, 3),
("WERTY56", 3, 11000, 2750.00, 2.6, 4),
("XCVB98H", 2, 18000, 4500.00, 3.2, 5),
("LKJH654", 1, 20000, 5000.00, 3.9, 6),
("ASDFGH1", 3, 13000, 3250.00, 3.1, 7),
("QWERT23", 2, 14000, 3500.00, 3.3, 8),
("ZXCVB67", 1, 16000, 4000.00, 3.7, 9),
("POIUY45", 3, 17000, 4250.00, 3.4, 10),
("MNBVC89", 2, 19000, 4750.00, 3.6, 11),
("LKJHG21", 1, 22000, 5500.00, 3.8, 12),
("QWERTY8", 3, 24000, 6000.00, 4.1, 13),
("ASDFGH4", 2, 26000, 6500.00, 4.5, 14),
("ZXCVBN5", 1, 28000, 7000.00, 4.2, 15),
("POIUYT6", 3, 30000, 7500.00, 4.6, 16),
("MNBVCX7", 2, 32000, 8000.00, 4.8, 17),
("LKJHGF9", 1, 35000, 8750.00, 4.9, 18),
("QWERT12", 3, 38000, 9500.00, 5.1, 19),
("ASDFGH3", 2, 40000, 10000.00, 5.5, 20);

INSERT INTO employee(employee_name, employee_username, employee_password, employee_type_id) VALUES
("Bob", "Bob123", "password", 1),
("DataRegistrering", "data", "data", 1),
("Skade og Udbedring", "skade", "skade", 2),
("Forretningsudvikler", "forretning", "forretning", 3);

INSERT INTO customer(customer_name, email, phone_number, address, driver_license_number, cpr_number) VALUES
("Karen", "karen@gmail.com", "+45 34042123", "København 10", 998080, 2003938974);

INSERT INTO damage_type(damage_type_name, damage_type_price) VALUES
("Lakfelt", 1500),
("Ridset alufælge", 400),
("Ny forrude", 3000);

INSERT INTO damage_report(damage_report_comment, damage_report_overdriven_km, employee_id) VALUES 
("Et par skader", 100, 3);

INSERT INTO rental_contract(start_date, end_date, ongoing, employee_id, subscription_id, 
customer_id, car_id, damage_report_id) VALUES
("2023-05-15", "2023-10-15", true, 2, 1, 1, 1, 1);

INSERT INTO damage_on_report(damage_report_id, damage_type_id) VALUES
(1,1),
(1,2);

INSERT INTO model_image(model_image_url) VALUES
("https://png.pngtree.com/png-clipart/20190604/original/pngtree-vector-car-png-image_1312227.jpg"); 

SET SQL_SAFE_UPDATES = 0;
UPDATE model SET model_image_id = 1;
SET SQL_SAFE_UPDATES = 1;
 