CREATE TABLE IF NOT EXISTS `produkt` (
  `produktID` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `preis` decimal(10,2) NOT NULL,
  `menge` varchar(50),
  PRIMARY KEY (`produktID`)
);


INSERT INTO `produkt` (`produktID`, `name`, `preis`, `menge`) VALUES
(1, 'Vollmilch', '3.5', '1L'),
(2, 'Käse', '1.2', '100g');