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

--Fremschlüssel dazufügen!!
CREATE TABLE IF NOT EXISTS `sales` (
  `salesID` int(10) NOT NULL AUTO_INCREMENT,
  `stationID` int(10) NOT NULL,
  `produktID` int(10) NOT NULL,
  `menge` int(10) NOT NULL,
  PRIMARY KEY (`salesID`),
);




INSERT INTO `product` (`produktID`, `name`, `preis`, `menge`) VALUES
(1, 'Vollmilch', '3.5', '1L'),
(2, 'Käse', '1.2', '100g');


INSERT INTO `station`(`stationID`, `beschreibung`, `standort`) VALUES 
(1, "Automat1", "Kempten");


INSERT INTO `inventory`(`inventoryID`, `stationID`, `produktID`, `aktmenge` , `minmenge`, `sollmenge`)VALUES
(1, 1, 1, 5, 3, 10),
(2, 1, 2, 1, 5, 10);


INSERT INTO `sales`(`salesID`, `stationID`, `produktID`, `menge`) VALUES
(1, 1, 1, 0),
(2, 1, 2, 0);





SELECT p.* FROM product p, inventory i WHERE
p.produktID = i.produktID
AND i.aktMenge < i.minMenge
AND i.stationID = 1;



-- Tabelle, in der ID, Menge*Preis pro Station Drinsteht!drinsteht

SELECT SUM(s.menge * p.preis) AS umsatz FROM sales s, product p where 
p.produktID = s.produktID
AND s.stationID = 1;

