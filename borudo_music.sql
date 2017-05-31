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
-- Table structure for table `music`
--

DROP TABLE IF EXISTS `music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `music` (
  `idMusic` int(11) NOT NULL AUTO_INCREMENT,
  `musicPath` varchar(255) NOT NULL,
  `musicTitle` varchar(45) NOT NULL,
  `mArtist` varchar(45) NOT NULL,
  `mPlaylist` varchar(45) NOT NULL,
  PRIMARY KEY (`idMusic`),
  UNIQUE KEY `idMusic_UNIQUE` (`idMusic`),
  KEY `FK_Artist_idx` (`mArtist`),
  KEY `FK_Playlist_idx` (`mPlaylist`),
  CONSTRAINT `FK_Artist` FOREIGN KEY (`mArtist`) REFERENCES `artist` (`ArtistName`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Playlist` FOREIGN KEY (`mPlaylist`) REFERENCES `playlist` (`PlaylistName`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music`
--

LOCK TABLES `music` WRITE;
/*!40000 ALTER TABLE `music` DISABLE KEYS */;
INSERT INTO `music` VALUES (1,'src\\Resources\\Music\\A Day Late.mp3','A Day Late','Anberlin','List 2'),(29,'src\\Resources\\Music\\Adelaide.mp3','Adelaide','Anberlin','List 2'),(30,'src\\Resources\\Music\\Alexithymia.mp3','Alexithymia','Anberlin','List 4'),(31,'src\\Resources\\Music\\Breaking.mp3','Breaking','Anberlin','List 2'),(32,'src\\Resources\\Music\\Comatose.mp3','Comatose','Skillet','List 1'),(33,'src\\Resources\\Music\\Hero.mp3','Hero','Skillet','List 2'),(34,'src\\Resources\\Music\\Hesitation Snow.mp3','Hesitation Snow','Fripside','List 2'),(35,'src\\Resources\\Music\\I Trust you.mp3','I Trust You','Skillet','List 1'),(36,'src\\Resources\\Music\\Late in autumn.mp3','Late in Autumn','Fripside','List 3'),(37,'src\\Resources\\Music\\Lies.mp3','Lies','Billy Talent','List 1'),(38,'src\\Resources\\Music\\Nothing To Lose.mp3','Nothing To Lose','Billy Talent','List 4');
/*!40000 ALTER TABLE `music` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-23  2:05:13
