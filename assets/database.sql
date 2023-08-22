-- MariaDB dump 10.19-11.0.2-MariaDB, for osx10.18 (arm64)
--
-- Host: localhost    Database: hockeydata
-- ------------------------------------------------------
-- Server version	10.11.3-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `LeagueConferences`
--

DROP TABLE IF EXISTS `LeagueConferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LeagueConferences` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LeagueConferences`
--

LOCK TABLES `LeagueConferences` WRITE;
/*!40000 ALTER TABLE `LeagueConferences` DISABLE KEYS */;
/*!40000 ALTER TABLE `LeagueConferences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LeagueDivisions`
--

DROP TABLE IF EXISTS `LeagueDivisions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LeagueDivisions` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LeagueDivisions`
--

LOCK TABLES `LeagueDivisions` WRITE;
/*!40000 ALTER TABLE `LeagueDivisions` DISABLE KEYS */;
/*!40000 ALTER TABLE `LeagueDivisions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LeagueFranchises`
--

DROP TABLE IF EXISTS `LeagueFranchises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LeagueFranchises` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LeagueFranchises`
--

LOCK TABLES `LeagueFranchises` WRITE;
/*!40000 ALTER TABLE `LeagueFranchises` DISABLE KEYS */;
/*!40000 ALTER TABLE `LeagueFranchises` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Players`
--

DROP TABLE IF EXISTS `Players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Players` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `firstName` text DEFAULT NULL,
  `lastName` text DEFAULT NULL,
  `primaryNumber` int(11) DEFAULT NULL,
  `birthYear` int(11) DEFAULT NULL,
  `birthMonth` int(11) DEFAULT NULL,
  `birthDay` int(11) DEFAULT NULL,
  `birthCity` text DEFAULT NULL,
  `birthProvince` text DEFAULT NULL,
  `birthCountry` text DEFAULT NULL,
  `height` text DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `shoot` text DEFAULT NULL,
  `rookie` tinyint(1) DEFAULT NULL,
  `teamId` int(11) DEFAULT NULL,
  `positionCode` tinytext DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Players`
--

LOCK TABLES `Players` WRITE;
/*!40000 ALTER TABLE `Players` DISABLE KEYS */;
/*!40000 ALTER TABLE `Players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PlayersSalaries`
--

DROP TABLE IF EXISTS `PlayersSalaries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PlayersSalaries` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `playerId` int(11) DEFAULT NULL,
  `season` int(11) DEFAULT NULL,
  `avv` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PlayersSalaries`
--

LOCK TABLES `PlayersSalaries` WRITE;
/*!40000 ALTER TABLE `PlayersSalaries` DISABLE KEYS */;
/*!40000 ALTER TABLE `PlayersSalaries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Positions`
--

DROP TABLE IF EXISTS `Positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Positions` (
  `code` tinytext NOT NULL,
  `abbrev` text DEFAULT NULL,
  `fullName` text DEFAULT NULL,
  `type` text DEFAULT NULL,
  PRIMARY KEY (`code`(3))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Positions`
--

LOCK TABLES `Positions` WRITE;
/*!40000 ALTER TABLE `Positions` DISABLE KEYS */;
INSERT INTO `Positions` VALUES
('C','C','Center','Forward'),
('D','D','Defenseman','Defenseman'),
('G','G','Goalie','Goalie'),
('HC','Head Coach','Head Coach','Coach'),
('L','LW','Left Wing','Forward'),
('N/A','N/A','Unknown','Unknown'),
('R','RW','Right Wing','Forward');
/*!40000 ALTER TABLE `Positions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Teams`
--

DROP TABLE IF EXISTS `Teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Teams` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` text DEFAULT NULL,
  `venue` text DEFAULT NULL,
  `abbreviation` text DEFAULT NULL,
  `firstYearOfPlay` text DEFAULT NULL,
  `divisionId` int(11) DEFAULT NULL,
  `conferenceId` int(11) DEFAULT NULL,
  `franchiseId` int(11) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Teams`
--

LOCK TABLES `Teams` WRITE;
/*!40000 ALTER TABLE `Teams` DISABLE KEYS */;
INSERT INTO `Teams` VALUES
(1,'New Jersey Devils','Prudential Center','NJD','1982',18,6,23,1),
(2,'New York Islanders','UBS Arena','NYI','1972',18,6,22,1),
(3,'New York Rangers','Madison Square Garden','NYR','1926',18,6,10,1),
(4,'Philadelphia Flyers','Wells Fargo Center','PHI','1967',18,6,16,1),
(5,'Pittsburgh Penguins','PPG Paints Arena','PIT','1967',18,6,17,1),
(6,'Boston Bruins','TD Garden','BOS','1924',17,6,6,1),
(7,'Buffalo Sabres','KeyBank Center','BUF','1970',17,6,19,1),
(8,'Montréal Canadiens','Bell Centre','MTL','1909',17,6,1,1),
(9,'Ottawa Senators','Canadian Tire Centre','OTT','1990',17,6,30,1),
(10,'Toronto Maple Leafs','Scotiabank Arena','TOR','1917',17,6,5,1),
(12,'Carolina Hurricanes','PNC Arena','CAR','1979',18,6,26,1),
(13,'Florida Panthers','FLA Live Arena','FLA','1993',17,6,33,1),
(14,'Tampa Bay Lightning','AMALIE Arena','TBL','1991',17,6,31,1),
(15,'Washington Capitals','Capital One Arena','WSH','1974',18,6,24,1),
(16,'Chicago Blackhawks','United Center','CHI','1926',16,5,11,1),
(17,'Detroit Red Wings','Little Caesars Arena','DET','1926',17,6,12,1),
(18,'Nashville Predators','Bridgestone Arena','NSH','1997',16,5,34,1),
(19,'St. Louis Blues','Enterprise Center','STL','1967',16,5,18,1),
(20,'Calgary Flames','Scotiabank Saddledome','CGY','1980',15,5,21,1),
(21,'Colorado Avalanche','Ball Arena','COL','1979',16,5,27,1),
(22,'Edmonton Oilers','Rogers Place','EDM','1979',15,5,25,1),
(23,'Vancouver Canucks','Rogers Arena','VAN','1970',15,5,20,1),
(24,'Anaheim Ducks','Honda Center','ANA','1993',15,5,32,1),
(25,'Dallas Stars','American Airlines Center','DAL','1967',16,5,15,1),
(26,'Los Angeles Kings','Crypto.com Arena','LAK','1967',15,5,14,1),
(28,'San Jose Sharks','SAP Center at San Jose','SJS','1990',15,5,29,1),
(29,'Columbus Blue Jackets','Nationwide Arena','CBJ','1997',18,6,36,1),
(30,'Minnesota Wild','Xcel Energy Center','MIN','1997',16,5,37,1),
(52,'Winnipeg Jets','Canada Life Centre','WPG','2011',16,5,35,1),
(53,'Arizona Coyotes','Mullett Arena','ARI','1979',16,5,28,1),
(54,'Vegas Golden Knights','T-Mobile Arena','VGK','2016',15,5,38,1),
(55,'Seattle Kraken','Climate Pledge Arena','SEA','2021',15,5,39,1);
/*!40000 ALTER TABLE `Teams` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-22  0:24:48
