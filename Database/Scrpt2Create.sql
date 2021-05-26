-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: d3
-- ------------------------------------------------------
-- Server version	8.0.22

-- use command below to create database first
-- create database mydb2;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `street` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `postcode` varchar(45) NOT NULL,
  `x` int NOT NULL,
  `y` int NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'Piratens Väg 2','Kristianstad','29145',1,4),(2,'Piratens Väg 3','Kristianstad','29145',1,5),(3,'Piratens Väg 4','Kristianstad','29145',1,6),(4,'Piratens Väg 5','Kristianstad','29145',1,7);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `charity`
--

DROP TABLE IF EXISTS `charity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `charity` (
  `User_ID` int NOT NULL,
  `webpage` varchar(45) DEFAULT NULL,
  `description` varchar(450) DEFAULT NULL,
  PRIMARY KEY (`User_ID`),
  KEY `fk_Charity_User1_idx` (`User_ID`),
  CONSTRAINT `fk_Charity_User1` FOREIGN KEY (`User_ID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `charity`
--

LOCK TABLES `charity` WRITE;
/*!40000 ALTER TABLE `charity` DISABLE KEYS */;
INSERT INTO `charity` VALUES (3,'www.hjälpenvän.se','Hjälpenvän är ett projekt där vi samlar in olika typer av resurser som sedan delas till hemlösa & behövande runt om i landet');
/*!40000 ALTER TABLE `charity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `charity_has_itemtype`
--

DROP TABLE IF EXISTS `charity_has_itemtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `charity_has_itemtype` (
  `Charity_User_ID` int NOT NULL,
  `itemType_ID` int NOT NULL,
  PRIMARY KEY (`Charity_User_ID`,`itemType_ID`),
  KEY `fk_Charity_has_itemType_itemType1_idx` (`itemType_ID`),
  KEY `fk_Charity_has_itemType_Charity1_idx` (`Charity_User_ID`),
  CONSTRAINT `fk_Charity_has_itemType_Charity1` FOREIGN KEY (`Charity_User_ID`) REFERENCES `charity` (`User_ID`),
  CONSTRAINT `fk_Charity_has_itemType_itemType1` FOREIGN KEY (`itemType_ID`) REFERENCES `itemtype` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `charity_has_itemtype`
--

LOCK TABLES `charity_has_itemtype` WRITE;
/*!40000 ALTER TABLE `charity_has_itemtype` DISABLE KEYS */;
INSERT INTO `charity_has_itemtype` VALUES(3,1),(3,2),(3,3);
/*!40000 ALTER TABLE `charity_has_itemtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `driver` (
  `User_ID` int NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`User_ID`),
  KEY `fk_Driver_User1_idx` (`User_ID`),
  CONSTRAINT `fk_Driver_User1` FOREIGN KEY (`User_ID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver`
--

LOCK TABLES `driver` WRITE;
/*!40000 ALTER TABLE `driver` DISABLE KEYS */;
INSERT INTO `driver` VALUES (2,NULL);
/*!40000 ALTER TABLE `driver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drivertasklist`
--

DROP TABLE IF EXISTS `drivertasklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drivertasklist` (
  `Driver_ID` int NOT NULL,
  `index_iterator` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`Driver_ID`),
  CONSTRAINT `drivertasklist_ibfk_1` FOREIGN KEY (`Driver_ID`) REFERENCES `driver` (`User_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drivertasklist`
--

LOCK TABLES `drivertasklist` WRITE;
/*!40000 ALTER TABLE `drivertasklist` DISABLE KEYS */;
INSERT INTO `drivertasklist` VALUES (2,0);
/*!40000 ALTER TABLE `drivertasklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drivertasklist_index`
--

DROP TABLE IF EXISTS `drivertasklist_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drivertasklist_index` (
  `Driver_ID` int NOT NULL,
  `Task_ID` int DEFAULT NULL,
  `Charity_ID` int DEFAULT NULL,
  `index_nr` int NOT NULL,
  KEY `Driver_ID` (`Driver_ID`),
  KEY `Task_ID` (`Task_ID`),
  KEY `Charity_ID` (`Charity_ID`),
  CONSTRAINT `drivertasklist_index_ibfk_1` FOREIGN KEY (`Driver_ID`) REFERENCES `drivertasklist` (`Driver_ID`),
  CONSTRAINT `drivertasklist_index_ibfk_2` FOREIGN KEY (`Task_ID`) REFERENCES `task` (`ID`),
  CONSTRAINT `drivertasklist_index_ibfk_3` FOREIGN KEY (`Charity_ID`) REFERENCES `charity` (`User_ID`),
  CONSTRAINT `CK_nulltest` CHECK (((`Task_ID` is not null) xor (`Charity_ID` is not null)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drivertasklist_index`
--



--
-- Table structure for table `itemtype`
--

DROP TABLE IF EXISTS `itemtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itemtype` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Description` varchar(45) DEFAULT NULL,
  `Name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemtype`
--

LOCK TABLES `itemtype` WRITE;
/*!40000 ALTER TABLE `itemtype` DISABLE KEYS */;
INSERT INTO `itemtype` VALUES (1,'Any type of items worn on the body','Clothes'),(2,'Dining furniture, Bookcases, Beds etc','Furniture'),(3,'Computers & things that run on electricity','Computer & Electrical items'),(4,'Crafting and work tools','Tools'),(5,'All kind of toys for children','Toys'),(6,'All kinds of fabrics','Cloths'),(7,'Things to read','Books');
/*!40000 ALTER TABLE `itemtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) DEFAULT NULL,
  `description` varchar(300) NOT NULL,
  `start_date` varchar(45) NOT NULL,
  `end_date` varchar(45) DEFAULT NULL,
  `Donor_ID` int NOT NULL,
  `pickupAddress_ID` int NOT NULL,
  `Driver_User_ID` int DEFAULT NULL,
  `Charity_User_ID` int NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_Task_User1_idx` (`Donor_ID`),
  KEY `fk_Task_Address1_idx` (`pickupAddress_ID`),
  KEY `fk_Task_Driver1_idx` (`Driver_User_ID`),
  KEY `fk_Task_Charity1_idx` (`Charity_User_ID`),
  CONSTRAINT `fk_Task_Address1` FOREIGN KEY (`pickupAddress_ID`) REFERENCES `address` (`ID`),
  CONSTRAINT `fk_Task_Charity1` FOREIGN KEY (`Charity_User_ID`) REFERENCES `charity` (`User_ID`),
  CONSTRAINT `fk_Task_Driver1` FOREIGN KEY (`Driver_User_ID`) REFERENCES `driver` (`User_ID`),
  CONSTRAINT `fk_Task_User1` FOREIGN KEY (`Donor_ID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--

--



--
-- Table structure for table `task_has_itemtype`
--

DROP TABLE IF EXISTS `task_has_itemtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_has_itemtype` (
  `Task_ID` int NOT NULL,
  `itemType_ID` int NOT NULL,
  PRIMARY KEY (`Task_ID`,`itemType_ID`),
  KEY `fk_Task_has_itemType_itemType1_idx` (`itemType_ID`),
  KEY `fk_Task_has_itemType_Task1_idx` (`Task_ID`),
  CONSTRAINT `fk_Task_has_itemType_itemType1` FOREIGN KEY (`itemType_ID`) REFERENCES `itemtype` (`ID`),
  CONSTRAINT `fk_Task_has_itemType_Task1` FOREIGN KEY (`Task_ID`) REFERENCES `task` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_has_itemtype`
--

LOCK TABLES `task_has_itemtype` WRITE;
/*!40000 ALTER TABLE `task_has_itemtype` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_has_itemtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `type` int NOT NULL,
  `phone` varchar(45) NOT NULL,
  `pass` varchar(45) NOT NULL,
  `Address_ID` int NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `userName_UNIQUE` (`userName`),
  KEY `fk_User_Address1_idx` (`Address_ID`),
  CONSTRAINT `fk_User_Address1` FOREIGN KEY (`Address_ID`) REFERENCES `address` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'test0','Ann Mori',0,'0703813272','root',1),(2,'test1','Zark',1,'0703813672','root',2),(3,'test3','Hjälp En Vän',2,'0704213212','root',3);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-21 17:06:47

select * from task;


