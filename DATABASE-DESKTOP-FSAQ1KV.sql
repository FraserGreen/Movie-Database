-- MySQL dump 10.13  Distrib 8.0.24, for Win64 (x86_64)
--
-- Host: localhost    Database: moviemad
-- ------------------------------------------------------
-- Server version	8.0.24

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `first_name` varchar(80) NOT NULL,
  `last_name` varchar(80) NOT NULL,
  `sex` varchar(1) NOT NULL,
  `birth_year` int NOT NULL,
  `country` varchar(255) NOT NULL,
  `post_code` varchar(15) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `type` varchar(10) NOT NULL,
  `organisation_name` varchar(63) DEFAULT NULL,
  `organisation_phone` varchar(31) DEFAULT NULL,
  PRIMARY KEY (`email`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('abc','def','m',2000,'Australia','3033','abc@gmail.com','$2a$10$3FDFkofEYNuTJOzwx7DK1OulR/8Zn0quyGv1vqGx.Luy9fWR40P1K','$2a$10$3FDFkofEYNuTJOzwx7DK1O','Critic','Miramax Films','8477124124'),('Fraser','Green','M',2000,'Australia','3051','fraser@gmail.com','$2a$10$wy4wSmkuSWAs8v.DyzH49uDnYHKQ8pfkHn4onSperuNWUxEIOdAkm','$2a$10$wy4wSmkuSWAs8v.DyzH49u','critic','Marvel','04565656556'),('John','Johnret','M',1990,'Australia','3000','John@gmail.com','$2a$10$n1k6rs3f8dfPbI4in2rZWuB84etnNjCdSYzqspIEy4pZVexYtQbT6','$2a$10$n1k6rs3f8dfPbI4in2rZWu','Admin','Marvel','04565656556'),('Showvic','Arnab','M',2000,'Australia','3000','showvic@student.com','$2a$10$QmP5L5VQlrz3MvxyWBkDSOcG3mlutYbve3oQ2AEZEEZxiJ2den5UG','$2a$10$QmP5L5VQlrz3MvxyWBkDSO','PCo','Miramax Films','04428462121');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_request`
--

DROP TABLE IF EXISTS `account_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_request` (
  `request_id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) NOT NULL,
  `request_type` varchar(45) NOT NULL,
  `organisation_name` varchar(125) DEFAULT NULL,
  `organisation_phone` varchar(31) DEFAULT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`request_id`),
  KEY `fk_email_idx` (`user_id`),
  CONSTRAINT `fk_email_account_request` FOREIGN KEY (`user_id`) REFERENCES `account` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_request`
--

LOCK TABLES `account_request` WRITE;
/*!40000 ALTER TABLE `account_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `production_company`
--

DROP TABLE IF EXISTS `production_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `production_company` (
  `proco_id` int NOT NULL AUTO_INCREMENT,
  `proco_name` varchar(45) NOT NULL,
  PRIMARY KEY (`proco_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `production_company`
--

LOCK TABLES `production_company` WRITE;
/*!40000 ALTER TABLE `production_company` DISABLE KEYS */;
INSERT INTO `production_company` VALUES (1,'Universal Pictures'),(2,'Paramount Pictures'),(3,'20th Century Fox'),(4,'Warner Bros.'),(5,'DreamWorks Pictures'),(6,'Metro-Goldwyn-Meyer'),(7,'Miramax'),(8,'Columbia Pictures'),(9,'Walt Disney Pictures'),(10,'Sony Pictures'),(11,'New Line Cinema');
/*!40000 ALTER TABLE `production_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `show`
--

DROP TABLE IF EXISTS `show`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `show` (
  `showid` int NOT NULL AUTO_INCREMENT,
  `show_title` varchar(45) NOT NULL,
  `genre` varchar(45) NOT NULL,
  `length` decimal(7,2) NOT NULL,
  `movie` tinyint(1) NOT NULL,
  `series` tinyint(1) NOT NULL,
  `proco_id` int NOT NULL,
  `year` int NOT NULL,
  `path_to_image` varchar(255) NOT NULL,
  `status` varchar(15) NOT NULL,
  PRIMARY KEY (`showid`),
  KEY `fk_proco_idx` (`proco_id`),
  CONSTRAINT `fk_proco` FOREIGN KEY (`proco_id`) REFERENCES `production_company` (`proco_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `show`
--

LOCK TABLES `show` WRITE;
/*!40000 ALTER TABLE `show` DISABLE KEYS */;
INSERT INTO `show` VALUES (1,'The Godfather','Crime, Drama',2.92,1,0,2,1972,'TheGodfather.jpg','Visible'),(2,'Titanic','Drama, Romance',2.23,1,0,2,1997,'Titanic.png','Visible'),(3,'Pulp Fiction','Crime, Drama',2.57,1,0,7,1994,'PulpFiction.jpg','Visible'),(4,'Family Guy','Comedy',366.00,0,1,3,1999,'FamilyGuy.png','Visible'),(5,'Star Trek','Sci-Fi',80.00,0,1,4,1966,'StarTrek.png','Waiting');
/*!40000 ALTER TABLE `show` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_review`
--

DROP TABLE IF EXISTS `user_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_review` (
  `reviewId` int NOT NULL AUTO_INCREMENT,
  `show_id` int NOT NULL,
  `user_id` varchar(45) NOT NULL,
  `rating` int NOT NULL DEFAULT '3',
  `review` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`reviewId`),
  KEY `fk_showid_idx` (`show_id`),
  KEY `fk_email_idx` (`user_id`),
  CONSTRAINT `fk_email` FOREIGN KEY (`user_id`) REFERENCES `account` (`email`),
  CONSTRAINT `fk_showid` FOREIGN KEY (`show_id`) REFERENCES `show` (`showid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_review`
--

LOCK TABLES `user_review` WRITE;
/*!40000 ALTER TABLE `user_review` DISABLE KEYS */;
INSERT INTO `user_review` VALUES (1,2,'2',3,'AWESOME!!','2021-03-23 00:00:00'),(2,1,'John',5,'Perfect','2021-01-01 00:00:00'),(3,1,'John',5,'Great stuff','2021-01-01 00:00:00'),(4,2,'John',3,'Average','2021-01-01 00:00:00'),(5,3,'John',4,'Great directing.','2021-05-14 00:00:00');
/*!40000 ALTER TABLE `user_review` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-23 20:02:32
