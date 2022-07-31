-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: pim_tool_db
-- ------------------------------------------------------
-- Server version	8.0.28

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

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `visa` varchar(3) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `birth_date` date NOT NULL,
  `version` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'NGU','thai','son','2001-03-09',1),(2,'TRA','hoang','thai','2001-09-11',1),(3,'NVA','van','anh','2001-01-01',1),(4,'NVB','van','bao','2001-02-02',1),(5,'NVC','van','canh','2001-03-04',1),(6,'NVD','van','danh','2001-04-04',1),(7,'NVT','van','tien','2001-05-05',1),(8,'NVP','van','phu','2001-06-06',1),(9,'NVQ','van','quoc','2001-07-07',1),(10,'NVM','van','minh','2001-08-08',1),(11,'NVN','van','nam','2001-09-09',1),(12,'NVL','van','long','2001-10-10',1),(13,'NVH','van','huy','2001-11-11',1);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `id` int NOT NULL AUTO_INCREMENT,
  `team_id` int NOT NULL,
  `project_number` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `customer` varchar(50) NOT NULL,
  `status` varchar(3) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `version` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_team_id_idx` (`team_id`),
  CONSTRAINT `fk_team_id` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,1,1234,'game android','elca','NEW','2022-07-31','2022-08-07',2),(2,2,5678,'game mobile ios','elca','FIN','2022-07-31',NULL,2),(3,3,9101,'web game','hcmus','PLA','2022-07-31','2022-08-06',1),(4,3,8989,'web market','uit','NEW','2022-07-31','2022-08-06',1),(5,4,6767,'web bank','vng','INP','2022-07-31','2022-08-06',1),(6,5,5555,'goFood','vin','NEW','2022-07-31','2022-08-06',1);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_employee`
--

DROP TABLE IF EXISTS `project_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_employee` (
  `project_id` int NOT NULL,
  `employee_id` int NOT NULL,
  KEY `fk_project_id_idx` (`project_id`),
  KEY `fk_employee_id_idx` (`employee_id`),
  CONSTRAINT `fk_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_employee`
--

LOCK TABLES `project_employee` WRITE;
/*!40000 ALTER TABLE `project_employee` DISABLE KEYS */;
INSERT INTO `project_employee` VALUES (1,3),(1,4),(2,3),(2,4),(3,3),(3,8),(3,1),(4,13),(4,3),(4,1),(5,4),(5,1),(6,3),(6,4),(6,1),(1,5);
/*!40000 ALTER TABLE `project_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team` (
  `id` int NOT NULL AUTO_INCREMENT,
  `team_leader_id` int NOT NULL,
  `version` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_team_leader_id_idx` (`team_leader_id`),
  CONSTRAINT `fk_team_leader_id` FOREIGN KEY (`team_leader_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES (1,1,1),(2,2,1),(3,4,1),(4,3,1),(5,2,1);
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-31 22:30:46
