CREATE DATABASE  IF NOT EXISTS `borudo` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `borudo`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win32 (AMD64)
--
-- Host: 127.0.0.1    Database: borudo
-- ------------------------------------------------------
-- Server version	5.7.16-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `photos`
--

DROP TABLE IF EXISTS `photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `photos` (
  `idPhotos` int(11) NOT NULL AUTO_INCREMENT,
  `PhotoPath` varchar(255) NOT NULL,
  `PTheme` varchar(45) NOT NULL,
  `PhotoTitle` varchar(45) NOT NULL,
  PRIMARY KEY (`idPhotos`),
  UNIQUE KEY `idPhotos_UNIQUE` (`idPhotos`),
  KEY `Theme_idx` (`PTheme`),
  CONSTRAINT `Theme` FOREIGN KEY (`PTheme`) REFERENCES `ptheme` (`PTheme`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `photos`
--

LOCK TABLES `photos` WRITE;
/*!40000 ALTER TABLE `photos` DISABLE KEYS */;
INSERT INTO `photos` VALUES (1,'file:src\\Resources\\Fotos\\Amsterdam(1).jpg','Amsterdam','Amsterdam 1'),(15,'file:src\\Resources\\Fotos\\Amsterdam(2).jpg','Amsterdam','Amsterdam 2'),(16,'file:src\\Resources\\Fotos\\Amsterdam(3).jpg','Amsterdam','Amsterdam 3'),(17,'file:src\\Resources\\Fotos\\Amsterdam(4).jpg','Amsterdam','Amsterdam 4'),(18,'file:src\\Resources\\Fotos\\Amsterdam(5).jpg','Amsterdam','Amsterdam 5'),(19,'file:src\\Resources\\Fotos\\Amsterdam(6).jpg','Amsterdam','Amsterdam 6'),(20,'file:src\\Resources\\Fotos\\Amsterdam(7).jpg','Amsterdam','Amsterdam 7'),(21,'file:src\\Resources\\Fotos\\Schaatsen(1).jpg','Schaatsen','Schaatsen 1'),(22,'file:src\\Resources\\Fotos\\Schaatsen(2).jpg','Schaatsen','Schaatsen 2'),(23,'file:src\\Resources\\Fotos\\Schaatsen(3).jpg','Schaatsen','Schaatsen 3'),(24,'file:src\\Resources\\Fotos\\Schaatsen(4).jpg','Schaatsen','Schaatsen 4'),(25,'file:src\\Resources\\Fotos\\Schaatsen(5).jpg','Schaatsen','Schaatsen 5'),(26,'file:src\\Resources\\Fotos\\Strand(1).jpg','Strand','Strand 1'),(27,'file:src\\Resources\\Fotos\\Strand(2).jpg','Strand','Strand 2'),(28,'file:src\\Resources\\Fotos\\Strand(3).jpg','Strand','Strand 3'),(29,'file:src\\Resources\\Fotos\\Strand(4).jpg','Strand','Strand 4'),(30,'file:src\\Resources\\Fotos\\Strand(5).jpg','Strand','Strand 5'),(31,'file:src\\Resources\\Fotos\\Strand(6).jpg','Strand','Strand 6');
/*!40000 ALTER TABLE `photos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-23  2:05:43
