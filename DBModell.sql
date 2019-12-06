CREATE TABLE IF NOT EXISTS `Product` (
  `ProduktID` int(10) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `Preis` decimal(10,2) NOT NULL,
  `Menge` varchar(10) NOT NULL,
  --- `Haltbarkeit` varchar(10) NOT NULL,
  PRIMARY KEY (`ProduktID`)
);


--- Fremdschlüssel fehlen überall!

CREATE TABLE IF NOT EXISTS `Station` (
  `StationID` int(10) NOT NULL AUTO_INCREMENT,
  `Beschreibung` varchar(30) NOT NULL,
  `Standort` varchar(30) NOT NULL,
  PRIMARY KEY (`StationID`)
);



CREATE TABLE IF NOT EXISTS `Inventory` (
  `InventoryID` int(10) NOT NULL AUTO_INCREMENT,
  `StationID` int(10) NOT NULL,
  `ProduktID` varchar(30) NOT NULL,
  `AktMenge` varchar(10) NOT NULL,
  `MinMenge` varchar(10) NOT NULL,
  `SollMenge` varchar(10) NOT NULL,
  PRIMARY KEY (`InventoryID`),
  FOREIGN KEY `StationID`(`StationID`)
  REFERENCES `Station`(`StationID`),
  FOREIGN KEY `ProduktID`(`ProduktID`)
  REFERENCES `Product`(`ProduktID`)
);


--- Passt noch nicht:
CREATE TABLE IF NOT EXISTS `Sales` (
  `SalesID` int(10) NOT NULL AUTO_INCREMENT,
  `StationID` int(10) NOT NULL,
  `ProduktID` int(10) NOT NULL,
  `Menge` varchar(10) NOT NULL,
  `SollMenge` varchar(10) NOT NULL,
  PRIMARY KEY (`SalesID`),
  
);




INSERT INTO `Products` (`ProduktID`, `Name`, `Preis`, `Menge`) VALUES
(1, 'Vollmilch', '3.5', '1L'),
(2, 'Käse', '1.2', '100g');

INSERT INTO `Station`(`StationID`, `Beschreibung`, `Standort`) VALUES 
(1, "Automat1", "Kempten");


INSERT INTO `Inventory`(`InventoryID`, `StationID`, `ProduktID`, `AktMenge` , `MinMenge`, `SollMenge`)VALUES
(1, 1, 1, 5, 3, 10),
(2, 1, 2, 1, 5, 10);

