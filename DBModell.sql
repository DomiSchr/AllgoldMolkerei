CREATE TABLE IF NOT EXISTS `product` (
  `produktID` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `preis` decimal(10,2) NOT NULL,
  `menge` varchar(10) NOT NULL,
  PRIMARY KEY (`produktID`)
);



CREATE TABLE IF NOT EXISTS `station` (
  `stationID` int(10) NOT NULL AUTO_INCREMENT,
  `beschreibung` varchar(30) NOT NULL,
  `standort` varchar(30) NOT NULL,
  PRIMARY KEY (`StationID`)
);


CREATE TABLE IF NOT EXISTS `inventory` (
  `inventoryID` int(10) NOT NULL AUTO_INCREMENT,
  `stationID` int(10) NOT NULL,
  `produktID` int(10) NOT NULL,
  `aktMenge` int(10) NOT NULL,
  `minMenge` int(10) NOT NULL,
  `sollMenge` int(10) NOT NULL,
  PRIMARY KEY (`inventoryID`),
  CONSTRAINT `FK_stationID` FOREIGN KEY (`stationID`) REFERENCES `station`(`stationID`),
  CONSTRAINT `FK_produktID` FOREIGN KEY (`produktID`) REFERENCES `product`(`produktID`)
);


CREATE TABLE IF NOT EXISTS `sales` (
  `salesID` int(10) NOT NULL AUTO_INCREMENT,
  `stationID` int(10) NOT NULL,
  `produktID` int(10) NOT NULL,
  `menge` int(10) NOT NULL,
  PRIMARY KEY (`salesID`),
  CONSTRAINT `FK2_stationID` FOREIGN KEY (`stationID`) REFERENCES `station`(`stationID`),
  CONSTRAINT `FK2_produktID` FOREIGN KEY (`produktID`) REFERENCES `product`(`produktID`)
);


CREATE TABLE IF NOT EXISTS `seller` (
  `sellerID` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `vorname` varchar(10) NOT NULL,
  `gebDate` date NOT NULL,
  PRIMARY KEY (`sellerID`)
);



INSERT INTO `product` (`produktID`, `name`, `preis`, `menge`) VALUES
(1, 'Vollmilch', '3.5', '1L'),
(2, 'Käse', '1.2', '100g'),
(3, 'Joghurt', '1.99', '500g'),
(4, 'Frischkäse', '1.19', '175g'),
(5, 'Emmentaler', '3.95', '100g'),
(6, 'Quark', '0.99', '150g'),
(7, 'Buttermilch', '3.89', '1L');


INSERT INTO `station`(`stationID`, `beschreibung`, `standort`) VALUES 
(1, "Automat1", "Kempten"),
(2, "Verkaufsstelle1", "Kempten");


INSERT INTO `inventory`(`inventoryID`, `stationID`, `produktID`, `aktmenge` , `minmenge`, `sollmenge`)VALUES
(1, 1, 1, 5, 3, 10),
(2, 1, 2, 1, 5, 10),
(3, 1, 3, 2, 5, 15),
(4, 1, 4, 5, 4, 15),
(5, 2, 1, 4, 5, 15),
(6, 2, 2, 5, 8, 15),
(7, 2, 3, 6, 8, 20),
(8, 2, 4, 7, 6, 20),
(9, 1, 5, 5, 3, 10),
(10, 2, 5, 1, 5, 10),
(11, 1, 6, 2, 5, 15),
(12, 2, 6, 1, 5, 10),
(13, 1, 7, 1, 5, 10),
(14, 2, 7, 1, 5, 10);


INSERT INTO `sales`(`salesID`, `stationID`, `produktID`, `menge`) VALUES
(1, 1, 1, 0),
(2, 1, 2, 0),
(3, 1, 3, 5),
(4, 1, 4, 4),
(5, 2, 1, 3),
(6, 2, 2, 4),
(7, 2, 3, 5),
(8, 2, 4, 6),
(9, 1, 5, 0),
(10, 2, 5, 0),
(11, 1, 6, 5),
(12, 2, 6, 4),
(13, 1, 7, 5),
(14, 2, 7, 4);


INSERT INTO `seller`(`sellerID`, `name`, `vorname`, `gebDate` ) VALUES
(1, "Hans", "Meyer", "1970-01-01"),
(2, "Veronika", "Huber", "1980-12-12")
(3, "Johannes", "Thoma", "1990-02-02");



-- __________TEST______________________

SELECT p.* FROM product p, inventory i WHERE
p.produktID = i.produktID
AND i.aktMenge < i.minMenge
AND i.stationID = 1;



-- Tabelle, in der ID, Menge*Preis pro Station Drinsteht!drinsteht

SELECT SUM(s.menge * p.preis) AS umsatz FROM sales s, product p where 
p.produktID = s.produktID
AND s.stationID = 1;


-- Tabelle für Anteile eines Produkts am Gesamtumsatz:

SELECT SUM(s.menge * p.preis) AS umsatz FROM sales s, product p WHERE 
p.produktID = s.produktID
AND p.produktID = 1;



-- Fehlende Produkte Liste mit Menge:
SELECT p.produktID, p.name, p.preis, (i.sollMenge - i.aktMenge) AS menge FROM product p, inventory i WHERE
        p.produktID = i.produktID
        AND i.aktMenge < i.minMenge
        AND i.stationID = 1;


-- Fehlende Produkte Liste alt:
"SELECT * FROM product p, inventory i WHERE
        p.produktID = i.produktID
        AND i.aktMenge < i.minMenge
        AND i.stationID = '".$station."';";