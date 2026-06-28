CREATE DATABASE  IF NOT EXISTS `diamondiq` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `diamondiq`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: diamondiq
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

UNLOCK TABLES;

--
-- Table structure for table `matchup_history`
--

DROP TABLE IF EXISTS `matchup_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matchup_history` (
  `matchup_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `team_one_id` int NOT NULL,
  `team_two_id` int NOT NULL,
  `predicted_winner_id` int DEFAULT NULL,
  `analysis_summary` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `team_one_score` double DEFAULT NULL,
  `team_two_score` double DEFAULT NULL,
  `confidence` int DEFAULT NULL,
  PRIMARY KEY (`matchup_id`),
  KEY `user_id` (`user_id`),
  KEY `team_one_id` (`team_one_id`),
  KEY `team_two_id` (`team_two_id`),
  KEY `predicted_winner_id` (`predicted_winner_id`),
  CONSTRAINT `matchup_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `matchup_history_ibfk_2` FOREIGN KEY (`team_one_id`) REFERENCES `teams` (`team_id`),
  CONSTRAINT `matchup_history_ibfk_3` FOREIGN KEY (`team_two_id`) REFERENCES `teams` (`team_id`),
  CONSTRAINT `matchup_history_ibfk_4` FOREIGN KEY (`predicted_winner_id`) REFERENCES `teams` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matchup_history`
--

LOCK TABLES `matchup_history` WRITE;
/*!40000 ALTER TABLE `matchup_history` DISABLE KEYS */;
INSERT INTO `matchup_history` VALUES (1,1,15,11,15,'Miami Marlins has a better record.\nHouston Astros has the stronger offense (OPS).\nMiami Marlins has the better pitching (ERA).\nMiami Marlins allows fewer baserunners (WHIP).\nHouston Astros scores more runs.\n','2026-06-28 05:06:36',NULL,NULL,NULL),(2,1,17,16,16,'Milwaukee Brewers has a better record.\nMinnesota Twins has the stronger offense (OPS).\nMilwaukee Brewers has the better pitching (ERA).\nMilwaukee Brewers allows fewer baserunners (WHIP).\nMilwaukee Brewers scores more runs.\n','2026-06-28 05:39:13',134.86,245.16,95),(3,1,7,21,21,'PHI has a better record.\nPHI has the stronger offense (OPS).\nPHI has the better pitching (ERA).\nPHI allows fewer baserunners (WHIP).\nPHI scores more runs.\n','2026-06-28 05:45:02',134.79000000000002,192.71,95),(4,1,5,6,5,'CHC has a better record.\nCHC has the stronger offense (OPS).\nCWS has the better pitching (ERA).\nCHC allows fewer baserunners (WHIP).\nCHC scores more runs.\n','2026-06-28 07:15:00',191.94999999999996,182.71000000000006,59),(5,1,11,17,11,'HOU has a better record.\nMIN has the stronger offense (OPS).\nHOU has the better pitching (ERA).\nHOU allows fewer baserunners (WHIP).\nMIN scores more runs.\n','2026-06-28 08:23:10',146.94000000000003,134.86,62),(6,1,11,17,11,'HOU has a better record.\nMIN has the stronger offense (OPS).\nHOU has the better pitching (ERA).\nHOU allows fewer baserunners (WHIP).\nMIN scores more runs.\n','2026-06-28 08:29:58',146.94000000000003,134.86,62),(7,1,11,17,11,'HOU has a better record.\nMIN has the stronger offense (OPS).\nHOU has the better pitching (ERA).\nHOU allows fewer baserunners (WHIP).\nMIN scores more runs.\n','2026-06-28 08:31:12',146.94000000000003,134.86,62),(8,1,11,12,11,'HOU has a better record.\nHOU has the stronger offense (OPS).\nHOU has the better pitching (ERA).\nHOU allows fewer baserunners (WHIP).\nHOU scores more runs.\n','2026-06-28 09:06:24',146.94000000000003,93.75000000000001,95),(9,1,11,15,15,'MIA has a better record.\nHOU has the stronger offense (OPS).\nMIA has the better pitching (ERA).\nMIA allows fewer baserunners (WHIP).\nHOU scores more runs.\n','2026-06-28 09:12:40',146.94000000000003,182.14,85),(10,1,15,11,15,'MIA has a better record.\nHOU has the stronger offense (OPS).\nMIA has the better pitching (ERA).\nMIA allows fewer baserunners (WHIP).\nHOU scores more runs.\n','2026-06-28 09:27:52',182.14,146.94000000000003,85),(11,1,11,15,15,'MIA has a better record.\nHOU has the stronger offense (OPS).\nMIA has the better pitching (ERA).\nMIA allows fewer baserunners (WHIP).\nHOU scores more runs.\n','2026-06-28 09:33:25',146.94000000000003,182.14,85),(12,1,17,8,8,'CLE has a better record.\nMIN has the stronger offense (OPS).\nCLE has the better pitching (ERA).\nCLE allows fewer baserunners (WHIP).\nMIN scores more runs.\n','2026-06-28 09:35:44',134.86,168.35,83),(13,1,5,6,5,'CHC has a better record.\nCHC has the stronger offense (OPS).\nCWS has the better pitching (ERA).\nCHC allows fewer baserunners (WHIP).\nCHC scores more runs.\n','2026-06-28 09:39:46',191.94999999999996,182.71000000000006,59),(14,1,5,17,5,'CHC has a better record.\nCHC has the stronger offense (OPS).\nCHC has the better pitching (ERA).\nCHC allows fewer baserunners (WHIP).\nMIN scores more runs.\n','2026-06-28 09:43:27',191.94999999999996,134.86,95),(15,1,5,21,21,'Philadelphia Phillies is projected to win because:\nBetter overall record.\nStronger pitching staff (lower ERA).\n\nChicago Cubs\'s biggest advantage is its run production, but DiamondIQ projects Philadelphia Phillies\'s overall profile to be stronger.','2026-06-28 09:48:42',191.94999999999996,192.71,50),(16,1,21,19,19,'WHY NEW YORK YANKEES IS FAVORED\nBetter overall record.\nMore productive offense (higher OPS).\nStronger pitching staff (lower ERA).\nAllows fewer baserunners (lower WHIP).\nScores more runs offensively.\n','2026-06-28 09:52:40',192.71,239.04999999999998,95),(17,1,15,29,15,'Why The Miami Marlins Are Favored\nBetter overall record.\nMore productive offense (higher OPS).\nStronger pitching staff (lower ERA).\nAllows fewer baserunners (lower WHIP).\nScores more runs offensively.\n','2026-06-28 09:59:14',182.14,143.93999999999994,85),(18,1,11,7,11,'Why The Houston Astros Are Favored\nBetter overall record.\nMore productive offense (higher OPS).\nAllows fewer baserunners (lower WHIP).\nScores more runs offensively.\n','2026-06-28 10:05:47',146.94000000000003,134.79000000000002,80),(19,1,20,25,25,'Why The Seattle Mariners Are Favored\nBetter overall record.\nStronger pitching staff (lower ERA).\nAllows fewer baserunners (lower WHIP).\n\nAthletics\'s biggest advantage is its run production, but DiamondIQ projects Seattle Mariners\'s overall profile to be stronger.','2026-06-28 10:40:01',145.61000000000004,173.69,85),(20,1,15,11,15,'Why The Miami Marlins Are Favored\nBetter overall record.\nStronger pitching staff (lower ERA).\nAllows fewer baserunners (lower WHIP).\n\nHouston Astros\'s biggest advantage is its run production, but DiamondIQ projects Miami Marlins\'s overall profile to be stronger.','2026-06-28 18:08:23',182.14,146.94000000000003,85),(21,1,11,17,11,'Why The Houston Astros Are Favored\nBetter overall record.\nStronger pitching staff (lower ERA).\nAllows fewer baserunners (lower WHIP).\n\nMinnesota Twins\'s biggest advantage is its run production, but DiamondIQ projects Houston Astros\'s overall profile to be stronger.','2026-06-28 18:08:58',146.94000000000003,134.86,80),(22,1,8,15,15,'Why The Miami Marlins Are Favored\nBetter overall record.\nMore productive offense (higher OPS).\nAllows fewer baserunners (lower WHIP).\nScores more runs offensively.\n','2026-06-28 19:25:06',168.35,182.14,78),(23,1,20,5,5,'Why The Chicago Cubs Are Favored\nBetter overall record.\nStronger pitching staff (lower ERA).\nAllows fewer baserunners (lower WHIP).\nScores more runs offensively.\n','2026-06-28 19:32:51',145.61000000000004,191.94999999999996,85);
/*!40000 ALTER TABLE `matchup_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_statistics`
--

DROP TABLE IF EXISTS `team_statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team_statistics` (
  `stat_id` int NOT NULL AUTO_INCREMENT,
  `team_id` int NOT NULL,
  `season_year` int NOT NULL,
  `wins` int DEFAULT '0',
  `losses` int DEFAULT '0',
  `runs_scored` int DEFAULT '0',
  `runs_allowed` int DEFAULT '0',
  `batting_average` decimal(5,3) DEFAULT NULL,
  `on_base_percentage` decimal(5,3) DEFAULT NULL,
  `slugging_percentage` decimal(5,3) DEFAULT NULL,
  `earned_run_average` decimal(4,2) DEFAULT NULL,
  `ops` decimal(5,3) DEFAULT NULL,
  `whip` decimal(4,2) DEFAULT NULL,
  `strikeouts` int DEFAULT NULL,
  `home_runs` int DEFAULT NULL,
  `stolen_bases` int DEFAULT NULL,
  PRIMARY KEY (`stat_id`),
  KEY `team_id` (`team_id`),
  CONSTRAINT `team_statistics_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `teams` (`team_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_statistics`
--

LOCK TABLES `team_statistics` WRITE;
/*!40000 ALTER TABLE `team_statistics` DISABLE KEYS */;
INSERT INTO `team_statistics` VALUES (1,1,2026,41,41,349,376,0.239,0.308,0.386,4.32,0.694,1.30,563,73,NULL),(2,2,2026,49,31,388,299,0.250,0.313,0.413,3.44,0.726,1.21,690,103,NULL),(3,3,2026,39,44,388,412,0.240,0.319,0.400,4.35,0.719,1.38,672,98,NULL),(4,4,2026,35,46,323,319,0.244,0.313,0.387,3.73,0.700,1.25,681,71,NULL),(5,5,2026,45,38,409,370,0.242,0.336,0.404,4.27,0.740,1.25,650,99,NULL),(6,6,2026,43,38,388,369,0.242,0.322,0.417,4.25,0.739,1.33,673,115,NULL),(7,7,2026,39,42,346,392,0.227,0.310,0.390,4.51,0.700,1.45,652,100,NULL),(8,8,2026,43,40,325,334,0.227,0.310,0.363,3.77,0.673,1.26,760,75,NULL),(9,9,2026,33,50,385,473,0.255,0.324,0.417,5.51,0.741,1.51,587,92,NULL),(10,10,2026,35,48,339,340,0.235,0.314,0.394,3.80,0.708,1.25,670,93,NULL),(11,11,2026,41,44,380,424,0.243,0.316,0.409,4.77,0.725,1.39,732,109,NULL),(12,12,2026,34,50,352,423,0.244,0.316,0.391,4.84,0.707,1.43,650,82,NULL),(13,13,2026,34,49,377,420,0.239,0.318,0.398,4.66,0.716,1.41,729,99,NULL),(14,14,2026,52,30,425,287,0.262,0.344,0.437,3.44,0.781,1.11,725,110,NULL),(15,15,2026,44,39,358,347,0.247,0.324,0.390,3.96,0.714,1.24,707,73,NULL),(16,16,2026,50,30,415,294,0.253,0.337,0.393,3.42,0.730,1.19,788,73,NULL),(17,17,2026,39,45,409,440,0.247,0.322,0.411,4.89,0.733,1.41,678,102,NULL),(18,18,2026,35,48,335,379,0.231,0.298,0.375,4.11,0.673,1.28,760,92,NULL),(19,19,2026,48,34,405,303,0.241,0.325,0.431,3.34,0.756,1.19,710,121,NULL),(20,20,2026,40,42,385,432,0.248,0.328,0.414,4.91,0.742,1.44,698,107,NULL),(21,21,2026,46,37,363,362,0.235,0.301,0.399,4.08,0.700,1.29,788,108,NULL),(22,22,2026,41,42,420,397,0.258,0.335,0.414,4.15,0.749,1.30,762,101,NULL),(23,23,2026,43,37,317,321,0.220,0.296,0.365,3.85,0.661,1.29,666,83,NULL),(24,24,2026,33,48,334,385,0.256,0.309,0.420,4.37,0.729,1.37,643,88,NULL),(25,25,2026,42,42,337,332,0.230,0.311,0.383,3.68,0.694,1.18,737,102,NULL),(26,26,2026,42,38,360,369,0.247,0.325,0.394,4.30,0.719,1.35,597,88,NULL),(27,27,2026,47,33,361,341,0.258,0.336,0.391,3.81,0.727,1.21,633,71,NULL),(28,28,2026,41,42,337,346,0.244,0.320,0.392,3.98,0.712,1.25,700,88,NULL),(29,29,2026,39,44,341,373,0.249,0.311,0.392,4.17,0.703,1.32,732,85,NULL),(30,30,2026,41,42,443,440,0.246,0.319,0.422,4.70,0.741,1.40,662,110,NULL);
/*!40000 ALTER TABLE `team_statistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teams`
--

DROP TABLE IF EXISTS `teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teams` (
  `team_id` int NOT NULL AUTO_INCREMENT,
  `team_name` varchar(100) NOT NULL,
  `abbreviation` varchar(10) NOT NULL,
  `league` varchar(50) NOT NULL,
  `division` varchar(50) NOT NULL,
  `city` varchar(100) NOT NULL,
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teams`
--

LOCK TABLES `teams` WRITE;
/*!40000 ALTER TABLE `teams` DISABLE KEYS */;
INSERT INTO `teams` VALUES (1,'Arizona Diamondbacks','ARI','National','West','Phoenix'),(2,'Atlanta Braves','ATL','National','East','Atlanta'),(3,'Baltimore Orioles','BAL','American','East','Baltimore'),(4,'Boston Red Sox','BOS','American','East','Boston'),(5,'Chicago Cubs','CHC','National','Central','Chicago'),(6,'Chicago White Sox','CWS','American','Central','Chicago'),(7,'Cincinnati Reds','CIN','National','Central','Cincinnati'),(8,'Cleveland Guardians','CLE','American','Central','Cleveland'),(9,'Colorado Rockies','COL','National','West','Denver'),(10,'Detroit Tigers','DET','American','Central','Detroit'),(11,'Houston Astros','HOU','American','West','Houston'),(12,'Kansas City Royals','KC','American','Central','Kansas City'),(13,'Los Angeles Angels','LAA','American','West','Anaheim'),(14,'Los Angeles Dodgers','LAD','National','West','Los Angeles'),(15,'Miami Marlins','MIA','National','East','Miami'),(16,'Milwaukee Brewers','MIL','National','Central','Milwaukee'),(17,'Minnesota Twins','MIN','American','Central','Minneapolis'),(18,'New York Mets','NYM','National','East','New York'),(19,'New York Yankees','NYY','American','East','New York'),(20,'Athletics','ATH','American','West','West Sacramento'),(21,'Philadelphia Phillies','PHI','National','East','Philadelphia'),(22,'Pittsburgh Pirates','PIT','National','Central','Pittsburgh'),(23,'San Diego Padres','SD','National','West','San Diego'),(24,'San Francisco Giants','SF','National','West','San Francisco'),(25,'Seattle Mariners','SEA','American','West','Seattle'),(26,'St. Louis Cardinals','STL','National','Central','St. Louis'),(27,'Tampa Bay Rays','TB','American','East','St. Petersburg'),(28,'Texas Rangers','TEX','American','West','Arlington'),(29,'Toronto Blue Jays','TOR','American','East','Toronto'),(30,'Washington Nationals','WSH','National','East','Washington');
/*!40000 ALTER TABLE `teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `favorite_team_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'kev','a@a','scooby','2026-06-24 12:36:40',13),(3,'tom','tom@tom','tom','2026-06-24 12:37:48',NULL),(4,'alli','ali@bigone.com','ali3','2026-06-24 13:24:00',NULL),(5,'allison','alli@alli','alli','2026-06-28 18:09:54',11);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-28 14:52:01
